/**
 * 
 */
package de.htwkleipzig.mmdb.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteResponse;
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

    /**
     * 
     * @param author
     * @return
     * @throws IOException
     */
    public boolean saveAthuor(Author author) throws IOException;

    /**
     * 
     * @param author
     * @return
     * @throws IOException
     */
    public boolean updateAuthor(Author author) throws IOException;

    public void onShutdown();
}
