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
import org.elasticsearch.action.index.IndexResponse;
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

import de.htwkleipzig.mmdb.model.University;
import de.htwkleipzig.mmdb.service.UniversityService;
import de.htwkleipzig.mmdb.util.UniversityHelper;
import de.htwkleipzig.mmdb.util.Utilities;
import fr.pilato.spring.elasticsearch.ElasticsearchTransportClientFactoryBean;

@Service
public class UniversityServiceImpl implements UniversityService {

    /**
     * 
     */
    private static final long serialVersionUID = -5057845859337839315L;
    private static final Logger LOGGER = LoggerFactory.getLogger(UniversityServiceImpl.class);
    private static String INDEX_UNIVERSITY_NAME = Utilities.getProperty("index.university.name");
    private static String INDEX_UNIVERSITY_TYPE = Utilities.getProperty("index.university.type");

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
            LOGGER.info("create an index called {}", INDEX_UNIVERSITY_NAME);
            // client.admin().indices().create(new CreateIndexRequest(INDEX_UNIVERSITY_NAME)).actionGet();
            IndicesAdminClient indAdminClient = client.admin().indices();

            indAdminClient.create(createAllIndizes(INDEX_UNIVERSITY_NAME, indAdminClient)).actionGet();

        } catch (IndexAlreadyExistsException ex) {
            LOGGER.debug("{} already exists", INDEX_UNIVERSITY_NAME);
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
    public University get(String id) {
        LOGGER.debug("get a resource from index {}, type {}, id {}", new Object[] { INDEX_UNIVERSITY_NAME,
                INDEX_UNIVERSITY_TYPE, id });
        GetResponse rsp = client.prepareGet(INDEX_UNIVERSITY_NAME, INDEX_UNIVERSITY_TYPE, id).execute().actionGet();
        Map<String, Object> ret = rsp.getSource();
        LOGGER.debug("try to map the response to University");
        if (rsp.exists()) {
            LOGGER.debug("University exists and will be returned");
            return UniversityHelper.source2University(ret);
        } else {
            LOGGER.debug("University doesn't exist");
            return null;
        }
    }

    @Override
    public List<University> getAll() {
        LOGGER.debug("get all universitys from index {}, type {}", new Object[] { INDEX_UNIVERSITY_NAME,
                INDEX_UNIVERSITY_TYPE });
        SearchRequestBuilder builder = client.prepareSearch(INDEX_UNIVERSITY_NAME);
        MatchAllQueryBuilder qb = new MatchAllQueryBuilder();

        builder.setQuery(qb);
        SearchResponse actionGet = builder.execute().actionGet();

        List<University> universitys = new ArrayList<University>();

        for (SearchHit hit : actionGet.getHits().getHits()) {
            if (hit.isSourceEmpty()) {
                LOGGER.info("source is empty");
            }
            LOGGER.info("id of the university {}", hit.getId());
            LOGGER.info("score of the hit {}", hit.getScore());

            Map<String, Object> resultMap = hit.sourceAsMap();

            University universityObject = UniversityHelper.source2University(resultMap);

            LOGGER.debug("university: {}", universityObject.toString());
            universitys.add(universityObject);

        }
        return universitys;

    }

    @Override
    @Deprecated
    public boolean save(String id, Map<String, Object> data) {
        IndexRequestBuilder irb = client.prepareIndex(INDEX_UNIVERSITY_NAME, INDEX_UNIVERSITY_TYPE, id).setSource(data);
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
        IndexRequestBuilder irb = client.prepareIndex(INDEX_UNIVERSITY_NAME, INDEX_UNIVERSITY_TYPE, id).setSource(
                content);
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
        DeleteResponse response = client.prepareDelete(INDEX_UNIVERSITY_NAME, INDEX_UNIVERSITY_TYPE, id).execute()
                .actionGet();

        if (response.notFound()) {
            LOGGER.debug("there was no document foudn with the id {}", id);
            return false;
        }
        return true;
    }

    @Override
    public SearchResponse search(QueryBuilder query) {
        SearchRequestBuilder builder = client.prepareSearch(INDEX_UNIVERSITY_NAME);
        builder.setQuery(query);

        return builder.execute().actionGet();
    }

    @Override
    public boolean save(University university) {
        if (university == null) {
            LOGGER.debug("The university is NULL");
            throw new IllegalArgumentException("The university is NULL");
        }
        LOGGER.debug("try to save the university with id {}", university.getUniversityId());
        XContentBuilder b = null;
        try {
            b = UniversityHelper.university2Json(university);
        } catch (IOException e) {
            LOGGER.error("error while parsing the university to xcontent {}", e.fillInStackTrace());
        }
        if (b == null) {
            LOGGER.warn("something wrong while dao2json move from university with id {}", university.getUniversityId());
            return false;
        }
        IndexRequestBuilder irb = client.prepareIndex(INDEX_UNIVERSITY_NAME, INDEX_UNIVERSITY_TYPE,
                university.getUniversityId()).setSource(b);
        IndexResponse actionGet = irb.execute().actionGet();
        LOGGER.debug("save result {}", actionGet.getId());
        return true;
    }

    @Override
    public boolean updateUniversity(University university) {
        LOGGER.debug("update university with id {}", university.getUniversityId());
        if (universityExists(university.getUniversityId())) {
            return save(university);
        }
        LOGGER.debug("university with id {} doesn't exists", university.getUniversityId());
        return save(university);
    }

    @Override
    public boolean universityExists(String authorId) {
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
