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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.htwkleipzig.mmdb.model.Paper;
import de.htwkleipzig.mmdb.service.PaperService;
import de.htwkleipzig.mmdb.util.PDFParser;
import de.htwkleipzig.mmdb.util.PaperHelper;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private PaperService paperService;

    public HomeController() {
    }

    @RequestMapping(value = "/elasticget")
    public String elasticbean(@RequestParam(required = true) String id, Model model) {

        Paper rsp = paperService.get(id);
        LOGGER.info("paper {}", rsp.toString());
        return "elastic";
    }

    @RequestMapping(value = "/")
    public String elasticstart(Model model) {
        LOGGER.info("startpage home");
        model.addAttribute("attribute", "hier kann text drin stehen");
        return "elastic";
    }

    @RequestMapping(value = "/elasticsearch")
    public String elasticsearch(@RequestParam(required = true) String searchPhrase, Model model) {
        // search at QuizRDF
        LOGGER.info("search for a query");
        QueryStringQueryBuilder query = QueryBuilders.queryString(searchPhrase).allowLeadingWildcard(false)
                .useDisMax(true);
        SearchResponse response = paperService.search(query);
        LOGGER.info("total hits {}", response.getHits().getTotalHits());
        LOGGER.info("MaxScore {}", response.getHits().getMaxScore());
        model.addAttribute("totalHits", response.getHits().getTotalHits());
        model.addAttribute("maxScore", response.getHits().getMaxScore());
        List<Paper> papers = new ArrayList<Paper>();
        for (SearchHit hit : response.getHits().getHits()) {
            if (hit.isSourceEmpty()) {
                LOGGER.info("source is empty");
            }
            LOGGER.info("id of the document {}", hit.getId());
            LOGGER.info("score of the hit {}", hit.getScore());
            model.addAttribute("documentId", hit.getId());
            model.addAttribute("documentScore", hit.getScore());

            Map<String, Object> resultMap = hit.sourceAsMap();

            Paper paper = PaperHelper.source2Paper(resultMap);

            paper.setContent("");
            LOGGER.debug("paper: {}", paper.getPaperId());
            papers.add(paper);

        }
        // LOGGER.info("MaxScore {}", response.getHits().getHits());
        model.addAttribute("paper", papers);
        model.addAttribute("searchTerm", searchPhrase);
        return "search";
    }

    @RequestMapping(value = "/elasticsave")
    public String elasticsave(@RequestParam(required = true) String path, @RequestParam(required = true) String id,
            Model model) {
        // File file = new File(path);
        Map<String, Object> test = new HashMap<String, Object>();
        LOGGER.debug("extract the content and metadata from the pdf");
        test.put("pdf", PDFParser.pdfParser(path));
        LOGGER.debug("the extracted content {}", test.get("pdf").toString());
        LOGGER.debug("try to save the context of the pdf to es");
        paperService.save(id, test);
        LOGGER.debug("saved");
        return "elastic";
    }

    @RequestMapping(value = "/getpaper", method = RequestMethod.GET)
    @ResponseBody
    public String getPaper(String id) {

        LOGGER.info("starting list test");
        Paper paper = paperService.get("jsonmap");
        if (paper != null) {
            LOGGER.debug("paper existiert");
            LOGGER.debug("paper {}", paper.toString());
        }
        LOGGER.info("paper existiert wohl nicht");
        return null;
    }

}
