package de.htwkleipzig.mmdb.service.impl;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
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
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;

import de.htwkleipzig.mmdb.model.Author;
import de.htwkleipzig.mmdb.service.AuthorService;
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
            return source2author(ret);
        } else {
            LOGGER.debug("author doesn't exist");
            return null;
        }
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

    /**
     * save the object author to es
     * 
     * @param author
     *            The author
     * @return true if the save was successful
     * @throws IOException
     *             if there is something wrong with the io e.g. author could not be parsed to json
     */
    @Override
    public boolean save(Author author) {

        if (author == null) {
            LOGGER.debug("The author is NULL");
            throw new IllegalArgumentException("The author is NULL");
        } else if (author.getAuthorId().isEmpty() || author.getEmail().isEmpty() || author.getLastname().isEmpty()
                || author.getName().isEmpty() || author.getPaperIds().isEmpty() || author.getTitle().isEmpty()
                || author.getUniversityId().isEmpty()) {
            LOGGER.debug("one of the Author arguments is empty. this is illegal!");
            throw new IllegalArgumentException("one of the author arguments is empty. This is illegal!");
        }
        LOGGER.debug("try to save the Author with id {}", author.getAuthorId());
        XContentBuilder b = null;
        try {
            b = dao2json(author);
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
        LOGGER.error("author with id {} doesn't exists", author.getAuthorId());
        return false;
    }

    @Override
    public boolean authorExists(String authorId) {
        LOGGER.debug("authorExists({})", authorId);
        if (this.get(authorId) != null) {
            return true;
        }
        return false;
    }

    /**
     * @param author
     * @return
     * @throws IOException
     */
    private XContentBuilder dao2json(Author author) throws IOException {
        LOGGER.debug("create the json object from Author");
        XContentBuilder b = jsonBuilder().startObject();
        b.field("authorId", author.getAuthorId());
        b.field("title", author.getTitle());
        b.field("name", author.getName());
        b.field("lastName", author.getLastname());
        b.field("email", author.getEmail());
        b.field("universityId", author.getUniversityId());
        b.field("paperIds", author.getPaperIds());
        LOGGER.debug(b.string());
        return b;
    }

    private Author source2author(Map<String, Object> source) {
        Author author = new Author();
        author.setAuthorId((String) source.get("authorId"));
        author.setTitle((String) source.get("title"));
        author.setName((String) source.get("name"));
        author.setLastname((String) source.get("lastName"));
        author.setEmail((String) source.get("email"));
        author.setUniversityId((String) source.get("universityId"));
        List<String> paperIds = (List<String>) source.get("paperIds");
        LOGGER.debug("counted papers {}", paperIds.size());
        author.setPaperIds(paperIds);

        return author;
    }

    @Override
    public void onShutdown() {
        client.close();
    }

}
