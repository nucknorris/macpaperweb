/**
 * 
 */
package de.htwkleipzig.mmdb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author spinner0815
 * 
 */
public class Paper implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 275195800963878375L;
    private String title;
    private List<Author> authors;
    private Date createDate;
    private List<String> keywords;
    private String kindOf;
    private String content;

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the authors
     */
    public List<Author> getAuthors() {
        if (authors == null) {
            authors = new ArrayList<Author>();
        }
        return authors;
    }

    /**
     * @param authors
     *            the authors to set
     */
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     *            the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the keywords
     */
    public List<String> getKeywords() {
        if (keywords == null) {
            keywords = new ArrayList<String>();
        }
        return keywords;
    }

    /**
     * @param keywords
     *            the keywords to set
     */
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * @return the kindOf
     */
    public String getKindOf() {
        return kindOf;
    }

    /**
     * @param kindOf
     *            the kindOf to set
     */
    public void setKindOf(String kindOf) {
        this.kindOf = kindOf;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Paper [title=").append(title).append(", authors=").append(authors).append(", createDate=")
                .append(createDate).append(", keywords=").append(keywords).append(", kindOf=").append(kindOf)
                .append("]");
        return builder.toString();
    }
}
