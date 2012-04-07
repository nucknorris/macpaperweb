/**
 * 
 */
package de.htwkleipzig.mmdb.model;

import java.io.Serializable;

/**
 * @author spinner0815
 * 
 */
public class University implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8667452943986443594L;

    private String city;
    private String postcode;
    private String street;
    private String street2;
    private String housenumber;
    private String country;
    private String name;

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the postcode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * @param postcode
     *            the postcode to set
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street
     *            the street to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @return the street2
     */
    public String getStreet2() {
        return street2;
    }

    /**
     * @param street2
     *            the street2 to set
     */
    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    /**
     * @return the housenumber
     */
    public String getHousenumber() {
        return housenumber;
    }

    /**
     * @param housenumber
     *            the housenumber to set
     */
    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country
     *            the country to set
     */
    public void setCountry(String country) {
        this.country = country;
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
}
