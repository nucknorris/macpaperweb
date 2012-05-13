/**
 * 
 */
package de.htwkleipzig.mmdb.model;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author spinner0815
 * 
 */
public class UploadItem {
	private String name;
	private String title;
	private List<String> listOfAuthors;
	private CommonsMultipartFile fileData;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

	public List<String> getListOfAuthors() {
		return listOfAuthors;
	}

	public void setListOfAuthors(List<String> listOfAuthors) {
		this.listOfAuthors = listOfAuthors;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
