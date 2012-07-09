/**
 * 
 */
package de.htwkleipzig.mmdb.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import de.htwkleipzig.mmdb.model.Paper;

/**
 * @author spinner0815
 * 
 */
public interface PaperService extends Serializable {

    void init();

    /**
     * get a resource from the elastic search
     * 
     * @param id
     * @return the resource
     */
    Paper get(String id);

    List<Paper> getAll();

    /**
     * save the paper, mapping is in the hashmap?
     * 
     * @deprecated use {@link #save(Paper)} instead
     * @param id
     * @param data
     * @return
     */
    @Deprecated
    boolean save(String id, Map<String, Object> data);

    /**
     * save the paper to es and update the index
     * 
     * @param paper
     *            the paper to save
     * @return true if everything was fine, false otherwise
     */
    boolean save(Paper paper);

    /**
     * save the paper in json format
     * 
     * @deprecated use {@link #save(Paper)} instead
     * @param id
     * @param content
     * @return
     */
    @Deprecated
    boolean saveJson(String id, XContentBuilder content);

    /**
     * Update the given paper (the id must match).<br>
     * You can change everything except the id. <br>
     * Best way you get the paper by calling {@link #get(String)} then change the fields and call this method
     * 
     * @param paper
     *            the paper to update
     * @return true if update was successful
     */
    boolean updatePaper(Paper paper);

    /**
     * search for a document
     * 
     * @param query
     *            the querybuilder with the search query
     * @return the result in {@link SearchResponse} format
     */
    SearchResponse search(QueryBuilder queries);

    /**
     * delete the paper with the given id
     * 
     * @param id
     *            the id for the paper to delete
     * @return true if the delete was successful
     */
    boolean delete(String id);

    /**
     * checks if the paper with this id exists
     * 
     * @param paperId
     * @return true if the paper exists
     */
    boolean paperExists(String paperId);

    public void onShutdown();
}
