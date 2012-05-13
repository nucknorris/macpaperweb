/**
 * 
 */
package de.htwkleipzig.mmdb.util;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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

				int readBytes = 0;
				byte[] buffer = new byte[10000];

				String md5Hash = de.htwkleipzig.mmdb.util.CryptMD5
						.getMD5Checksum(inputStream);

				LOGGER.debug(md5Hash);
				fileName = Utilities.getProperty("paper.directory") + md5Hash
						+ ".pdf";

				outputStream = new FileOutputStream(fileName);
				// schreiben
				while ((readBytes = inputStream.read(buffer, 0, 10000)) != -1) {
					outputStream.write(buffer, 0, readBytes);
				}
				outputStream.close();
				inputStream.close();
				LOGGER.debug("Datei hochgeladen!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.debug("Fehler beim Hochladen!");
		}
	}
}
