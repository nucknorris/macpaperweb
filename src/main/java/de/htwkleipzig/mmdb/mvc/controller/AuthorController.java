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

import de.htwkleipzig.mmdb.service.AuthorService;

/**
 * @author spinner0815
 * 
 */
@Controller
@RequestMapping("/author")
public class AuthorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorController.class);

    @Autowired
    private AuthorService authorService;

    public AuthorController() {
    }

    @RequestMapping(value = "")
    public String elasticstart(Model model) {
        LOGGER.info("startpage author");
        model.addAttribute("attribute", "Authoren");
        return "author";
    }
}
