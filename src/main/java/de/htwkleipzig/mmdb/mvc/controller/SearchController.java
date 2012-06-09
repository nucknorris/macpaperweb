package de.htwkleipzig.mmdb.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import de.htwkleipzig.mmdb.service.ExtendedSearchService;
import de.htwkleipzig.mmdb.service.PaperService;
import de.htwkleipzig.mmdb.service.NormalSearchService;

/**
 * @author men0x
 * 
 */
@Controller
public class SearchController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SearchController.class);

	@Autowired
	private PaperService paperService;

	public SearchController() {
		LOGGER.info("SearchController aufgerufen!");
	}

	@RequestMapping(value = "/normalSearch")
	public void normalSearch() {
		new NormalSearchService().search();
	}

	@RequestMapping(value = "/extendedSearch")
	public void extendedSearch() {
		new ExtendedSearchService().search();
	}

}
