/**
 * 
 */
package de.htwkleipzig.mmdb.service;

import java.util.Map;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * @author spinner0815
 * 
 */
public interface ElasticsearchService {

    void init();

    /**
     * get a resource from the elastic search
     * 
     * @param indexName
     * @param type
     * @param id
     * @return the resource
     */
    GetResponse get(String indexName, String type, String id);

    /**
     * save a object to es
     * 
     * @param indexName
     * @param type
     * @param id
     * @param data
     * @return
     */
    boolean save(String indexName, String type, String id, Map<String, Object> data);

    /**
     * 
     * @param indexName
     * @param query
     * @return
     */
    SearchResponse searchsearch(String indexName, QueryBuilder query);

    /**
     * 
     * @param indexName
     * @param type
     * @param id
     * @return
     */
    DeleteResponse delete(String indexName, String type, String id);

    /**
     * 
     * @param indexName
     * @param type
     * @param id
     * @param content
     * @return
     */
    boolean saveJson(String indexName, String type, String id, XContentBuilder content);
}
