package de.htwkleipzig.mmdb.mvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.htwkleipzig.mmdb.model.Author;
import de.htwkleipzig.mmdb.model.Paper;
import de.htwkleipzig.mmdb.service.AuthorService;
import de.htwkleipzig.mmdb.service.PaperService;
import de.htwkleipzig.mmdb.service.UniversityService;
import de.htwkleipzig.mmdb.util.AuthorHelper;
import de.htwkleipzig.mmdb.util.PaperHelper;

/**
 * @author men0x
 * 
 */
@Controller
public class SearchController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private PaperService paperService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private UniversityService universityService;

    @RequestMapping(value = "/search")
    public String elasticsearch(@RequestParam(required = true) String searchPhrase, Model model) {
        // search at QuizRDF
        LOGGER.info("search for a query");
        if (searchPhrase.isEmpty()) {
            LOGGER.debug("searchPhrase is empty");
            return "home";
        }
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
        return "resultPage";
    }

    @RequestMapping(value = "/extendedSearch")
    public String extendedSearch() {
        LOGGER.info("starting extended search");
        return "extendedSearch";
    }

    @RequestMapping(value = "/evaluateExtendedSearch")
    public String evaluateExtendedSearch(@RequestParam String author, @RequestParam String uni,
            @RequestParam String category, @RequestParam String tags, @RequestParam String keywords,
            @RequestParam String andor, Model model) {
        LOGGER.info("starting evaluating of extended Search");
        QueryBuilder query = null;
        SearchResponse response = null;
        if (!keywords.isEmpty()) {
            LOGGER.debug("searchPhrase is not empty");
            query = QueryBuilders.queryString(keywords).useDisMax(true);
        } else if (!author.isEmpty()) {
            LOGGER.debug("Author Search");
            // TODO - Author field is an id that point to the author in the author index
            // have to search the author in index author, get the id and have to search the authorId in field author of
            // index paper IMPORTANT the authorsId is an array!!
            QueryBuilder authorQuery = QueryBuilders.queryString(author).useDisMax(true)
                    .defaultOperator(QueryStringQueryBuilder.Operator.OR).analyzer("simple").field("lastName")
                    .field("name");
            LOGGER.debug("authorQuery: {}", authorQuery.toString());

            SearchResponse authorResponse = authorService.search(authorQuery);
            List<String> authors = new ArrayList<String>();
            for (SearchHit hit : authorResponse.getHits().getHits()) {
                if (hit.isSourceEmpty()) {
                    LOGGER.info("source is empty");
                }
                LOGGER.info("id of the author {}", hit.getId());
                LOGGER.info("score of the hit {}", hit.getScore());

                Map<String, Object> resultMap = hit.sourceAsMap();

                Author authorObject = AuthorHelper.source2author(resultMap);

                LOGGER.debug("paper: {}", authorObject.getAuthorId());
                authors.add(authorObject.getAuthorId());

            }
            query = QueryBuilders.inQuery("authorsId", authors.toArray());
            LOGGER.debug("query with authorIds for documentSearch {}", query.toString());

        }
        response = paperService.search(query);
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
        model.addAttribute("searchTerm", keywords);
        return "resultPage";
    }

}
