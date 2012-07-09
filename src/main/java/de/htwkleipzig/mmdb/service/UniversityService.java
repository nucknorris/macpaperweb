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

import de.htwkleipzig.mmdb.model.University;

/**
 * @author spinner0815
 * 
 */
public interface UniversityService extends Serializable {

    void init();

    /**
     * get a resource from the elastic search
     * 
     * @param id
     * @return the resource
     */
    University get(String id);

    List<University> getAll();

    /**
     * @deprecated use {@link #save(University)} instead
     * @param id
     * @param data
     * @return
     */
    @Deprecated
    boolean save(String id, Map<String, Object> data);

    /**
     * save the university to es and update the index
     * 
     * @param university
     *            the university to save
     * @return true if everything was fine, false otherwise
     */
    boolean save(University university);

    /**
     * @deprecated use {@link #save(University)} instead
     * @param id
     * @param content
     * @return
     */
    @Deprecated
    boolean saveJson(String id, XContentBuilder content);

    /**
     * Update the given university (the id must match).<br>
     * You can change everything except the id. <br>
     * Best way you get the university by calling {@link #get(String)} then change the fields and call this method
     * 
     * @param university
     *            the university to update
     * @return true if update was successful
     */
    boolean updateUniversity(University university);

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
    boolean delete(String id);

    /**
     * checks if the university with this id exists
     * 
     * @param universityId
     * @return true if the university exists
     */
    boolean universityExists(String universityId);

    public void onShutdown();

}
