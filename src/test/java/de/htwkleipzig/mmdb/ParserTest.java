package de.htwkleipzig.mmdb;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.htwkleipzig.mmdb.util.TikaParser;

public class ParserTest {

	@Test
	public void testPdfParser() {
		URL pdfUrl = (ParserTest.class.getClassLoader().getResource("10.1.1.122.5934.pdf"));
		TikaParser parser = new TikaParser();

		List<String> listOfKeywords = new ArrayList<String>();
		listOfKeywords.add("Abstract");
//		parser.startTokenizing(listOfKeywords, pdfUrl);

	}
}
