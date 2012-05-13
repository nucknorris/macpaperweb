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

import de.htwkleipzig.mmdb.service.PaperService;
import de.htwkleipzig.mmdb.util.PDFParser;

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

        GetResponse rsp = paperService.get(id);
        Map<String, Object> source = rsp.getSource();
        for (String key : source.keySet()) {
            LOGGER.info("resource {}", key);
            LOGGER.info(source.get(key).toString());
        }
        return "elastic";
    }

    @RequestMapping(value = "/")
    public String elasticstart(Model model) {
        LOGGER.info("startpage home");
        model.addAttribute("attribute", "hier kann text drin stehen");
        return "elastic";
    }

	@RequestMapping(value = "/elasticsearch")
	public String elasticsearch(
			@RequestParam(required = true) String searchPhrase, Model model) {
		// search at QuizRDF
		LOGGER.info("search for a query");
		QueryStringQueryBuilder query = QueryBuilders.queryString(searchPhrase)
				.allowLeadingWildcard(false).useDisMax(true);
		SearchResponse response = paperService.search(query);
		LOGGER.info("total hits {}", response.getHits().getTotalHits());
		LOGGER.info("MaxScore {}", response.getHits().getMaxScore());
		model.addAttribute("totalHits", response.getHits().getTotalHits());
		model.addAttribute("maxScore", response.getHits().getTotalHits());

		for (SearchHit hit : response.getHits().getHits()) {
			if (hit.isSourceEmpty()) {
				LOGGER.info("source is empty");
			}
			LOGGER.info("id of the document {}", hit.getId());
			LOGGER.info("score of the hit {}", hit.getScore());
			model.addAttribute("documentId", hit.getId());
			model.addAttribute("documentScore", hit.getScore());

			Map<String, Object> source = hit.sourceAsMap();
			for (String key : source.keySet()) {
				LOGGER.info("key of the source {}", key);
				LOGGER.info(source.get(key).toString());
				// model.addAttribute("documentKey",
				// source.get(key).toString());
			}
		}
		LOGGER.info("MaxScore {}", response.getHits().getHits());
		model.addAttribute("searchTerm", searchPhrase);
		return "search";
	}

	@RequestMapping(value = "/elasticsave")
	public String elasticsave(@RequestParam(required = true) String path,
			@RequestParam(required = true) String id, Model model) {
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
		uniMadrid
				.setName("Escuela Politecnica Superior Universidad Autonoma de Madrid");
		uniMadrid.setStreet("C/ Francisco Tomas y Valiente");

		University uniMiltonKeynes = new University();
		uniMiltonKeynes.setCity("Milton Keynes");
		uniMiltonKeynes.setCountry("United Kingdom");
		uniMiltonKeynes.setPostcode("MK7 6AA");
		uniMiltonKeynes
				.setName("Knowledge Media institute The Open University");
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

		LOGGER.info(json);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("paper2", json);
		LOGGER.info("paper wurde gebaut und wird gespeichert");
		// final XContentBuilder jsonBuilder = XContentFactory.jsonBuilder();
		// jsonBuilder.startObject();
		// jsonBuilder.field("paper", paper);
		//
		// jsonBuilder.endObject();
		paperService.save("jsonmap", jsonMap);
		return "elastic";
	}

	@RequestMapping(value = "/getpaper", method = RequestMethod.GET)
	@ResponseBody
	public String getPaper(String id) {

		LOGGER.info("starting list test");
		GetResponse resp = paperService.get("jsonmap");
		Map<String, Object> source = resp.getSource();
		if (source != null && !source.isEmpty()) {
			LOGGER.info("source ist nicht null oder empty");
			if (source.containsKey("paper2")) {
				LOGGER.info("paper sollte existieren");
				String paper = (String) source.get("paper2");
				LOGGER.info("paper wird ausgespuckt");
				return paper;
			}
		}
		LOGGER.info("paper existiert wohl nicht");
		return null;
	}

}
