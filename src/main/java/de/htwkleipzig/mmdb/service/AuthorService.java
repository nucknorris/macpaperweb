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

import de.htwkleipzig.mmdb.model.Author;

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
     * @return the Author
     */
    Author get(String id);

    List<Author> getAll();

    /**
     * @deprecated use {@link #save(Author)} instead
     * @param id
     * @param content
     * @return
     */
    @Deprecated
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
    boolean delete(String id);

    /**
     * @deprecated use {@link #save(Author)} instead
     * @param id
     * @param content
     * @return
     */
    @Deprecated
    boolean saveJson(String id, XContentBuilder content);

    /**
     * save the author to es and update the index
     * 
     * @param author
     *            the author to save
     * @return true if everything was fine, false otherwise
     */
    public boolean save(Author author);

    /**
     * Update the given author (the id must match).<br>
     * You can change everything except the id. <br>
     * Best way you get the author by calling {@link #get(String)} then change the fields and call this method
     * 
     * @param author
     *            the author to update
     * @return true if update was successful
     */
    public boolean updateAuthor(Author author);

    /**
     * checks if the author with this id exists
     * 
     * @param authorId
     * @return true if the author exists
     */
    boolean authorExists(String authorId);

    public void onShutdown();

}
