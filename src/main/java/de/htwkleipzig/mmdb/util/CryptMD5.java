package de.htwkleipzig.mmdb.util;

import java.io.InputStream;
import java.security.MessageDigest;

/**
 * 
 * @author men0x
 */
public class CryptMD5 {

	public static byte[] createChecksum(InputStream fis) throws Exception {

		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("MD5");
		int numRead;

		do {
			numRead = fis.read(buffer);
			if (numRead > 0) {
				complete.update(buffer, 0, numRead);
			}
		} while (numRead != -1);
		return complete.digest();
	}

	public static String getMD5Checksum(InputStream fis) throws Exception {
		byte[] b = createChecksum(fis);
		String result = "";

		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}
}