package de.htwkleipzig.mmdb.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.ToHTMLContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PDFParser {
    private static final Logger logger = LoggerFactory.getLogger(PDFParser.class);

    public static String pdfParser(String path) {
        Tika tika = new Tika();
        Metadata met = new Metadata();
        URL pdf =null;
        try {
            pdf = new URL(path);
        } catch (MalformedURLException e1) {
            logger.error("error while loading from path");
            e1.printStackTrace();
        }
//        LinkContentHandler linkHandler = new LinkContentHandler();
//        ContentHandler textHandler = new BodyContentHandler();
        ToHTMLContentHandler toHTMLHandler = new ToHTMLContentHandler();
//        TeeContentHandler teeHandler = new TeeContentHandler(linkHandler, textHandler, toHTMLHandler);
        ParseContext parseContext = new ParseContext();

        try {
            Parser parser = tika.getParser();
            parser.parse(pdf.openStream(), toHTMLHandler, met, parseContext);
            logger.info(System.getProperty("line.separator") + "Metadata:\t" + met);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage() + " " + e.getCause());
        }
        logger.info(toHTMLHandler.toString());
        return toHTMLHandler.toString();
    }
}
