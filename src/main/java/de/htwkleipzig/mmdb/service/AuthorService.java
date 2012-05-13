/**
 * 
 */
package de.htwkleipzig.mmdb.service;

import java.io.Serializable;
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
public interface AuthorService extends Serializable {

    void init();

    /**
     * get a resource from the elastic search
     * 
     * @param id
     * @return the resource
     */
    GetResponse get(String id);

    /**
     * save a object to es
     * 
     * @param id
     * @param data
     * @return
     */
    boolean save(String id, Map<String, Object> data);

    /**
     * 
     * @param query
     * @return
     */
    SearchResponse search(QueryBuilder query);

    /**
     * 
     * @param id
     * @return
     */
    DeleteResponse delete(String id);

    /**
     * @param id
     * @param content
     * @return
     */
    boolean saveJson(String id, XContentBuilder content);

    public void onShutdown();
}
