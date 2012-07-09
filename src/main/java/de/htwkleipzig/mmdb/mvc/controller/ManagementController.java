package de.htwkleipzig.mmdb.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import de.htwkleipzig.mmdb.service.AuthorService;
import de.htwkleipzig.mmdb.service.PaperService;
import de.htwkleipzig.mmdb.service.UniversityService;

/**
 * @author snuck, men0x
 * 
 */

@Controller
@RequestMapping(value = "/management")
public class ManagementController {

    @Autowired
    private AuthorService authorService;
    @Autowired
    private UniversityService universityService;
    @Autowired
    private PaperService paperService;

    public ManagementController() {
    }

    @RequestMapping(value = "")
    public String manage(Model model) {

        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("papers", paperService.getAll());
        model.addAttribute("universities", universityService.getAll());

        return "management";
    }

}
