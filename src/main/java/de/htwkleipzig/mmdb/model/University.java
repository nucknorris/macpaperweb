/**
 * 
 */
package de.htwkleipzig.mmdb.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author spinner0815
 * 
 */
public class University implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8667452943986443594L;

    /**
     * @uml.property  name="city"
     */
    private String city;
    /**
     * @uml.property  name="postcode"
     */
    private String postcode;
    /**
     * @uml.property  name="street"
     */
    private String street;
    /**
     * @uml.property  name="street2"
     */
    private String street2;
    /**
     * @uml.property  name="housenumber"
     */
    private String housenumber;
    /**
     * @uml.property  name="country"
     */
    private String country;
    /**
     * @uml.property  name="name"
     */
    private String name;

    /**
     * @return  the city
     * @uml.property  name="city"
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city  the city to set
     * @uml.property  name="city"
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return  the postcode
     * @uml.property  name="postcode"
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * @param postcode  the postcode to set
     * @uml.property  name="postcode"
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * @return  the street
     * @uml.property  name="street"
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street  the street to set
     * @uml.property  name="street"
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @return  the street2
     * @uml.property  name="street2"
     */
    public String getStreet2() {
        return street2;
    }

    /**
     * @param street2  the street2 to set
     * @uml.property  name="street2"
     */
    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    /**
     * @return  the housenumber
     * @uml.property  name="housenumber"
     */
    public String getHousenumber() {
        return housenumber;
    }

    /**
     * @param housenumber  the housenumber to set
     * @uml.property  name="housenumber"
     */
    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }

    /**
     * @return  the country
     * @uml.property  name="country"
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country  the country to set
     * @uml.property  name="country"
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return  the name
     * @uml.property  name="name"
     */
    public String getName() {
        return name;
    }

    /**
     * @param name  the name to set
     * @uml.property  name="name"
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Universaty [city=").append(city).append(", postcode=").append(postcode).append(", street=")
                .append(street).append(", street2=").append(street2).append(", housenumber=").append(housenumber)
                .append(", country=").append(country).append(", name=").append(name).append("]");
        return builder.toString();
    }

    /**
     * @uml.property  name="author"
     * @uml.associationEnd  multiplicity="(0 -1)" ordering="true" inverse="university:de.htwkleipzig.mmdb.model.Author"
     */
    private List<Author> author;

    /**
     * Getter of the property <tt>author</tt>
     * @return  Returns the author.
     * @uml.property  name="author"
     */
    public List<Author> getAuthor() {
        return author;
    }

    /**
     * Returns the element at the specified position in this list.
     * @param index  index of element to return.
     * @return  the element at the specified position in this list.
     * @see java.util.List#get(int)
     * @uml.property  name="author"
     */
    public Author getAuthor(int i) {
        return (Author) author.get(i);
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     * @return  an iterator over the elements in this list in proper sequence.
     * @see java.util.List#iterator()
     * @uml.property  name="author"
     */
    public Iterator<Author> authorIterator() {
        return author.iterator();
    }

    /**
     * Returns <tt>true</tt> if this list contains no elements.
     * @return  <tt>true</tt> if this list contains no elements.
     * @see java.util.List#isEmpty()
     * @uml.property  name="author"
     */
    public boolean isAuthorEmpty() {
        return author.isEmpty();
    }

    /**
     * Returns <tt>true</tt> if this list contains the specified element.
     * @param element  element whose presence in this list is to be tested.
     * @return  <tt>true</tt> if this list contains the specified element.
     * @see java.util.List#contains(Object)
     * @uml.property  name="author"
     */
    public boolean containsAuthor(Author author) {
        return this.author.contains(author);
    }

    /**
     * Returns <tt>true</tt> if this list contains all of the elements of the specified collection.
     * @param elements  collection to be checked for containment in this list.
     * @return  <tt>true</tt> if this list contains all of the elements of the specified collection.
     * @see java.util.List#containsAll(Collection)
     * @uml.property  name="author"
     */
    public boolean containsAllAuthor(Collection<? extends Author> author) {
        return this.author.containsAll(author);
    }

    /**
     * Returns the number of elements in this list.
     * @return  the number of elements in this list.
     * @see java.util.List#size()
     * @uml.property  name="author"
     */
    public int authorSize() {
        return author.size();
    }

    /**
     * Returns an array containing all of the elements in this list in proper sequence.
     * @return  an array containing all of the elements in this list in proper sequence.
     * @see java.util.List#toArray()
     * @uml.property  name="author"
     */
    public Author[] authorToArray() {
        return author.toArray(new Author[author.size()]);
    }

    /**
     * Returns an array containing all of the elements in this list in proper sequence; the runtime type of the returned array is that of the specified array.
     * @param a  the array into which the elements of this list are to be stored.
     * @return  an array containing all of the elements in this list in proper sequence.
     * @see java.util.List#toArray(Object[])
     * @uml.property  name="author"
     */
    public <T extends Author> T[] authorToArray(T[] author) {
        return (T[]) this.author.toArray(author);
    }

    /**
     * Inserts the specified element at the specified position in this list (optional operation)
     * @param index  index at which the specified element is to be inserted.
     * @param element  element to be inserted.
     * @see java.util.List#add(int,Object)
     * @uml.property  name="author"
     */
    public void addAuthor(int index, Author author) {
        this.author.add(index, author);
    }

    /**
     * Appends the specified element to the end of this list (optional operation).
     * @param element  element to be appended to this list.
     * @return  <tt>true</tt> (as per the general contract of the <tt>Collection.add</tt> method).
     * @see java.util.List#add(Object)
     * @uml.property  name="author"
     */
    public boolean addAuthor(Author author) {
        return this.author.add(author);
    }

    /**
     * Removes the element at the specified position in this list (optional operation).
     * @param index  the index of the element to removed.
     * @return  the element previously at the specified position.
     * @see java.util.List#remove(int)
     * @uml.property  name="author"
     */
    public Object removeAuthor(int index) {
        return author.remove(index);
    }

    /**
     * Removes the first occurrence in this list of the specified element  (optional operation).
     * @param element  element to be removed from this list, if present.
     * @return  <tt>true</tt> if this list contained the specified element.
     * @see java.util.List#remove(Object)
     * @uml.property  name="author"
     */
    public boolean removeAuthor(Author author) {
        return this.author.remove(author);
    }

    /**
     * Removes all of the elements from this list (optional operation).
     * @see java.util.List#clear()
     * @uml.property  name="author"
     */
    public void clearAuthor() {
        author.clear();
    }

    /**
     * Setter of the property <tt>author</tt>
     * @param author  the author to set.
     * @uml.property  name="author"
     */
    public void setAuthor(List<Author> author) {
        this.author = author;
    }
}
