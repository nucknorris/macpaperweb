package de.htwkleipzig.mmdb.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author spinner0815
 * 
 */
public class Author implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1903387717479229994L;
    private String authorId;
    private String title;
    private String name;
    private String lastname;
    private String email;
    private String universityId;
    private List<String> paperIds;

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname
     *            the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the email
     * @uml.property name="email"
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the universaty
     */
    public String getUniversityId() {
        return universityId;
    }

    /**
     * @param university
     *            the universaty to set
     */
    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    /**
     * @return the paperId
     */
    public List<String> getPaperIds() {
        return paperIds;
    }

    /**
     * @param paperId
     *            the paperId to set
     */
    public void setPaperIds(List<String> paperIds) {
        this.paperIds = paperIds;
    }

    /**
     * @return the authorId
     */
    public String getAuthorId() {
        return authorId;
    }

    /**
     * @param authorId
     *            the authorId to set
     */
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Author [title=").append(title).append(", name=").append(name).append(", lastname=")
                .append(lastname).append(", email=").append(email).append(", universaty=").append(universityId)
                .append("]");
        return builder.toString();
    }

}
