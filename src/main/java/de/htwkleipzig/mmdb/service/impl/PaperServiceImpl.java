package de.htwkleipzig.mmdb.service.impl;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;

import de.htwkleipzig.mmdb.model.Paper;
import de.htwkleipzig.mmdb.service.PaperService;
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
            // TODO Auto-generated catch block
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
            return source2Paper(ret);
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
            b = paper2Json(paper);
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
        LOGGER.debug("update author with id {}", paper.getPaperId());
        if (paperExists(paper.getPaperId())) {
            return save(paper);
        }
        LOGGER.error("author with id {} doesn't exists", paper.getPaperId());
        return false;
    }

    @Override
    public boolean paperExists(String authorId) {
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

    private XContentBuilder paper2Json(Paper paper) throws IOException {
        LOGGER.debug("create the json object from Paper");
        XContentBuilder b = jsonBuilder().startObject();
        b.field("paperId", paper.getPaperId());
        b.field("title", paper.getTitle() == null ? "empty" : paper.getTitle());
        b.field("paperAbstract", paper.getPaperAbstract() == null ? "empty" : paper.getPaperAbstract());

        List<String> authorIds = new ArrayList<String>();
        authorIds.add("empty");
        b.field("authorsId", paper.getAuthors() == null ? authorIds : paper.getAuthors());
        b.field("createDate",
                paper.getCreateDate() == null ? new Date(System.currentTimeMillis()) : paper.getCreateDate());

        List<String> keywords = new ArrayList<String>();
        authorIds.add("empty");
        b.field("keywords", paper.getKeywords() == null ? keywords : paper.getKeywords());
        b.field("kindOf", paper.getKindOf() == null ? "empty" : paper.getKindOf());
        b.field("content", paper.getContent() == null ? "empty" : paper.getContent());
        b.field("latexBib", paper.getLatexBib() == null ? "empty" : paper.getLatexBib());
        b.field("uploadDate",
                paper.getUploadDate() == null ? new Date(System.currentTimeMillis()) : paper.getUploadDate());
        b.field("fileName", paper.getFileName() == null ? "empty" : paper.getFileName());
        // b.field("ddc", paper.getDdc()); TODO - currently disabled
        LOGGER.debug(b.string());
        return b;
    }

    private Paper source2Paper(Map<String, Object> source) {
        LOGGER.debug("convert from source to paper");
        Paper paper = new Paper();
        paper.setPaperId((String) source.get("paperId"));
        paper.setTitle((String) source.get("title"));
        paper.setPaperAbstract((String) source.get("paperAbstract"));
        paper.setAuthors((List<String>) source.get("authorsId"));

        DateTime dt = DateTime.parse((String) source.get("createDate"));// 2012-05-27T17:35:05.989Z
        paper.setCreateDate(dt.toDate());
        paper.setKeywords((List<String>) source.get("keywords"));
        paper.setKindOf((String) source.get("kindOf"));
        paper.setContent((String) source.get("content"));
        paper.setLatexBib((String) source.get("latexBib"));

        dt = DateTime.parse((String) source.get("uploadDate"));// 2012-05-27T17:35:05.989Z
        paper.setUploadDate(dt.toDate());

        paper.setFileName((String) source.get("street2"));
        // paper.setDdc((String) source.get("ddc")); TODO - currently disabled
        LOGGER.debug("finished converting {}", paper.toString());
        return paper;
    }
}
