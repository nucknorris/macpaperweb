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

/**
 * @author spinner0815
 * 
 */
@Controller
@RequestMapping(value = "/upload")
public class UploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

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
                LOGGER.error("Error: " + error.getCode() + " - " + error.getDefaultMessage());
            }
            return "uploadForm";
        }

        // Some type of file processing...
        LOGGER.debug("-------------------------------------------");
        LOGGER.debug("Test upload: " + uploadItem.getName());
        LOGGER.debug("Test upload: " + uploadItem.getFileData().getOriginalFilename());
        FileUploader fileUploader = new FileUploader();
        LOGGER.debug("upload the file");
        String paperName = fileUploader.fileUploader(uploadItem);
        LOGGER.debug("Name of the stored pdf file: {}", paperName);

        LOGGER.debug("get the paper content");
        String paperContent = null;
        try {
            paperContent = PDFParser.pdfParser(uploadItem.getFileData().getInputStream());
        } catch (IOException e) {
            LOGGER.error("ERROR while extracting the content", e.fillInStackTrace());
        }

        LOGGER.debug("is the paperContent empty? {}", paperContent.isEmpty());

        LOGGER.debug("save the String to elastic Search");
        // create the paper object and store it
        Paper paper1 = new Paper();
        paper1.setPaperId(paperName);
        paper1.setFileName(paperName + ".pdf");
        paper1.setContent(paperContent);
        paper1.setCreateDate(new Date(System.currentTimeMillis()));
        paper1.setUploadDate(new Date(System.currentTimeMillis()));
        paperService.save(paper1);
        LOGGER.debug("-------------------------------------------");
        return "redirect:/";
    }
}
