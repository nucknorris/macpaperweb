/**
 * 
 */
package de.htwkleipzig.mmdb.mvc.controller;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.htwkleipzig.mmdb.model.Paper;
import de.htwkleipzig.mmdb.model.UploadItem;
import de.htwkleipzig.mmdb.service.PaperService;
import de.htwkleipzig.mmdb.util.FileUploader;
import de.htwkleipzig.mmdb.util.PDFParser;
import de.htwkleipzig.mmdb.util.TikaParser;

/**
 * @author spinner0815
 * 
 */
@Controller
@RequestMapping(value = "/upload")
public class UploadController {
	private String paperName;
	private String paperContent;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UploadController.class);

	@Autowired
	private PaperService paperService;

	@RequestMapping(method = RequestMethod.GET)
	public String getUploadForm(Model model) {
		model.addAttribute(new UploadItem());
		return "uploadForm";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(UploadItem uploadItem, BindingResult result) {
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				LOGGER.error("Error: " + error.getCode() + " - "
						+ error.getDefaultMessage());
			}
			return "uploadForm";
		}

		// Some type of file processing...
		LOGGER.debug("-------------------------------------------");
		LOGGER.debug("Test upload: " + uploadItem.getName());
		LOGGER.debug("Test upload: "
				+ uploadItem.getFileData().getOriginalFilename());

		LOGGER.debug("uploading the file...");
		paperName = FileUploader.uploadFileAndGenerateHash(uploadItem);
		LOGGER.debug("Name of the stored pdf file: {}", paperName);

		paperContent = parsePaperToHTML(uploadItem);
		LOGGER.debug("is the paperContent empty? {}", paperContent.isEmpty());

		LOGGER.debug("saving the String to elastic Search");
		Paper paper = new Paper();
		savePaper(paper);
		LOGGER.debug("-------------------------------------------");
		// return "redirect:/";
		// return "uploadDetailInputForm";
		return "redirect:/paper/" + paperName;
	}

	private void savePaper(Paper paper) {
		paper.setPaperId(paperName);
		paper.setFileName(paperName + ".pdf");
		paper.setContent(paperContent);
		paper.setCreateDate(new Date(System.currentTimeMillis()));
		paper.setUploadDate(new Date(System.currentTimeMillis()));
		TikaParser parser = new TikaParser();
		paper.setPaperAbstract(parser.startTokenizing(paperContent));
		paperService.save(paper);
	}

	private String parsePaperToHTML(UploadItem paper) {
		LOGGER.debug("start parsing the paper to html ...");
		try {
			return PDFParser.pdfParser(paper.getFileData().getInputStream());
		} catch (IOException e) {
			LOGGER.warn("error while parsing paper, returning the original paper");
			return "empty";
		}
	}
}
