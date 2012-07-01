/*
 * Copyright 2002-2010 the original author or authors
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */
package de.htwkleipzig.mmdb.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    // @Autowired
    // private PaperService paperService;

    public HomeController() {
    }

    // @RequestMapping(value = "/elasticget")
    // public String elasticbean(@RequestParam(required = true) String id, Model model) {
    //
    // Paper rsp = paperService.get(id);
    // LOGGER.info("paper {}", rsp.toString());
    // return "home";
    // }

    @RequestMapping(value = "/")
    public String elasticstart(Model model) {
        LOGGER.info("startpage home");
        model.addAttribute("attribute", "hier kann text drin stehen");
        return "home";
    }

}
