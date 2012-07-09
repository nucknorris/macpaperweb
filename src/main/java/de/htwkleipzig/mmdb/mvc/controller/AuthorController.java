/**
 * 
 */
package de.htwkleipzig.mmdb.mvc.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.htwkleipzig.mmdb.model.Author;
import de.htwkleipzig.mmdb.service.AuthorService;

/**
 * @author spinner0815, men0x
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

    @RequestMapping(value = "/{recievedAuthorID}")
    public String elasticstart(@PathVariable("recievedAuthorID") String recievedAuthorID, Model model) {
        LOGGER.info("startpage author {}", recievedAuthorID);
        model.addAttribute("recievedUniversityID", recievedAuthorID);
        Author author = authorService.get(recievedAuthorID);
        model.addAttribute("author", author);
        return "author";
    }

    /**
     * @param name
     * @param lastName
     * @param Model
     * @return
     */
    @RequestMapping(value = "/createAuthor")
    public String authorCreate(String title, String name, String lastName, String email, String universityId,
            String paperids, Model model) {

        Author author = new Author();

        List<String> paperIds = new ArrayList<String>();
        paperids = paperids.replaceAll(" ", "");
        String[] splittetPapers = paperids.split(",");
        paperIds.addAll(Arrays.asList(splittetPapers));

        author.setPaperIds(paperIds);
        author.setAuthorId(name + lastName);
        author.setTitle(title);
        author.setEmail(email);
        author.setLastname(lastName);
        author.setName(name);
        author.setUniversityId(universityId);
        authorService.save(author);
        model.addAttribute("author", author);
        return "author";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String saveUniversity(@ModelAttribute("author") Author author, BindingResult bindingResult) {
        LOGGER.debug("Received request to update a author");

        if (bindingResult.hasErrors()) {
            LOGGER.debug("error:" + bindingResult.getAllErrors());
        }

        LOGGER.debug("author: {}", author.getAuthorId());
        LOGGER.debug("name: {}", author.getName());
        authorService.updateAuthor(author);
        LOGGER.debug("author saved");
        return "redirect:/author/" + author.getAuthorId() + "/";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testJSP() {

        return "test";
    }

    @RequestMapping(value = "/authorpopup", method = RequestMethod.GET)
    public String authorPopup(Model model) {
        model.addAttribute("authors", authorService.getAll());
        return "authorpopup";
    }

    @RequestMapping(value = "/allauthors")
    @ResponseBody
    public String getAllAuthors() {
        LOGGER.info("all authors shit");
        return authorService.getAll().toString();
    }
}
