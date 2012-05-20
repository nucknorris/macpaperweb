/**
 * 
 */
package de.htwkleipzig.mmdb.mvc.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

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
        Map<String, String> paper = new HashMap<String, String>();
        paper.put(paperName, paperContent);
        LOGGER.debug("converting Map<paperName, paperContent> into json String");
        Gson gson = new Gson();
        String jsonPaper = gson.toJson(paper);

        LOGGER.debug("save the String to elastic Search");
        paperService.save(paperName, jsonPaper);
        LOGGER.debug("-------------------------------------------");
        return "redirect:/";
    }
}
