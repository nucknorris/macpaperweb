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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.get.GetResponse;
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

import com.google.gson.Gson;
import com.vaadin.ui.Window;

import de.htwkleipzig.mmdb.model.Author;
import de.htwkleipzig.mmdb.model.Paper;
import de.htwkleipzig.mmdb.model.University;
import de.htwkleipzig.mmdb.service.ElasticsearchService;
import de.htwkleipzig.mmdb.util.PDFParser;
import de.htwkleipzig.mmdb.vaadin.HelloButton;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

    private static final String INDEXTYPE = "indextype";

    private static final String PAPERINDEX = "paperindex";

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ElasticsearchService elasticService;

    public HomeController(ElasticsearchService elasticsearchService) {
        this.elasticService = elasticsearchService;
    }

    @RequestMapping(value = "/elasticget")
    public String elasticbean(@RequestParam(required = true) String id, Model model) {

        GetResponse rsp = elasticService.get(HomeController.PAPERINDEX, HomeController.INDEXTYPE, id);
        Map<String, Object> source = rsp.getSource();
        for (String key : source.keySet()) {
            logger.info("resource {}", key);
            logger.info(source.get(key).toString());
        }
        return "elastic";
    }

    // @RequestMapping(value = "/")
    // public String elasticstart(Model model) {
    // logger.info("try to find the document with the id swse");
    // GetResponse rsp = elasticService.get(HomeController.PAPERINDEX, HomeController.INDEXTYPE, "swse");
    // Map<String, Object> source = rsp.getSource();
    // for (String key : source.keySet()) {
    // logger.info("resource {}", key);
    // logger.info(source.get(key).toString());
    // }
    // return "elastic";
    // }

    @RequestMapping(value = "/elasticsearch")
    public String elasticsearch(@RequestParam(required = true) String searchPhrase, Model model) {
        // search at QuizRDF
        logger.info("search for a query");
        QueryStringQueryBuilder qb = QueryBuilders.queryString(searchPhrase).allowLeadingWildcard(false)
                .useDisMax(true);
        SearchResponse search = elasticService.searchsearch(HomeController.PAPERINDEX, qb);
        logger.info("total hits {}", search.getHits().getTotalHits());
        logger.info("MaxScore {}", search.getHits().getMaxScore());
        for (SearchHit hit : search.getHits().getHits()) {
            if (hit.isSourceEmpty()) {
                logger.info("source is empty");
            }
            logger.info("id of the document {}", hit.getId());
            logger.info("score of the hit {}", hit.getScore());

            Map<String, Object> source = hit.sourceAsMap();
            for (String key : source.keySet()) {
                logger.info("key of the source {}", key);
                logger.info(source.get(key).toString());
            }
        }
        logger.info("MaxScore {}", search.getHits().getHits());
        return "elastic";
    }

    @RequestMapping(value = "/elasticsave")
    public String elasticsave(@RequestParam(required = true) String path, @RequestParam(required = true) String id,
            Model model) {
        // File file = new File(path);
        Map<String, Object> test = new HashMap<String, Object>();
        logger.debug("extract the content and metadata from the pdf");
        test.put("pdf", PDFParser.pdfParser(path));
        logger.debug("the extracted content {}", test.get("pdf").toString());
        logger.debug("try to save the context of the pdf to es");
        elasticService.save(HomeController.PAPERINDEX, HomeController.INDEXTYPE, id, test);
        logger.debug("saved");
        return "elastic";
    }

    @RequestMapping(value = "/savejson")
    public String elasticSaveJson(Model model) {

        List<String> keywords = new ArrayList<String>();
        keywords.add("semantic search");
        keywords.add("Information Retrieval");
        keywords.add("evaluation benchmarks");

        Paper paper = new Paper();
        paper.setCreateDate(new Date(System.currentTimeMillis()));
        paper.setKindOf("paper");
        paper.setTitle("Using TREC for cross-comparison between classic IR and ontology-based search models at a Web scale");
        paper.setKeywords(keywords);

        University uniMadrid = new University();
        uniMadrid.setCity("Madrid");
        uniMadrid.setCountry("Spain");
        uniMadrid.setPostcode("28048");
        uniMadrid.setHousenumber("11");
        uniMadrid.setName("Escuela Politecnica Superior Universidad Autonoma de Madrid");
        uniMadrid.setStreet("C/ Francisco Tomas y Valiente");

        University uniMiltonKeynes = new University();
        uniMiltonKeynes.setCity("Milton Keynes");
        uniMiltonKeynes.setCountry("United Kingdom");
        uniMiltonKeynes.setPostcode("MK7 6AA");
        uniMiltonKeynes.setName("Knowledge Media institute The Open University");
        uniMiltonKeynes.setStreet("Walton Hall");

        Author authorFern = new Author();
        authorFern.setEmail("Miriam.fernandez@uam.es");
        authorFern.setLastname("Fernandez");
        authorFern.setName("Miriam");
        authorFern.setUniversity(uniMadrid);
        paper.getAuthors().add(authorFern);

        Author authorVallet = new Author();
        authorVallet.setEmail("David.vallet@uam.es");
        authorVallet.setLastname("Vallet");
        authorVallet.setName("David");
        authorVallet.setUniversity(uniMadrid);
        paper.getAuthors().add(authorVallet);

        Author authorCastells = new Author();
        authorCastells.setEmail("pablo.castells@uam.es");
        authorCastells.setLastname("Castells");
        authorCastells.setName("Paplo");
        authorCastells.setUniversity(uniMadrid);
        paper.getAuthors().add(authorCastells);

        paper.setContent("hier kommt der ganze inhalt mit rein");
        Gson gson = new Gson();

        // convert java object to JSON format,
        // and returned as JSON formatted string
        String json = gson.toJson(paper);

        logger.info(json);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("paper2", json);
        logger.info("paper wurde gebaut und wird gespeichert");
        // final XContentBuilder jsonBuilder = XContentFactory.jsonBuilder();
        // jsonBuilder.startObject();
        // jsonBuilder.field("paper", paper);
        //
        // jsonBuilder.endObject();
        elasticService.save(PAPERINDEX, INDEXTYPE, "jsonmap", jsonMap);
        return "elastic";
    }

    @RequestMapping(value = "/getpaper", method = RequestMethod.GET)
    @ResponseBody
    public String getPaper(String id) {

        logger.info("starting list test");
        GetResponse resp = elasticService.get(PAPERINDEX, INDEXTYPE, "jsonmap");
        Map<String, Object> source = resp.getSource();
        if (source != null && !source.isEmpty()) {
            logger.info("source ist nicht null oder empty");
            if (source.containsKey("paper2")) {
                logger.info("paper sollte existieren");
                String paper = (String) source.get("paper2");
                logger.info("paper wird ausgespuckt");
                return paper;
            }
        }
        logger.info("paper existiert wohl nicht");
        return null;
    }

}
