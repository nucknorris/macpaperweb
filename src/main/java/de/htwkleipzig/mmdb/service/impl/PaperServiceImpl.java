package de.htwkleipzig.mmdb.service.impl;

import java.io.IOException;
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

import de.htwkleipzig.mmdb.model.Paper;
import de.htwkleipzig.mmdb.service.PaperService;
import de.htwkleipzig.mmdb.util.PaperHelper;
import de.htwkleipzig.mmdb.util.Utilities;
import fr.pilato.spring.elasticsearch.ElasticsearchTransportClientFactoryBean;

@Service
public class PaperServiceImpl implements PaperService {
    /**
     * 
     */
    private static final long serialVersionUID = -5057845859337839315L;
    private static final Logger LOGGER = LoggerFactory.getLogger(PaperServiceImpl.class);
    private static String INDEX_PAPER_NAME = Utilities.getProperty("index.paper.name");
    private static String INDEX_PAPER_TYPE = Utilities.getProperty("index.paper.type");

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
            e.printStackTrace();
        }

        try {
            // create an index called myindex
            LOGGER.info("create an index called {}", INDEX_PAPER_NAME);
            // client.admin().indices().create(new CreateIndexRequest(INDEX_UNIVERSITY_NAME)).actionGet();
            IndicesAdminClient indAdminClient = client.admin().indices();

            indAdminClient.create(createAllIndizes(INDEX_PAPER_NAME, indAdminClient)).actionGet();

        } catch (IndexAlreadyExistsException ex) {
            LOGGER.debug("{} already exists", INDEX_PAPER_NAME);
        }
    }

    private CreateIndexRequest createAllIndizes(String indexName, IndicesAdminClient indAdminClient) {
        CreateIndexRequestBuilder createIndexRequestBuilder = new CreateIndexRequestBuilder(indAdminClient, indexName);
        JsonObject settings = new JsonObject();
        settings.addProperty("number_of_shards", 1);
        settings.addProperty("number_of_replicas", 1);

        createIndexRequestBuilder.setSettings(settings.toString());

        return createIndexRequestBuilder.request();
    }

    @Override
    public Paper get(String id) {
        LOGGER.debug("get a resource from index {}, type {}, id {}", new Object[] { INDEX_PAPER_NAME, INDEX_PAPER_TYPE,
                id });
        GetResponse rsp = client.prepareGet(INDEX_PAPER_NAME, INDEX_PAPER_TYPE, id).execute().actionGet();
        Map<String, Object> ret = rsp.getSource();
        LOGGER.debug("try to map the response to Paper");
        if (rsp.exists()) {
            LOGGER.debug("Paper exists and will be returned");
            return PaperHelper.source2Paper(ret);
        } else {
            LOGGER.debug("Paper doesn't exist");
            return null;
        }
    }

    @Override
    @Deprecated
    public boolean save(String id, Map<String, Object> data) {
        IndexRequestBuilder irb = client.prepareIndex(INDEX_PAPER_NAME, INDEX_PAPER_TYPE, id).setSource(data);
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
        IndexRequestBuilder irb = client.prepareIndex(INDEX_PAPER_NAME, INDEX_PAPER_TYPE, id).setSource(content);
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
        DeleteResponse response = client.prepareDelete(INDEX_PAPER_NAME, INDEX_PAPER_TYPE, id).execute().actionGet();

        if (response.notFound()) {
            LOGGER.debug("there was no document foudn with the id {}", id);
            return false;
        }
        return true;
    }

    @Override
    public SearchResponse search(QueryBuilder query) {
        SearchRequestBuilder builder = client.prepareSearch(INDEX_PAPER_NAME);
        builder.setQuery(query);

        return builder.execute().actionGet();
    }

    @Override
    public boolean save(Paper paper) {
        if (paper == null) {
            LOGGER.debug("The paper is NULL");
            throw new IllegalArgumentException("The paper is NULL");
        }
        LOGGER.debug("try to save the paper with id {}", paper.getPaperId());
        XContentBuilder b = null;
        try {
            b = PaperHelper.paper2Json(paper);
        } catch (IOException e) {
            LOGGER.error("error while parsing the paper to xcontent {}", e.fillInStackTrace());
        }
        if (b == null) {
            LOGGER.warn("something wrong while dao2json move from paper with id {}", paper.getPaperId());
            return false;
        }
        IndexRequestBuilder irb = client.prepareIndex(INDEX_PAPER_NAME, INDEX_PAPER_TYPE, paper.getPaperId())
                .setSource(b);
        irb.execute().actionGet();
        return true;
    }

    @Override
    public boolean updatePaper(Paper paper) {
        LOGGER.debug("update paper with id {}", paper.getPaperId());
        if (paperExists(paper.getPaperId())) {
            return save(paper);
        }
        LOGGER.error("paper with id {} doesn't exists", paper.getPaperId());
        return false;
    }

    @Override
    public boolean paperExists(String paperId) {
        LOGGER.debug("paperExists({})", paperId);
        if (this.get(paperId) != null) {
            return true;
        }
        return false;
    }

    @Override
    public void onShutdown() {
        client.close();
    }

}
