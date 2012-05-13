/**
 * 
 */
package de.htwkleipzig.mmdb.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import de.htwkleipzig.mmdb.model.UploadItem;

/**
 * @author men0x
 * 
 */
public class FileUploader {

	public FileUploader(UploadItem uploadItem) {
		fileUploader(uploadItem);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FileUploader.class);

	private void fileUploader(UploadItem uploadItem) {
		try {
			MultipartFile file = uploadItem.getFileData();
			String fileName = null;
			InputStream inputStream = null;
			OutputStream outputStream = null;

			if (file.getSize() > 0) {
				inputStream = file.getInputStream();
				LOGGER.debug("size:" + file.getSize());

				LOGGER.debug("file:" + file.getOriginalFilename());

				inputStream.mark(0);

				String md5Hash = de.htwkleipzig.mmdb.util.CryptMD5
						.getMD5Checksum(inputStream);

				LOGGER.debug(md5Hash);

				fileName = Utilities.getProperty("paper.directory") + md5Hash
						+ ".pdf";
				File f = new File(fileName);
				outputStream = new FileOutputStream(f);
				// schreiben

				// int read = 0;
				// byte[] bytes = new byte[1024];
				//
				// while ((read = inputStream.read(bytes)) != -1) {
				// outputStream.write(bytes, 0, read);
				// LOGGER.debug("write");
				// }
				inputStream = file.getInputStream();
				byte buf[] = new byte[1024];
				int len;
				while ((len = inputStream.read(buf)) > 0) {
					outputStream.write(buf, 0, len);
					LOGGER.debug("write");
				}

				// outputStream.flush();
				outputStream.close();
				inputStream.close();
				LOGGER.debug("Datei hochgeladen!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.debug("Fehler beim Hochladen!");
		}
	}

	public String convertStreamToString(InputStream is) throws IOException {
		if (is != null) {
			Writer writer = new StringWriter();
			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,
						"UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}
}
