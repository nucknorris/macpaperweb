package de.htwkleipzig.mmdb.model;

import java.io.Serializable;

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
    private String title;
    private String name;
    private String lastname;
    private String email;
    private University university;

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
    public University getUniversity() {
        if (university == null) {
            university = new University();
        }
        return university;
    }

    /**
     * @param university
     *            the universaty to set
     */
    public void setUniversity(University university) {
        this.university = university;
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
                .append(lastname).append(", email=").append(email).append(", universaty=").append(university)
                .append("]");
        return builder.toString();
    }
}
