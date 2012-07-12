/**
 * 
 */
package de.htwkleipzig.mmdb.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.htwkleipzig.mmdb.model.Author;
import de.htwkleipzig.mmdb.model.Paper;
import de.htwkleipzig.mmdb.model.University;
import de.htwkleipzig.mmdb.service.AuthorService;
import de.htwkleipzig.mmdb.service.PaperService;
import de.htwkleipzig.mmdb.service.UniversityService;
import de.htwkleipzig.mmdb.util.OwnHash;

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

    @Autowired
    private UniversityService universityService;

    @Autowired
    private PaperService paperService;

    public AuthorController() {
    }

    @RequestMapping(value = "/{recievedAuthorID}")
    public String elasticstart(@PathVariable("recievedAuthorID") String recievedAuthorID, Model model) {
        LOGGER.info("startpage author {}", recievedAuthorID);
        model.addAttribute("recievedAuthorID", recievedAuthorID);
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
    public String authorCreate(Model model) {
        LOGGER.debug("create a new author");

        model.addAttribute("author", new Author());
        return "author";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String saveUniversity(@ModelAttribute("author") Author author, BindingResult bindingResult) {
        LOGGER.debug("Received request to update a author");

        if (bindingResult.hasErrors()) {
            LOGGER.debug("error:" + bindingResult.getAllErrors());
        }
        if (author.getAuthorId().isEmpty()) {
            LOGGER.debug("auhtor id is empty");
            author.setAuthorId(OwnHash.ownHash(author.getName(), author.getLastname()));
        }
        if (author.getUniversityId().isEmpty()) {
            LOGGER.debug("university id is empty");
            author.setUniversityId("empty");
        } // add authorId to university
        else {
            List<String> authorIds;
            University university = universityService.get(author.getUniversityId());
            authorIds = university.getAuthorIds();
            authorIds.add(author.getAuthorId());
            university.setAuthorIds(authorIds);
            universityService.updateUniversity(university);
            authorIds = null;
        }
        if (author.getPaperIds().isEmpty()) {
            LOGGER.debug("paperId is empty");
            List<String> paperIds = new ArrayList<String>();
            paperIds.add("empty");
            author.setPaperIds(paperIds);
        } else {
            List<String> authorIds;
            for (String paperId : author.getPaperIds()) {
                Paper paper = paperService.get(paperId);
                authorIds = paper.getAuthorIds();
                authorIds.add(author.getAuthorId());
                paper.setAuthorIds(authorIds);
                paperService.updatePaper(paper);
                authorIds = null;
            }
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

    @RequestMapping(value = "/delete")
    public String deletePaper(@RequestParam("authorId") String authorId, HttpServletResponse resp) {
        LOGGER.debug("Received request to delete author");
        authorService.delete(authorId);
        LOGGER.debug("author with authorId {} deleted", authorId);
        return "redirect:/management/";
    }

    @RequestMapping(value = { "/{.*}/universitypopup", "/universitypopup" }, method = RequestMethod.GET)
    public String universityPopup(Model model) {
        model.addAttribute("universities", universityService.getAll());
        return "universitypopup";
    }
}
