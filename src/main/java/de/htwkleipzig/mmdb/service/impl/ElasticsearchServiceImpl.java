package de.htwkleipzig.mmdb.service.impl;

import java.util.Map;

import org.elasticsearch.ElasticSearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.htwkleipzig.mmdb.service.ElasticsearchService;
import fr.pilato.spring.elasticsearch.ElasticsearchTransportClientFactoryBean;

@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

    TransportClient client;
    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchServiceImpl.class);

    @Autowired
    ElasticsearchTransportClientFactoryBean transportFactoryBean;

    @Override
    public void init() {
        logger.info("init TransportClient");
        for (String node : transportFactoryBean.getEsNodes()) {
            logger.info("node {}", node);
        }
        try {
            client = (TransportClient) transportFactoryBean.getObject();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            // create an index called myindex
            logger.info("create an index called myindex");
            client.admin().indices().create(new CreateIndexRequest("myindex")).actionGet();
        } catch (IndexAlreadyExistsException ex) {
            logger.warn("already exists", ex);
        }
    }

    @Override
    public GetResponse get(String indexName, String type, String id) {
        logger.debug("get a resource from index {}, type {}, id {}", new Object[] { indexName, type, id });
        GetResponse rsp = client.prepareGet(indexName, type, id).
                execute().actionGet();
        return rsp;
    }

    @Override
    public boolean save(String indexName, String type, String id, Map<String, Object> data) {
        IndexRequestBuilder irb = client.prepareIndex(indexName, type, id).
                setSource(data);
        try {
            logger.debug("try to save the content to es");
            irb.execute().actionGet();
        } catch (ElasticSearchException ese) {
            logger.error("Error while saving it", ese);
            return false;
        }
        return true;
    }

    @Override
    public boolean saveJson(String indexName, String type, String id, XContentBuilder content) {
        IndexRequestBuilder irb = client.prepareIndex(indexName, type, id).
                setSource(content);
        try {
            irb.execute().actionGet();
        } catch (ElasticSearchException ese) {
            logger.error("Error while saving it", ese);
            return false;
        }
        return true;
    }

    @Override
    public DeleteResponse delete(String indexName, String type, String id) {
        DeleteResponse response = client.prepareDelete(indexName, type, id)
                .execute()
                .actionGet();

        return response;
    }

    @Override
    public SearchResponse searchsearch(String indexName, QueryBuilder query) {
        SearchRequestBuilder builder = client.prepareSearch(indexName);
        builder.setQuery(query);

        return builder.execute().actionGet();
    }

    @Override
    public void onShutdown() {
        client.close();
    }

}
