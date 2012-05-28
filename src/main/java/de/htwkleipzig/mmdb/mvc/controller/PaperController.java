package de.htwkleipzig.mmdb.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import de.htwkleipzig.mmdb.model.Paper;
import de.htwkleipzig.mmdb.service.PaperService;

/**
 * @author spinner0815
 * 
 */
@Controller
@RequestMapping(value = "/paper")
public class PaperController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaperController.class);

    @Autowired
    private PaperService paperService;

    public PaperController() {
    }

    @RequestMapping(value = "/{recievedPaperName}")
    public String elasticstart(@PathVariable("recievedPaperName") String recievedPaperName, Model model) {
        LOGGER.info("startpage paper {}", recievedPaperName);
        model.addAttribute("recievedPaperName", recievedPaperName);
        Paper paper = paperService.get(recievedPaperName);
        model.addAttribute("paperName", paper.getFileName());
        return "paper";
    }
}
