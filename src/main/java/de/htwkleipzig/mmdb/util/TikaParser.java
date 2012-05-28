package de.htwkleipzig.mmdb.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.ToHTMLContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TikaParser {
    private static final Logger logger = LoggerFactory.getLogger(TikaParser.class);

    private static final List<String> listOfKeywords = Arrays.asList("abstract", "zusammenfassung");

    /**
     * Starts tokenizing, i.e. it splits the paper by tokens and extracts the
     * token containing the keyword.
     * 
     * @param listOfKeywords
     * @param paperContent
     * @return
     */
    public String startTokenizing(String paperContent) {
        String startsWith = "<p>";
        String endsWith = "</p>";
        String extractedString = null;
        logger.info("starting tokenizing...");

        List<String> listOfTokenizedStrings = tokenize(paperContent, startsWith, endsWith);

        for (String keyword : listOfKeywords) {
            extractedString = extract(listOfTokenizedStrings, keyword, startsWith, endsWith);
            if (extractedString != null) {
                logger.debug("abstract with keyword \"{}\" extacted: {}", keyword, extractedString);
                return extractedString;
            }
        }
        return null;
    }

    public String parsePdfToXml(URL file) {
        Tika tika = new Tika();
        Metadata met = new Metadata();
        ToHTMLContentHandler toHTMLHandler = new ToHTMLContentHandler();
        ParseContext parseContext = new ParseContext();

        try {
            Parser parser = tika.getParser();
            parser.parse(file.openStream(), toHTMLHandler, met, parseContext);
            logger.info(System.getProperty("line.separator") + "Metadata:\t" + met);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage() + " " + e.getCause());
        }
        return toHTMLHandler.toString();
    }

    /**
     * Returns a list of tokenized strings.
     * 
     * @param listOfKeywords
     * @param text
     * @param startsWith
     * @param endsWith
     * @return
     */
    public List<String> tokenize(String text, String startsWith, String endsWith) {
        List<String> listOfMatches = new ArrayList<String>();

        logger.info("string to be tokenized: {}", text);

        /*
         * That pattern extracts everything betweet two html paragraph-tags
         */
        String patternString = String.format("(%s).*?(%s)", Pattern.quote(startsWith), Pattern.quote(endsWith));

        /*
         * Using the DOTALL parameter to ignore newlines
         */
        Pattern pattern = Pattern.compile(patternString, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            listOfMatches.add(matcher.group());
        }
        logger.info("all groupes tokenized!");

        return listOfMatches;
    }

    public String extract(List<String> listOfMatches, String keyword, String startsWith, String endsWith) {
        int count = 0;
        String abstractString = null;
        for (String string : listOfMatches) {

            /*
             * One paragraph containing the keyword implies, that the next
             * paragraph has to be the expected. After extracting this
             * paragraph, the html tags and the leading and trailing whitespaces
             * are going to be removed.
             */

            /*
             * First case: <p>Abstract</p><p> .... </p>
             */
            if (string.toLowerCase().contains(keyword.toLowerCase())
                    && string.length() < (keyword.length() + startsWith.length() + endsWith.length() + 5)) {
                abstractString = listOfMatches.get(count + 1);
                abstractString = cleanString(abstractString, startsWith, endsWith);
                logger.info("keyword found: {}", abstractString);
            } else if (string.toLowerCase().contains(keyword.toLowerCase())) {
                /*
                 * Second case: <p>Abstract .... </p>
                 */
                abstractString = listOfMatches.get(count);
                abstractString = cleanString(abstractString, startsWith, endsWith);
                logger.info("keyword found: {}", abstractString);
            }
            count++;
        }
        return abstractString;

    }

    private static String cleanString(String abstractString, String startsWith, String endsWith) {
        return abstractString.replace(startsWith, "").replace(endsWith, "").trim();
    }
}
