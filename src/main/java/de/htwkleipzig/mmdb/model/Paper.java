/**
 * 
 */
package de.htwkleipzig.mmdb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author spinner0815
 * 
 */
public class Paper implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 275195800963878375L;
    /**
     * @uml.property  name="title"
     */
    private String title;
    /** 
     * @uml.property name="authors"
     * @uml.associationEnd multiplicity="(1 -1)" ordering="true" inverse="paper:de.htwkleipzig.mmdb.model.Author"
     */
    private List<Author> authors;
    /**
     * @uml.property  name="createDate"
     */
    private Date createDate;
    /**
     * @uml.property  name="keywords"
     * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
     */
    private List<String> keywords;
    /**
     * @uml.property  name="kindOf"
     */
    private String kindOf;
    /**
     * @uml.property  name="content"
     */
    private String content;
    /**
     * @uml.property  name="latexBib"
     */
    private String latexBib;
    /**
     * @uml.property  name="uploadDate"
     */
    private Date uploadDate;
    /** 
     * @uml.property name="ddc"
     * @uml.associationEnd inverse="paper:de.htwkleipzig.mmdb.model.DeweyDecimalClassification"
     */
    private DeweyDecimalClassification ddc;

    /**
     * @return  the latexBib
     * @uml.property  name="latexBib"
     */
    public String getLatexBib() {
        return latexBib;
    }

    /**
     * @param latexBib  the latexBib to set
     * @uml.property  name="latexBib"
     */
    public void setLatexBib(String latexBib) {
        this.latexBib = latexBib;
    }

    /**
     * @return  the title
     * @uml.property  name="title"
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title  the title to set
     * @uml.property  name="title"
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** 
     * @return the authors
     * @uml.property  name="authors"
     */
    public List<Author> getAuthors() {
        return authors;
    }

    /** 
     * @param authors the authors to set
     * @uml.property  name="authors"
     */
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    /**
     * @return  the createDate
     * @uml.property  name="createDate"
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate  the createDate to set
     * @uml.property  name="createDate"
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
     * @return  the kindOf
     * @uml.property  name="kindOf"
     */
    public String getKindOf() {
        return kindOf;
    }

    /**
     * @param kindOf  the kindOf to set
     * @uml.property  name="kindOf"
     */
    public void setKindOf(String kindOf) {
        this.kindOf = kindOf;
    }

    /**
     * @return  the content
     * @uml.property  name="content"
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content  the content to set
     * @uml.property  name="content"
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return  the uploadDate
     * @uml.property  name="uploadDate"
     */
    public Date getUploadDate() {
        return uploadDate;
    }

    /**
     * @param uploadDate  the uploadDate to set
     * @uml.property  name="uploadDate"
     */
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
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

    /** 
     * @return  the ddc
     * @uml.property  name="ddc"
     */
    public DeweyDecimalClassification getDdc() {
        return ddc;
    }

    /** 
     * @param ddc  the ddc to set
     * @uml.property  name="ddc"
     */
    public void setDdc(DeweyDecimalClassification ddc) {
        this.ddc = ddc;
    }

    /** 
     * @return the authors
     * @uml.property  name="authors"
     */
    public Author getAuthors(int i) {
        return (Author) authors.get(i);
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     * @return  an iterator over the elements in this list in proper sequence.
     * @see java.util.List#iterator()
     * @uml.property  name="authors"
     */
    public Iterator<Author> authorsIterator() {
        return authors.iterator();
    }

    /**
     * Returns <tt>true</tt> if this list contains no elements.
     * @return  <tt>true</tt> if this list contains no elements.
     * @see java.util.List#isEmpty()
     * @uml.property  name="authors"
     */
    public boolean isAuthorsEmpty() {
        return authors.isEmpty();
    }

    /**
     * Returns <tt>true</tt> if this list contains the specified element.
     * @param element  element whose presence in this list is to be tested.
     * @return  <tt>true</tt> if this list contains the specified element.
     * @see java.util.List#contains(Object)
     * @uml.property  name="authors"
     */
    public boolean containsAuthors(Author author) {
        return authors.contains(author);
    }

    /**
     * Returns <tt>true</tt> if this list contains all of the elements of the specified collection.
     * @param elements  collection to be checked for containment in this list.
     * @return  <tt>true</tt> if this list contains all of the elements of the specified collection.
     * @see java.util.List#containsAll(Collection)
     * @uml.property  name="authors"
     */
    public boolean containsAllAuthors(Collection<? extends Author> authors) {
        return this.authors.containsAll(authors);
    }

    /**
     * Returns the number of elements in this list.
     * @return  the number of elements in this list.
     * @see java.util.List#size()
     * @uml.property  name="authors"
     */
    public int authorsSize() {
        return authors.size();
    }

    /**
     * Returns an array containing all of the elements in this list in proper sequence.
     * @return  an array containing all of the elements in this list in proper sequence.
     * @see java.util.List#toArray()
     * @uml.property  name="authors"
     */
    public Author[] authorsToArray() {
        return authors.toArray(new Author[authors.size()]);
    }

    /**
     * Returns an array containing all of the elements in this list in proper sequence; the runtime type of the returned array is that of the specified array.
     * @param a  the array into which the elements of this list are to be stored.
     * @return  an array containing all of the elements in this list in proper sequence.
     * @see java.util.List#toArray(Object[])
     * @uml.property  name="authors"
     */
    public <T extends Author> T[] authorsToArray(T[] authors) {
        return (T[]) this.authors.toArray(authors);
    }

    /**
     * Inserts the specified element at the specified position in this list (optional operation)
     * @param index  index at which the specified element is to be inserted.
     * @param element  element to be inserted.
     * @see java.util.List#add(int,Object)
     * @uml.property  name="authors"
     */
    public void addAuthors(int index, Author author) {
        authors.add(index, author);
    }

    /**
     * Appends the specified element to the end of this list (optional operation).
     * @param element  element to be appended to this list.
     * @return  <tt>true</tt> (as per the general contract of the <tt>Collection.add</tt> method).
     * @see java.util.List#add(Object)
     * @uml.property  name="authors"
     */
    public boolean addAuthors(Author author) {
        return authors.add(author);
    }

    /**
     * Removes the element at the specified position in this list (optional operation).
     * @param index  the index of the element to removed.
     * @return  the element previously at the specified position.
     * @see java.util.List#remove(int)
     * @uml.property  name="authors"
     */
    public Object removeAuthors(int index) {
        return authors.remove(index);
    }

    /**
     * Removes the first occurrence in this list of the specified element  (optional operation).
     * @param element  element to be removed from this list, if present.
     * @return  <tt>true</tt> if this list contained the specified element.
     * @see java.util.List#remove(Object)
     * @uml.property  name="authors"
     */
    public boolean removeAuthors(Author author) {
        return authors.remove(author);
    }

    /**
     * Removes all of the elements from this list (optional operation).
     * @see java.util.List#clear()
     * @uml.property  name="authors"
     */
    public void clearAuthors() {
        authors.clear();
    }
}
