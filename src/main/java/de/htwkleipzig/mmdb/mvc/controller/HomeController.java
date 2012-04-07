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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.htwkleipzig.mmdb.model.TwitterMessage;
import de.htwkleipzig.mmdb.service.ElasticsearchService;
import de.htwkleipzig.mmdb.service.TwitterService;
import de.htwkleipzig.mmdb.util.PDFParser;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    // @Autowired
    private TwitterService twitterService;

    @Autowired
    private ElasticsearchService elasticService;

    // /**
    // * Simply selects the home view to render by returning its name.
    // */
    // @RequestMapping(value = "/")
    // public String home(Model model, @RequestParam(required = false) String startTwitter,
    // @RequestParam(required = false) String stopTwitter) {
    //
    // if (startTwitter != null) {
    // twitterService.startTwitterAdapter();
    // return "redirect:/";
    // }
    //
    // if (stopTwitter != null) {
    // twitterService.stopTwitterAdapter();
    // return "redirect:/";
    // }
    //
    // final Collection<TwitterMessage> twitterMessages = twitterService.getTwitterMessages();
    //
    // logger.info("Retrieved {} Twitter messages.", twitterMessages.size());
    //
    // model.addAttribute("twitterMessages", twitterMessages);
    //
    // return "home";
    // }
    //
    // /**
    // * Simply selects the home view to render by returning its name.
    // */
    // @RequestMapping(value = "/ajax")
    // public String ajaxCall(Model model) {
    //
    // final Collection<TwitterMessage> twitterMessages = twitterService.getTwitterMessages();
    //
    // logger.info("Retrieved {} Twitter messages.", twitterMessages.size());
    // model.addAttribute("twitterMessages", twitterMessages);
    //
    // return "twitterMessages";
    //
    // }

    // @RequestMapping(value = "/tika")
    // public String tikaTest(Model model) {
    // Tika tika = new Tika();
    // Metadata met = new Metadata();
    // URL pdf = (HomeController.class.getClassLoader().getResource("10.1.1.122.5934.pdf"));
    // LinkContentHandler linkHandler = new LinkContentHandler();
    // ContentHandler textHandler = new BodyContentHandler();
    // ToHTMLContentHandler toHTMLHandler = new ToHTMLContentHandler();
    // TeeContentHandler teeHandler = new TeeContentHandler(linkHandler, textHandler, toHTMLHandler);
    // PDFParser pdfParser = new PDFParser();
    // ParseContext parseContext = new ParseContext();
    //
    // try {
    // Parser parser = tika.getParser();
    // // parser.parse(pdf.openStream(), toHTMLHandler, met, null);
    // pdfParser.parse(pdf.openStream(), teeHandler, met, parseContext);
    // logger.info(System.getProperty("line.separator") + "Metadata:\t" + met);
    // model.addAttribute("applicationName", met.toString());
    // model.addAttribute("toHtml", toHTMLHandler.toString());
    // model.addAttribute("link", linkHandler.toString());
    // // model.addAttribute("text", textHandler.toString());
    // model.addAttribute("text", parseContext.toString());
    // } catch (Exception e) {
    // logger.error(e.getLocalizedMessage() + " " + e.getCause());
    // }
    // return "tika";
    // }

    // @RequestMapping(value = "/elastic")
    public String elasticSearch(Model model) {
        Map<String, Object> test = new HashMap<String, Object>();
        // test.put("pdf", PDFParser.pdfParser());
        // Node node = NodeBuilder.nodeBuilder().node();
        // node.start();
        //
        // Client client = node.client();
        //

        logger.info("create settings");
        Settings settings = ImmutableSettings.settingsBuilder().build();
        logger.info("Add transport adresse");
        TransportClient client = new TransportClient(settings);

        logger.info("add settings to transportclient");
        client.addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300));
        try {
            // create an index called myindex
            logger.info("create an index called myindex");
            client.admin().indices().create(new CreateIndexRequest("myindex")).actionGet();
        } catch (IndexAlreadyExistsException ex) {
            logger.warn("already exists", ex);
        }
        logger.info("indexRequestBuilder");
        // index the object test on index myindex with indextype indextype and id 3
        IndexRequestBuilder irb = client.prepareIndex("myindex", "indextype", "3").
                setSource(test);
        irb.execute().actionGet();
        // gets the object from myindex with indextype indextype and id 3
        GetResponse rsp = client.prepareGet("myindex", "indextype", "3").
                execute().actionGet();
        Map<String, Object> source = rsp.getSource();
        logger.info(source.size() + "");
        // logger.info((String) source.get("pdf"));

        // search at myindex
        SearchRequestBuilder builder = client.prepareSearch("myindex");
        // search at QuizRDF
        QueryStringQueryBuilder qb = QueryBuilders.queryString("QuizRDF").allowLeadingWildcard(false)
                .useDisMax(true);
        builder.setQuery(qb);

        SearchResponse searchresponse = builder.execute().actionGet();
        SearchHit[] docs = searchresponse.getHits().getHits();
        for (SearchHit sd : docs) {
            // to get explanation you'll need to enable this when querying:
            // System.out.println(sd.getExplanation().toString());

            // if we use in mapping: "_source" : {"enabled" : false}
            // we need to include all necessary fields in query and then to use doc.getFields()
            // instead of doc.getSource()
            Map<String, Object> searchSource = sd.getSource();
            for (String key : searchSource.keySet()) {
                logger.info((String) searchSource.get(key));
            }
        }
        client.close();

        return "elastic";
    }

    @RequestMapping(value = "/elasticget")
    public String elasticbean(@RequestParam(required = true) String id, Model model) {

        GetResponse rsp = elasticService.get("myindex", "indextype", id);
        Map<String, Object> source = rsp.getSource();
        for (String key : source.keySet()) {
            logger.info("resource {}", key);
            logger.info(source.get(key).toString());
        }
        return "elastic";
    }

    @RequestMapping(value = "/")
    public String elasticstart(Model model) {
        logger.info("try to find the document with the id swse");
        GetResponse rsp = elasticService.get("myindex", "indextype", "swse");
        Map<String, Object> source = rsp.getSource();
        for (String key : source.keySet()) {
            logger.info("resource {}", key);
            logger.info(source.get(key).toString());
        }
        return "elastic";
    }

    @RequestMapping(value = "/elasticsearch")
    public String elasticsearch(@RequestParam(required = true) String searchPhrase, Model model) {
        // search at QuizRDF
        logger.info("search for a query");
        QueryStringQueryBuilder qb = QueryBuilders.queryString(searchPhrase).allowLeadingWildcard(false)
                .useDisMax(true);
        SearchResponse search = elasticService.searchsearch("myindex", qb);
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
        elasticService.save("myindex", "indextype", id, test);
        logger.debug("saved");
        return "elastic";
    }
}
