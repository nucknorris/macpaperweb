package de.htwkleipzig.mmdb.model;

import java.io.Serializable;
import java.util.Iterator;
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
    /**
     * @uml.property name="title"
     */
    private String title;
    /**
     * @uml.property name="name"
     */
    private String name;
    /**
     * @uml.property name="lastname"
     */
    private String lastname;
    /**
     * @uml.property name="email"
     */
    private String email;
    /**
     * @uml.property name="university"
     * @uml.associationEnd inverse="author:de.htwkleipzig.mmdb.model.University"
     */
    private University university;

    /**
     * @return the title
     * @uml.property name="title"
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     * @uml.property name="title"
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the name
     * @uml.property name="name"
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     * @uml.property name="name"
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the lastname
     * @uml.property name="lastname"
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname
     *            the lastname to set
     * @uml.property name="lastname"
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
     * @uml.property name="email"
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the universaty
     * @uml.property name="university"
     */
    public University getUniversity() {
        return university;
    }

    /**
     * @param university
     *            the universaty to set
     * @uml.property name="university"
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

    /**
     * @uml.property name="paper"
     * @uml.associationEnd multiplicity="(1 -1)" ordering="true" inverse="authors:java.util.String"
     */
    private List<String> paper;

    /**
     * Getter of the property <tt>paper</tt>
     * 
     * @return Returns the paper.
     * @uml.property name="paper"
     */
    public List<String> getPaper() {
        return paper;
    }

    /**
     * Getter of the property <tt>paper</tt>
     * 
     * @return Returns the paper.
     * @uml.property name="paper"
     */
    public String getPaper(int i) {
        return (String) paper.get(i);
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     * 
     * @return an iterator over the elements in this list in proper sequence.
     * @see java.util.List#iterator()
     * @uml.property name="paper"
     */
    public Iterator<String> paperIterator() {
        return paper.iterator();
    }

    /**
     * Returns <tt>true</tt> if this list contains no elements.
     * 
     * @return <tt>true</tt> if this list contains no elements.
     * @see java.util.List#isEmpty()
     * @uml.property name="paper"
     */
    public boolean isPaperEmpty() {
        return paper.isEmpty();
    }

    /**
     * Returns <tt>true</tt> if this list contains the specified element.
     * 
     * @param element
     *            element whose presence in this list is to be tested.
     * @return <tt>true</tt> if this list contains the specified element.
     * @see java.util.List#contains(Object)
     * @uml.property name="paper"
     */
    public boolean containsPaper(String String) {
        return paper.contains(String);
    }

    /**
     * Returns <tt>true</tt> if this list contains all of the elements of the specified String.
     * 
     * @param elements
     *            String to be checked for containment in this list.
     * @return <tt>true</tt> if this list contains all of the elements of the specified String.
     * @see java.util.List#containsAll(String)
     * @uml.property name="paper"
     */
    public boolean containsAllPaper(List<? extends String> paper) {
        return this.paper.containsAll(paper);
    }

    /**
     * Returns the number of elements in this list.
     * 
     * @return the number of elements in this list.
     * @see java.util.List#size()
     * @uml.property name="paper"
     */
    public int paperSize() {
        return paper.size();
    }

    /**
     * Inserts the specified element at the specified position in this list (optional operation)
     * 
     * @param index
     *            index at which the specified element is to be inserted.
     * @param element
     *            element to be inserted.
     * @see java.util.List#add(int,Object)
     * @uml.property name="paper"
     */
    public void addPaper(int index, String String) {
        paper.add(index, String);
    }

    /**
     * Appends the specified element to the end of this list (optional operation).
     * 
     * @param element
     *            element to be appended to this list.
     * @return <tt>true</tt> (as per the general contract of the <tt>String.add</tt> method).
     * @see java.util.List#add(Object)
     * @uml.property name="paper"
     */
    public boolean addPaper(String String) {
        return paper.add(String);
    }

    /**
     * Removes the element at the specified position in this list (optional operation).
     * 
     * @param index
     *            the index of the element to removed.
     * @return the element previously at the specified position.
     * @see java.util.List#remove(int)
     * @uml.property name="paper"
     */
    public Object removePaper(int index) {
        return paper.remove(index);
    }

    /**
     * Removes the first occurrence in this list of the specified element (optional operation).
     * 
     * @param element
     *            element to be removed from this list, if present.
     * @return <tt>true</tt> if this list contained the specified element.
     * @see java.util.List#remove(Object)
     * @uml.property name="paper"
     */
    public boolean removePaper(String String) {
        return paper.remove(String);
    }

    /**
     * Removes all of the elements from this list (optional operation).
     * 
     * @see java.util.List#clear()
     * @uml.property name="paper"
     */
    public void clearPaper() {
        paper.clear();
    }

    /**
     * Setter of the property <tt>paper</tt>
     * 
     * @param paper
     *            the paper to set.
     * @uml.property name="paper"
     */
    public void setPaper(List<String> paper) {
        this.paper = paper;
    }

}
