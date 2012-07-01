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
public class DeweyDecimalClassification implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3450395285315694650L;
    /**
     * @uml.property  name="paper"
     * @uml.associationEnd  multiplicity="(0 -1)" ordering="true" inverse="ddc:de.htwkleipzig.mmdb.model.Paper"
     */
    private List<Paper> paper;

    /**
     * Getter of the property <tt>paper</tt>
     * @return  Returns the paper.
     * @uml.property  name="paper"
     */
    public List<Paper> getPaper() {
        return paper;
    }

    /**
     * Returns the element at the specified position in this list.
     * @param index  index of element to return.
     * @return  the element at the specified position in this list.
     * @see java.util.List#get(int)
     * @uml.property  name="paper"
     */
    public Paper getPaper(int i) {
        return (Paper) paper.get(i);
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     * @return  an iterator over the elements in this list in proper sequence.
     * @see java.util.List#iterator()
     * @uml.property  name="paper"
     */
    public Iterator<Paper> paperIterator() {
        return paper.iterator();
    }

    /**
     * Returns <tt>true</tt> if this list contains no elements.
     * @return  <tt>true</tt> if this list contains no elements.
     * @see java.util.List#isEmpty()
     * @uml.property  name="paper"
     */
    public boolean isPaperEmpty() {
        return paper.isEmpty();
    }

    /**
     * Returns <tt>true</tt> if this list contains the specified element.
     * @param element  element whose presence in this list is to be tested.
     * @return  <tt>true</tt> if this list contains the specified element.
     * @see java.util.List#contains(Object)
     * @uml.property  name="paper"
     */
    public boolean containsPaper(Paper paper) {
        return this.paper.contains(paper);
    }

    /**
     * Returns <tt>true</tt> if this list contains all of the elements of the specified collection.
     * @param elements  collection to be checked for containment in this list.
     * @return  <tt>true</tt> if this list contains all of the elements of the specified collection.
     * @see java.util.List#containsAll(Collection)
     * @uml.property  name="paper"
     */
    public boolean containsAllPaper(Collection<? extends Paper> paper) {
        return this.paper.containsAll(paper);
    }

    /**
     * Returns the number of elements in this list.
     * @return  the number of elements in this list.
     * @see java.util.List#size()
     * @uml.property  name="paper"
     */
    public int paperSize() {
        return paper.size();
    }

    /**
     * Returns an array containing all of the elements in this list in proper sequence.
     * @return  an array containing all of the elements in this list in proper sequence.
     * @see java.util.List#toArray()
     * @uml.property  name="paper"
     */
    public Paper[] paperToArray() {
        return paper.toArray(new Paper[paper.size()]);
    }

    /**
     * Returns an array containing all of the elements in this list in proper sequence; the runtime type of the returned array is that of the specified array.
     * @param a  the array into which the elements of this list are to be stored.
     * @return  an array containing all of the elements in this list in proper sequence.
     * @see java.util.List#toArray(Object[])
     * @uml.property  name="paper"
     */
    public <T extends Paper> T[] paperToArray(T[] paper) {
        return (T[]) this.paper.toArray(paper);
    }

    /**
     * Inserts the specified element at the specified position in this list (optional operation)
     * @param index  index at which the specified element is to be inserted.
     * @param element  element to be inserted.
     * @see java.util.List#add(int,Object)
     * @uml.property  name="paper"
     */
    public void addPaper(int index, Paper paper) {
        this.paper.add(index, paper);
    }

    /**
     * Appends the specified element to the end of this list (optional operation).
     * @param element  element to be appended to this list.
     * @return  <tt>true</tt> (as per the general contract of the <tt>Collection.add</tt> method).
     * @see java.util.List#add(Object)
     * @uml.property  name="paper"
     */
    public boolean addPaper(Paper paper) {
        return this.paper.add(paper);
    }

    /**
     * Removes the element at the specified position in this list (optional operation).
     * @param index  the index of the element to removed.
     * @return  the element previously at the specified position.
     * @see java.util.List#remove(int)
     * @uml.property  name="paper"
     */
    public Object removePaper(int index) {
        return paper.remove(index);
    }

    /**
     * Removes the first occurrence in this list of the specified element  (optional operation).
     * @param element  element to be removed from this list, if present.
     * @return  <tt>true</tt> if this list contained the specified element.
     * @see java.util.List#remove(Object)
     * @uml.property  name="paper"
     */
    public boolean removePaper(Paper paper) {
        return this.paper.remove(paper);
    }

    /**
     * Removes all of the elements from this list (optional operation).
     * @see java.util.List#clear()
     * @uml.property  name="paper"
     */
    public void clearPaper() {
        paper.clear();
    }

    /**
     * Setter of the property <tt>paper</tt>
     * @param paper  the paper to set.
     * @uml.property  name="paper"
     */
    public void setPaper(List<Paper> paper) {
        this.paper = paper;
    }

}
