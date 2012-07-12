package de.htwkleipzig.mmdb.mvc.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BaseQueryBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FieldQueryBuilder;
import org.elasticsearch.index.query.FuzzyLikeThisQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder.Operator;
import org.elasticsearch.index.query.TextQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.htwkleipzig.mmdb.model.Author;
import de.htwkleipzig.mmdb.model.Categories;
import de.htwkleipzig.mmdb.model.Paper;
import de.htwkleipzig.mmdb.model.University;
import de.htwkleipzig.mmdb.service.AuthorService;
import de.htwkleipzig.mmdb.service.PaperService;
import de.htwkleipzig.mmdb.service.UniversityService;
import de.htwkleipzig.mmdb.util.AuthorHelper;
import de.htwkleipzig.mmdb.util.PaperHelper;
import de.htwkleipzig.mmdb.util.UniversityHelper;

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
        List<Paper> papers = new ArrayList<Paper>();
        LOGGER.info("search for a query");
        if (searchPhrase.isEmpty()) {
            LOGGER.debug("searchPhrase is empty");
            return "home";
        }
        QueryStringQueryBuilder query = QueryBuilders.queryString(searchPhrase).allowLeadingWildcard(false)
                .useDisMax(true);
        papers.addAll(searchResult(query));
        if (papers.isEmpty()) {
            papers.addAll(fuzzyLikeSearch(searchPhrase));
        }
        // LOGGER.info("MaxScore {}", response.getHits().getHits());
        model.addAttribute("paper", papers);
        model.addAttribute("searchTerm", searchPhrase);
        return "resultPage";
    }

    private List<Paper> searchResult(BaseQueryBuilder query) {
        SearchResponse response = paperService.search(query);
        LOGGER.info("total hits {}", response.getHits().getTotalHits());
        LOGGER.info("MaxScore {}", response.getHits().getMaxScore());
        // model.addAttribute("totalHits", response.getHits().getTotalHits());
        // model.addAttribute("maxScore", response.getHits().getMaxScore());
        List<Paper> papers = new ArrayList<Paper>();
        for (SearchHit hit : response.getHits().getHits()) {
            if (hit.isSourceEmpty()) {
                LOGGER.info("source is empty");
            }
            LOGGER.info("id of the document {}", hit.getId());
            LOGGER.info("score of the hit {}", hit.getScore());
            // model.addAttribute("documentId", hit.getId());
            // model.addAttribute("documentScore", hit.getScore());

            Map<String, Object> resultMap = hit.sourceAsMap();

            Paper paper = PaperHelper.source2Paper(resultMap);

            // paper.setContent("");
            LOGGER.debug("paper: {}", paper.getPaperId());
            papers.add(paper);

        }
        return papers;
    }

    private List<Paper> fuzzyLikeSearch(String searchPhrase) {
        LOGGER.debug("start fuzzy search");
        FuzzyLikeThisQueryBuilder likeText = QueryBuilders.fuzzyLikeThisQuery().likeText(searchPhrase);
        LOGGER.debug("likeText {}", likeText.toString());
        return searchResult(likeText);

    }

    @RequestMapping(value = "/extendedSearch")
    public String extendedSearch(Model model) {
        LOGGER.info("starting extended search");
        model.addAttribute("categories", Categories.values());
        return "extendedSearch";
    }

    @RequestMapping(value = "/searchUniversity")
    public String searchUniversity(@RequestParam String university, Model model) {
        QueryBuilder universityQuery = universityQuery(university);
        SearchResponse search = universityService.search(universityQuery);
        List<University> universities = new ArrayList<University>();
        for (SearchHit hit : search.getHits().getHits()) {
            if (hit.isSourceEmpty()) {
                LOGGER.info("source is empty");
            }
            LOGGER.info("id of the university {}", hit.getId());
            LOGGER.info("score of the hit {}", hit.getScore());

            Map<String, Object> resultMap = hit.sourceAsMap();

            University universityObject = UniversityHelper.source2University(resultMap);
            LOGGER.debug("university: {}", universityObject.getName());
            universities.add(universityObject);

        }

        model.addAttribute("universities", universities);
        return "resultUniversities";
    }

    @RequestMapping(value = "/searchAuthor")
    public String searchAuthor(@RequestParam String author, Model model) {
        QueryBuilder authorQuery = authorQuery(author);
        SearchResponse search = authorService.search(authorQuery);
        List<Author> authors = new ArrayList<Author>();
        for (SearchHit hit : search.getHits().getHits()) {
            if (hit.isSourceEmpty()) {
                LOGGER.info("source is empty");
            }
            LOGGER.info("id of the author {}", hit.getId());
            LOGGER.info("score of the hit {}", hit.getScore());

            Map<String, Object> resultMap = hit.sourceAsMap();

            Author authorObject = AuthorHelper.source2author(resultMap);
            LOGGER.debug("author: {}", authorObject.getLastname());
            authors.add(authorObject);

        }

        model.addAttribute("authors", authors);
        return "resultAuthors";
    }

    /**
     * all fields can contain more than one value. They have seperated by spaces
     * 
     * @param author
     *            the authors query.should contain
     * @param uni
     *            query.should contain
     * @param category
     *            query.must contain
     * @param tags
     *            query.must contain
     * @param and
     *            query.must contain
     * @param or
     *            query.should contain
     * @param secialand
     *            query.must contain
     * @param model
     *            the container for the result page
     * @return the String the resultpage is named
     */
    @RequestMapping(value = "/evaluateExtendedSearch")
    public String evaluateExtendedSearch(@RequestParam String author, @RequestParam String uni,
            @RequestParam String category, @RequestParam String and, @RequestParam String or,
            @RequestParam String secialand, Model model, HttpServletRequest request) {
        LOGGER.info("starting evaluating of extended Search");
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("searchTerm", and);
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (!and.isEmpty()) {
            boolQuery.must(andQuery(and));
        }
        if (!or.isEmpty()) {
            boolQuery.should(orQuery(or));
        }
        if (!author.isEmpty()) {
            QueryBuilder docsfromAuthors = getPaperfromAuthors(authorQuery(author));
            boolQuery.should(docsfromAuthors);

        }
        if (!uni.isEmpty()) {
            LOGGER.debug("Search for university");
            QueryBuilder universityIdsQuery = getUniversitiesIdsQueryBuilder(universityQuery(uni));
            QueryBuilder paperQueryBuilder = getPaperQueryBuilder(universityIdsQuery);
            LOGGER.debug("UniversityQuery Build {}", universityIdsQuery.toString());
            boolQuery.should(paperQueryBuilder);

        }
        if (!category.isEmpty()) {
            boolQuery.must(categoryQuery(category));

        }
        // if (!tags.isEmpty()) {
        // boolQuery.must(tagsQuery(tags));
        //
        // }
        if (!secialand.isEmpty()) {
            // exakt der inhalt
            boolQuery.should(secialandQuery(secialand));

        }

        prepareResult(model, paperService.search(boolQuery));
        return "resultPage";
    }

    /**
     * uni
     * 
     * @param uni
     * @return
     */
    private QueryBuilder universityQuery(String uni) {
        LOGGER.debug("University Search");
        // TODO - Author field is an id that point to the author in the author index
        // have to search the author in index author, get the id and have to search the authorId in field author of
        // index paper IMPORTANT the authorsId is an array!!
        QueryBuilder universityQuery = QueryBuilders.queryString(uni).useDisMax(true).field("name")
                .defaultOperator(Operator.OR);

        LOGGER.debug("universityQuery: {}", universityQuery.toString());

        return universityQuery;
    }

    /**
     * 
     * @param universityResponse
     * @return
     */
    private QueryBuilder getPaperQueryBuilder(QueryBuilder universityQuery) {
        LOGGER.debug("universityQuery for the search of author {}", universityQuery.toString());

        SearchResponse authorResponse = authorService.search(universityQuery);
        QueryBuilder paperIdsQuery;
        List<String> paperIds = new ArrayList<String>();
        for (SearchHit hit : authorResponse.getHits().getHits()) {
            if (hit.isSourceEmpty()) {
                LOGGER.info("source is empty");
            }
            LOGGER.info("id of the  {}", hit.getId());
            LOGGER.info("score of the hit {}", hit.getScore());

            Map<String, Object> resultMap = hit.sourceAsMap();

            Author authorObject = AuthorHelper.source2author(resultMap);

            LOGGER.debug("Author: {}", authorObject.getName());
            paperIds.addAll(authorObject.getPaperIds());

        }
        paperIdsQuery = QueryBuilders.inQuery("paperId", paperIds.toArray());
        LOGGER.debug("query with paperIds for documentSearch {}", paperIdsQuery.toString());
        return paperIdsQuery;
    }

    private QueryBuilder getUniversitiesIdsQueryBuilder(QueryBuilder universityQuery) {
        SearchResponse universityResponse = universityService.search(universityQuery);
        QueryBuilder universityIdsQuery;
        // List<String> universityIds = new ArrayList<String>();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        for (SearchHit hit : universityResponse.getHits().getHits()) {
            if (hit.isSourceEmpty()) {
                LOGGER.info("source is empty");
            }
            LOGGER.info("id of the university {}", hit.getId());
            LOGGER.info("score of the hit {}", hit.getScore());

            Map<String, Object> resultMap = hit.sourceAsMap();

            University universityObject = UniversityHelper.source2University(resultMap);
            FieldQueryBuilder fieldQuery = QueryBuilders.fieldQuery("universityId", universityObject.getUniversityId());
            boolQuery.should(fieldQuery);
            LOGGER.debug("university: {}", universityObject.getName());
            // universityIds.add(universityObject.getUniversityId());

        }
        // universityIdsQuery = QueryBuilders.inQuery("universityId", universityIds.toArray());
        LOGGER.debug("query with universityIds for author search {}", boolQuery.toString());
        return boolQuery;
    }

    /**
     * Auhor search at authors (or concat), extract the paper ids and return a query for the search at the paperids
     * 
     * @param author
     * @return the Query with the paperIds
     */
    private QueryBuilder authorQuery(String author) {
        LOGGER.debug("Author Search");
        // TODO - Author field is an id that point to the author in the author index
        // have to search the author in index author, get the id and have to search the authorId in field author of
        // index paper IMPORTANT the authorsId is an array!!
        QueryBuilder authorQuery = QueryBuilders.queryString(author).useDisMax(true)
                .defaultOperator(QueryStringQueryBuilder.Operator.OR).analyzer("simple").field("lastName")
                .field("name");
        LOGGER.debug("authorQuery: {}", authorQuery.toString());

        return authorQuery;
    }

    /**
     * @param authorQuery
     * @return
     */
    private QueryBuilder getPaperfromAuthors(QueryBuilder authorQuery) {
        QueryBuilder authorDocQuery;
        SearchResponse authorResponse = authorService.search(authorQuery);
        List<String> paperIds = new ArrayList<String>();
        for (SearchHit hit : authorResponse.getHits().getHits()) {
            if (hit.isSourceEmpty()) {
                LOGGER.info("source is empty");
            }
            LOGGER.info("id of the author {}", hit.getId());
            LOGGER.info("score of the hit {}", hit.getScore());

            Map<String, Object> resultMap = hit.sourceAsMap();

            Author authorObject = AuthorHelper.source2author(resultMap);

            LOGGER.debug("author: {}", authorObject.getName());
            LOGGER.debug("paper: {}", authorObject.getPaperIds());
            paperIds.addAll(authorObject.getPaperIds());

        }
        authorDocQuery = QueryBuilders.inQuery("paperId", paperIds.toArray());
        LOGGER.debug("query with authorIds for documentSearch {}", authorDocQuery.toString());
        return authorDocQuery;
    }

    /**
     * tags aka keywords
     * 
     * @param tags
     * @return
     */
    private QueryBuilder tagsQuery(String tags) {
        LOGGER.debug("tagsQuery build");
        tags = tags.replace("+", " ");
        QueryBuilder tagsQuery = QueryBuilders.queryString(tags).field("keywords");
        LOGGER.debug("query build: {}", tagsQuery.toString());
        return tagsQuery;
    }

    /**
     * category aka kindOf
     * 
     * @param category
     * @return
     */
    private QueryBuilder categoryQuery(String category) {
        LOGGER.debug("categoryQuery build");
        category = category.replace("+", " ");
        QueryBuilder categoryQuery = QueryBuilders.queryString(category).field("keywords");
        LOGGER.debug("query build: {}", categoryQuery.toString());
        return categoryQuery;
    }

    /**
     * secialand
     * 
     * @param secialand
     * @return
     */
    private QueryBuilder secialandQuery(String secialand) {
        LOGGER.debug("secialand query builder");
        secialand = secialand.replace("+", " ");
        TextQueryBuilder textPhraseQuery = QueryBuilders.textPhraseQuery("content", secialand);
        LOGGER.debug("query build: {}", textPhraseQuery.toString());
        return textPhraseQuery;

    }

    /**
     * OR
     * 
     * @param or
     * @return the or query
     */
    private QueryBuilder orQuery(String or) {
        LOGGER.debug("or is not empty");
        or = or.replace("+", " ");
        QueryBuilder orQuery = QueryBuilders.queryString(or).field("title").field("content");
        LOGGER.debug("query build: {}", orQuery.toString());
        return orQuery;
    }

    /**
     * and
     * 
     * @param and
     * @return
     */
    private QueryBuilder andQuery(String and) {
        LOGGER.debug("and is not empty");
        and = and.replace("+", " ");
        QueryBuilder andQuery = QueryBuilders.queryString(and).field("title").field("content");
        LOGGER.debug("query build: {}", andQuery.toString());
        return andQuery;
    }

    /**
     * @param model
     * @param boolQuery
     */
    private void prepareResult(Model model, SearchResponse response) {
        // put all the subqueries together
        LOGGER.info("result preparation");
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
    }

}
