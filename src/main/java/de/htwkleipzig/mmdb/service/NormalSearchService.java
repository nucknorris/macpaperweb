package de.htwkleipzig.mmdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.htwkleipzig.mmdb.model.Paper;
import de.htwkleipzig.mmdb.util.PaperHelper;

/**
 * 
 * @author men0x
 */
public class NormalSearchService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExtendedSearchService.class);
	@Autowired
	private PaperService paperService;

	public String search() {
		LOGGER.info("Einfache Suche aufgerufen!");
		return "normalSearch";
	}

	@RequestMapping(value = "/runNormalSearch")
	private String runSearch(@RequestParam(required = true) String searchPhrase) {
		LOGGER.info("Suche gestartet!");
		QueryStringQueryBuilder query = QueryBuilders.queryString(searchPhrase)
				.allowLeadingWildcard(false).useDisMax(true);
		SearchResponse response = paperService.search(query);

		List<Paper> papers = new ArrayList<Paper>();
		for (SearchHit hit : response.getHits().getHits()) {
			if (hit.isSourceEmpty()) {
				LOGGER.info("source is empty");
			}

			Map<String, Object> resultMap = hit.sourceAsMap();

			Paper paper = PaperHelper.source2Paper(resultMap);

			paper.setContent("");
			LOGGER.debug("paper: {}", paper.getPaperId());
			papers.add(paper);

		}
		return "searchPhrase";
	}
}
