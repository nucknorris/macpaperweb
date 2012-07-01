/**
 * 
 */
package de.htwkleipzig.mmdb;

import org.junit.Test;

import de.htwkleipzig.mmdb.util.Utilities;

/**
 * @author spinner0815
 * 
 */
public class PropertiesLoadTest {

    @Test
    public void propertiesTest() {
        String name = Utilities.getProperty("index.type");
        System.out.println(name);
    }
}
