package de.htwkleipzig.mmdb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author men0x
 */
public class NormalSearchService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExtendedSearchService.class);

	public String search() {
		LOGGER.info("Normale Suche aufgerufen!");
		return "extendedSearch";
	}

	private void startSearch() {
		LOGGER.info("Suche gestartet!");
		listPaper();

	}

	public void listPaper() {
		LOGGER.info("Ergebnisse ausgeben!");

	}
}
