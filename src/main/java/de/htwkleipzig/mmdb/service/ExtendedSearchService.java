package de.htwkleipzig.mmdb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author men0x
 */
public class ExtendedSearchService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExtendedSearchService.class);

	public String search() {
		LOGGER.info("call extended search");
		return "extendedSearch";
	}

	@RequestMapping(value = "/runNormalSearch")
	private String runSearch(@RequestParam(required = true) String searchPhrase) {
		LOGGER.info("run search");
		return "searchPhrase";
	}

}
