package de.htwkleipzig.mmdb.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.ElasticSearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;

import de.htwkleipzig.mmdb.model.Author;
import de.htwkleipzig.mmdb.service.AuthorService;
import de.htwkleipzig.mmdb.util.AuthorHelper;
import de.htwkleipzig.mmdb.util.Utilities;
import fr.pilato.spring.elasticsearch.ElasticsearchTransportClientFactoryBean;

@Service
public class AuthorServiceImpl implements AuthorService {

    /**
     * 
     */
    private static final long serialVersionUID = -5057845859337839315L;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorServiceImpl.class);
    private static String INDEX_AUTHOR_NAME = Utilities.getProperty("index.author.name");
    private static String INDEX_AUTHOR_TYPE = Utilities.getProperty("index.author.type");

    TransportClient client;

    @Autowired
    ElasticsearchTransportClientFactoryBean transportFactoryBean;

    @Override
    public void init() {
        LOGGER.info("init TransportClient");
        for (String node : transportFactoryBean.getEsNodes()) {
            LOGGER.info("node {}", node);
        }
        try {
            client = (TransportClient) transportFactoryBean.getObject();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // create an index called myindex
        LOGGER.info("create an index called {}", INDEX_AUTHOR_NAME);
        // client.admin().indices().create(new CreateIndexRequest(INDEX_AUTHOR_NAME)).actionGet();
        IndicesAdminClient indAdminClient = client.admin().indices();
        // indexRequest.mapping(INDEX_AUTHOR_TYPE, data)
        try {
            indAdminClient.create(createIndex(INDEX_AUTHOR_NAME, indAdminClient)).actionGet();
        } catch (IndexAlreadyExistsException ex) {
            LOGGER.debug("{} already exists", INDEX_AUTHOR_NAME);
        }

    }

    private CreateIndexRequest createIndex(String indexName, IndicesAdminClient indAdminClient) {
        CreateIndexRequestBuilder createIndexRequestBuilder = new CreateIndexRequestBuilder(indAdminClient, indexName);
        JsonObject settings = new JsonObject();
        settings.addProperty("number_of_shards", 1);
        settings.addProperty("number_of_replicas", 1);

        createIndexRequestBuilder.setSettings(settings.toString());

        return createIndexRequestBuilder.request();
    }

    @Override
    public Author get(String id) {
        LOGGER.debug("get a resource from index {}, type {}, id {}", new Object[] { INDEX_AUTHOR_NAME,
                INDEX_AUTHOR_TYPE, id });
        GetResponse rsp = client.prepareGet(INDEX_AUTHOR_NAME, INDEX_AUTHOR_TYPE, id).execute().actionGet();
        Map<String, Object> ret = rsp.getSource();
        LOGGER.debug("try to map the response to author");
        if (rsp.exists()) {
            LOGGER.debug("author exists and will be returned");
            return AuthorHelper.source2author(ret);
        } else {
            LOGGER.debug("author doesn't exist");
            return null;
        }
    }

    @Override
    public List<Author> getAll() {
        LOGGER.debug("get all Authors from index {}, type {}", new Object[] { INDEX_AUTHOR_NAME, INDEX_AUTHOR_TYPE });
        SearchRequestBuilder builder = client.prepareSearch(INDEX_AUTHOR_NAME);
        MatchAllQueryBuilder qb = new MatchAllQueryBuilder();

        builder.setQuery(qb);
        SearchResponse actionGet = builder.execute().actionGet();

        List<Author> authors = new ArrayList<Author>();

        for (SearchHit hit : actionGet.getHits().getHits()) {
            if (hit.isSourceEmpty()) {
                LOGGER.info("source is empty");
            }
            LOGGER.info("id of the author {}", hit.getId());
            LOGGER.info("score of the hit {}", hit.getScore());

            Map<String, Object> resultMap = hit.sourceAsMap();

            Author authorObject = AuthorHelper.source2author(resultMap);

            LOGGER.debug("author: {}", authorObject.toString());
            LOGGER.debug("paper: {}", authorObject.getPaperIds().toString());
            authors.add(authorObject);

        }
        return authors;

    }

    @Override
    @Deprecated
    public boolean save(String id, Map<String, Object> data) {
        IndexRequestBuilder irb = client.prepareIndex(INDEX_AUTHOR_NAME, INDEX_AUTHOR_TYPE, id).setSource(data);
        try {
            LOGGER.debug("try to save the content to es");
            irb.execute().actionGet();
        } catch (ElasticSearchException ese) {
            LOGGER.error("Error while saving it", ese);
            return false;
        }
        return true;
    }

    @Override
    @Deprecated
    public boolean saveJson(String id, XContentBuilder content) {
        IndexRequestBuilder irb = client.prepareIndex(INDEX_AUTHOR_NAME, INDEX_AUTHOR_TYPE, id).setSource(content);
        try {
            irb.execute().actionGet();
        } catch (ElasticSearchException ese) {
            LOGGER.error("Error while saving it", ese);
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(String id) {
        DeleteResponse response = client.prepareDelete(INDEX_AUTHOR_NAME, INDEX_AUTHOR_TYPE, id).execute().actionGet();
        if (response.notFound()) {
            LOGGER.debug("there was no document foudn with the id {}", id);
            return false;
        }
        return true;
    }

    @Override
    public SearchResponse search(QueryBuilder query) {
        SearchRequestBuilder builder = client.prepareSearch(INDEX_AUTHOR_NAME);
        builder.setQuery(query);

        return builder.execute().actionGet();
    }

    @Override
    public boolean save(Author author) {

        if (author == null) {
            LOGGER.debug("The author is NULL");
            throw new IllegalArgumentException("The author is NULL");
        }
        LOGGER.debug("try to save the Author with id {}", author.getAuthorId());
        XContentBuilder b = null;
        try {
            b = AuthorHelper.dao2json(author);
        } catch (IOException e) {
            LOGGER.error("error while parsing the author to xcontent {}", e.fillInStackTrace());
        }
        if (b == null) {
            LOGGER.warn("something wrong while dao2json move from author with id {}", author.getAuthorId());
            return false;
        }
        IndexRequestBuilder irb = client.prepareIndex(INDEX_AUTHOR_NAME, INDEX_AUTHOR_TYPE, author.getAuthorId())
                .setSource(b);
        irb.execute().actionGet();
        return true;
    }

    /**
     * 
     * @param author
     * @return
     * @throws IOException
     */
    @Override
    public boolean updateAuthor(Author author) {
        LOGGER.debug("update author with id {}", author.getAuthorId());
        if (authorExists(author.getAuthorId())) {
            return save(author);
        }
        LOGGER.debug("author with id {} doesn't exists", author.getAuthorId());
        return save(author);
    }

    @Override
    public boolean authorExists(String authorId) {
        LOGGER.debug("authorExists({})", authorId);
        if (this.get(authorId) != null) {
            return true;
        }
        return false;
    }

    @Override
    public void onShutdown() {
        client.close();
    }

}
