/**
 * 
 */
package de.htwkleipzig.mmdb.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import de.htwkleipzig.mmdb.service.UniversityService;

/**
 * @author spinner0815
 * 
 */
@Controller
@RequestMapping("/university")
public class UniversityController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UniversityController.class);

    @Autowired
    private UniversityService universityService;

    public UniversityController() {
    }

    @RequestMapping(value = "")
    public String elasticstart(Model model) {
        LOGGER.info("startpage university");
        model.addAttribute("attribute", "university");
        return "university";
    }

}
