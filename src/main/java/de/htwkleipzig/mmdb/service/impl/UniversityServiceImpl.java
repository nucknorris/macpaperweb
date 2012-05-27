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

import de.htwkleipzig.mmdb.model.University;
import de.htwkleipzig.mmdb.service.UniversityService;
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
            return source2University(ret);
        } else {
            LOGGER.debug("University doesn't exist");
            return null;
        }
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
            b = university2Json(university);
        } catch (IOException e) {
            LOGGER.error("error while parsing the university to xcontent {}", e.fillInStackTrace());
        }
        if (b == null) {
            LOGGER.warn("something wrong while dao2json move from university with id {}", university.getUniversityId());
            return false;
        }
        IndexRequestBuilder irb = client.prepareIndex(INDEX_UNIVERSITY_NAME, INDEX_UNIVERSITY_TYPE,
                university.getUniversityId()).setSource(b);
        irb.execute().actionGet();
        return true;
    }

    @Override
    public boolean updateUniversity(University university) {
        LOGGER.debug("update author with id {}", university.getUniversityId());
        if (universityExists(university.getUniversityId())) {
            return save(university);
        }
        LOGGER.error("author with id {} doesn't exists", university.getUniversityId());
        return false;
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

    private XContentBuilder university2Json(University university) throws IOException {
        LOGGER.debug("create the json object from University");
        XContentBuilder b = jsonBuilder().startObject();
        b.field("universityId", university.getUniversityId());
        b.field("city", university.getCity());
        b.field("country", university.getCountry());
        b.field("name", university.getName());
        b.field("housenumber", university.getHousenumber());
        b.field("postcode", university.getPostcode());
        b.field("street", university.getStreet());
        b.field("street2", university.getStreet2());
        b.field("authors", university.getAuthorIds());
        LOGGER.debug(b.string());
        return b;
    }

    private University source2University(Map<String, Object> source) {
        LOGGER.debug("convert from source to university");
        University university = new University();
        university.setUniversityId((String) source.get("universityId"));
        university.setCity((String) source.get("city"));
        university.setCountry((String) source.get("country"));
        university.setName((String) source.get("name"));
        university.setHousenumber((String) source.get("housenumber"));
        university.setPostcode((String) source.get("postcode"));
        university.setStreet((String) source.get("street"));
        university.setStreet2((String) source.get("street2"));
        university.setAuthorIds((List<String>) source.get("authors"));
        LOGGER.debug("finished converting {}", university.toString());
        return university;
    }
}
