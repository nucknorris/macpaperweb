package de.htwkleipzig.mmdb.mvc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.htwkleipzig.mmdb.model.Paper;
import de.htwkleipzig.mmdb.service.PaperService;
import de.htwkleipzig.mmdb.util.Utilities;

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
}
