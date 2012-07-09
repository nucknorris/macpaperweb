package de.htwkleipzig.mmdb.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/management")
public class ManagementController {

    public ManagementController() {
    }

    @RequestMapping(value = "")
    public String manage(){
        return "management";
    }
    
}
