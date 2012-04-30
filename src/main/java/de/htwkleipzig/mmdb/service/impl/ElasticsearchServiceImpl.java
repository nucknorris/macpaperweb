package de.htwkleipzig.mmdb.service.impl;

import java.util.Map;

import org.elasticsearch.ElasticSearchException;
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

import de.htwkleipzig.mmdb.service.ElasticsearchService;
import de.htwkleipzig.mmdb.util.Utilities;
import fr.pilato.spring.elasticsearch.ElasticsearchTransportClientFactoryBean;

@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

    /**
     * 
     */
    private static final long serialVersionUID = -5057845859337839315L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchServiceImpl.class);
    private static String INDEX_NAME = Utilities.getProperty("index.name");
    private static String INDEX_TYPE = Utilities.getProperty("index.type");
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

        try {
            // create an index called myindex
            LOGGER.info("create an index called {}", INDEX_NAME);
            // client.admin().indices().create(new CreateIndexRequest(INDEX_NAME)).actionGet();
            IndicesAdminClient indAdminClient = client.admin().indices();
            CreateIndexRequestBuilder createIndexRequestBuilder = new CreateIndexRequestBuilder(indAdminClient,
                    INDEX_NAME);
            JsonObject settings = new JsonObject();
            settings.addProperty("number_of_shards", 1);
            settings.addProperty("number_of_replicas", 1);

            createIndexRequestBuilder.setSettings(settings.toString());
            indAdminClient.create(createIndexRequestBuilder.request()).actionGet();

        } catch (IndexAlreadyExistsException ex) {
            LOGGER.debug("already exists");
        }
    }

    @Override
    public GetResponse get(String id) {
        LOGGER.debug("get a resource from index {}, type {}, id {}",
                new Object[] { INDEX_NAME, Utilities.getProperty("index.type"), id });
        GetResponse rsp = client.prepareGet(INDEX_NAME, INDEX_TYPE, id).execute().actionGet();
        return rsp;
    }

    @Override
    public boolean save(String id, Map<String, Object> data) {
        IndexRequestBuilder irb = client.prepareIndex(INDEX_NAME, INDEX_TYPE, id).setSource(data);
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
    public boolean saveJson(String id, XContentBuilder content) {
        IndexRequestBuilder irb = client.prepareIndex(INDEX_NAME, INDEX_TYPE, id).setSource(content);
        try {
            irb.execute().actionGet();
        } catch (ElasticSearchException ese) {
            LOGGER.error("Error while saving it", ese);
            return false;
        }
        return true;
    }

    @Override
    public DeleteResponse delete(String id) {
        DeleteResponse response = client.prepareDelete(INDEX_NAME, INDEX_TYPE, id).execute().actionGet();

        return response;
    }

    @Override
    public SearchResponse search(QueryBuilder query) {
        SearchRequestBuilder builder = client.prepareSearch(INDEX_NAME);
        builder.setQuery(query);

        return builder.execute().actionGet();
    }

    @Override
    public void onShutdown() {
        client.close();
    }

}
