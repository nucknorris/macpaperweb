/**
 * 
 */
package de.htwkleipzig.mmdb.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.htwkleipzig.mmdb.model.UploadItem;

/**
 * @author spinner0815
 * 
 */
@Controller
@RequestMapping(value = "/upload")
public class UploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

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
        LOGGER.debug("-------------------------------------------");
        return "redirect:/";
    }
}
