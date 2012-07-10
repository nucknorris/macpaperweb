package de.htwkleipzig.mmdb.mvc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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
import de.htwkleipzig.mmdb.model.Categories;
import de.htwkleipzig.mmdb.model.Paper;
import de.htwkleipzig.mmdb.service.AuthorService;
import de.htwkleipzig.mmdb.service.PaperService;
import de.htwkleipzig.mmdb.util.Utilities;

/**
 * @author spinner0815, snuck, men0x
 * 
 */
@Controller
@RequestMapping(value = "/paper")
public class PaperController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaperController.class);

    @Autowired
    private PaperService paperService;
    @Autowired
    private AuthorService authorService;

    public PaperController() {
    }

    @RequestMapping(value = "/{recievedPaperName}")
    public String elasticstart(@PathVariable("recievedPaperName") String recievedPaperName, Model model) {
        LOGGER.info("startpage paper {}", recievedPaperName);
        model.addAttribute("recievedPaperName", recievedPaperName);
        Paper paper = paperService.get(recievedPaperName);
        model.addAttribute("categories", Categories.values());
        model.addAttribute("paper", paper);

        return "paper";
    }

    @RequestMapping(value = "/editListOfAuthors")
    public String editListOfAuthors(@RequestParam("id") String paperId, Model model) {
        Paper paper = paperService.get(paperId);
        model.addAttribute("authors", paper.getAuthorIds());
        return "editListOfAuthors";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String savePaper(@ModelAttribute("paper") Paper paper, BindingResult bindingResult) {
        LOGGER.debug("Received request to update paper");

        if (bindingResult.hasErrors()) {
            LOGGER.debug("error:" + bindingResult.getAllErrors());
        }

        LOGGER.debug("paper: {}", paper.getPaperId());
        LOGGER.debug("title: {}", paper.getTitle());
        if (!paper.getAuthorIds().isEmpty()) {
            LOGGER.debug("paper has authors");
            for (String authorId : paper.getAuthorIds()) {
                Author author = authorService.get(authorId);
                if (author == null) {
                    LOGGER.debug("author is empty");
                } else {
                    List<String> paperIds = new ArrayList<String>();
                    if (author.getPaperIds() == null) {
                        paperIds.addAll(author.getPaperIds());
                    }
                    paperIds.add(paper.getPaperId());
                    LOGGER.debug("update author with name {} {}", author.getName(), author.getLastname());
                    authorService.updateAuthor(author);
                }
            }
        }
        paperService.updatePaper(paper);
        LOGGER.debug("paper saved");
        return "redirect:/paper/" + paper.getPaperId() + "/";
    }

    @RequestMapping(value = "/delete")
    public String deletePaper(@RequestParam("paperId") String paperId, HttpServletResponse resp) {
        LOGGER.debug("Received request to update paper");
        paperService.delete(paperId);
        LOGGER.debug("paper with paperId {} deleted", paperId);
        return "redirect:/";
    }

    @RequestMapping(value = "/download")
    public String downloadFile(@RequestParam("paperId") String paperId, HttpServletResponse resp) throws IOException {
        String paperDirectory = Utilities.getProperty("paper.directory");
        LOGGER.debug("files loading from {}", paperDirectory);
        File file = new File(paperDirectory + "/" + paperId + ".pdf");

        resp.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        OutputStream out = resp.getOutputStream();
        resp.setContentType("text/plain; charset=utf-8");
        FileInputStream fi = new FileInputStream(file);
        IOUtils.copy(fi, out);
        out.flush();
        out.close();

        return null;
    }

    @RequestMapping(value = "/allpapers")
    @ResponseBody
    public String getAllAuthors() {
        LOGGER.info("all paper");
        return paperService.getAll().toString();
    }

    @RequestMapping(value = { "/{.*}/authorpopup", "/authorpopup" }, method = RequestMethod.GET)
    public String authorPopup(Model model) {
        model.addAttribute("authors", authorService.getAll());
        return "authorpopup";
    }
}
