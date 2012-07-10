package de.htwkleipzig.mmdb.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * The Class OwnHash.
 *
 * @author men0x
 */
public class OwnHash {

    /**
     * Own hash.
     *
     * @param name the name
     * @param lastname the lastname
     * @return the string
     */
    public static String ownHash(String name, String lastname) {
        String hash = "";

        // clean name and lastname
        name = name.replaceAll("[^a-zA-Z]", "");
        lastname = lastname.replaceAll("[^a-zA-Z]", "");

        Random generator = new Random((name + lastname).length());

        for (int i = name.length(); i <= 3; i++) {

            try {
                name = name + CryptSHA1.SHA1("wurstbrot").charAt(generator.nextInt(32));
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        for (int ii = lastname.length(); ii <= 3; ii++) {

            try {
                lastname = lastname + CryptSHA1.SHA1("wurstbrot").charAt(generator.nextInt(32));
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // part1
        hash = "" + name.charAt(0) + name.charAt(1) + name.charAt(2) + name.charAt(3);
        // part2
        hash = hash + lastname.charAt(0) + lastname.charAt(1) + lastname.charAt(2) + lastname.charAt(3);

        Random generator2 = new Random((name + lastname).length());

        try {
            for (int iii = 0; iii < 5; iii++) {
                hash = hash + CryptSHA1.SHA1(name + lastname).charAt(generator2.nextInt((name + lastname).length()));
            }
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return hash;
    }
}
