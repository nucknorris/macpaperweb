/**
 * 
 */
package de.htwkleipzig.mmdb.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.elasticsearch.search.SearchHit;
import org.springframework.ui.Model;

/**
 * @author spinner0815
 * 
 */
public class Backup {

    // /**
    // * Simply selects the home view to render by returning its name.
    // */
    // @RequestMapping(value = "/")
    // public String home(Model model, @RequestParam(required = false) String startTwitter,
    // @RequestParam(required = false) String stopTwitter) {
    //
    // if (startTwitter != null) {
    // twitterService.startTwitterAdapter();
    // return "redirect:/";
    // }
    //
    // if (stopTwitter != null) {
    // twitterService.stopTwitterAdapter();
    // return "redirect:/";
    // }
    //
    // final Collection<TwitterMessage> twitterMessages = twitterService.getTwitterMessages();
    //
    // logger.info("Retrieved {} Twitter messages.", twitterMessages.size());
    //
    // model.addAttribute("twitterMessages", twitterMessages);
    //
    // return "home";
    // }
    //
    // /**
    // * Simply selects the home view to render by returning its name.
    // */
    // @RequestMapping(value = "/ajax")
    // public String ajaxCall(Model model) {
    //
    // final Collection<TwitterMessage> twitterMessages = twitterService.getTwitterMessages();
    //
    // logger.info("Retrieved {} Twitter messages.", twitterMessages.size());
    // model.addAttribute("twitterMessages", twitterMessages);
    //
    // return "twitterMessages";
    //
    // }

    // @RequestMapping(value = "/tika")
    // public String tikaTest(Model model) {
    // Tika tika = new Tika();
    // Metadata met = new Metadata();
    // URL pdf = (HomeController.class.getClassLoader().getResource("10.1.1.122.5934.pdf"));
    // LinkContentHandler linkHandler = new LinkContentHandler();
    // ContentHandler textHandler = new BodyContentHandler();
    // ToHTMLContentHandler toHTMLHandler = new ToHTMLContentHandler();
    // TeeContentHandler teeHandler = new TeeContentHandler(linkHandler, textHandler, toHTMLHandler);
    // PDFParser pdfParser = new PDFParser();
    // ParseContext parseContext = new ParseContext();
    //
    // try {
    // Parser parser = tika.getParser();
    // // parser.parse(pdf.openStream(), toHTMLHandler, met, null);
    // pdfParser.parse(pdf.openStream(), teeHandler, met, parseContext);
    // logger.info(System.getProperty("line.separator") + "Metadata:\t" + met);
    // model.addAttribute("applicationName", met.toString());
    // model.addAttribute("toHtml", toHTMLHandler.toString());
    // model.addAttribute("link", linkHandler.toString());
    // // model.addAttribute("text", textHandler.toString());
    // model.addAttribute("text", parseContext.toString());
    // } catch (Exception e) {
    // logger.error(e.getLocalizedMessage() + " " + e.getCause());
    // }
    // return "tika";
    // }

    // @RequestMapping(value = "/elastic")
    // public String elasticSearch(Model model) {
    // Map<String, Object> test = new HashMap<String, Object>();
    //
    // logger.info("create settings");
    // Settings settings = ImmutableSettings.settingsBuilder().build();
    // logger.info("Add transport adresse");
    // TransportClient client = new TransportClient(settings);
    //
    // logger.info("add settings to transportclient");
    // client.addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300));
    // try {
    // // create an index called paperindex
    // logger.info("create an index called paperindex");
    // client.admin().indices().create(new CreateIndexRequest("paperindex")).actionGet();
    // } catch (IndexAlreadyExistsException ex) {
    // logger.warn("already exists", ex);
    // }
    // logger.info("indexRequestBuilder");
    // // index the object test on index paperindex with indextype indextype and id 3
    // IndexRequestBuilder irb = client.prepareIndex("paperindex", "indextype", "3").
    // setSource(test);
    // irb.execute().actionGet();
    // // gets the object from paperindex with indextype indextype and id 3
    // GetResponse rsp = client.prepareGet("paperindex", "indextype", "3").
    // execute().actionGet();
    // Map<String, Object> source = rsp.getSource();
    // logger.info(source.size() + "");
    // // logger.info((String) source.get("pdf"));
    //
    // // search at paperindex
    // SearchRequestBuilder builder = client.prepareSearch("paperindex");
    // // search at QuizRDF
    // QueryStringQueryBuilder qb = QueryBuilders.queryString("QuizRDF").allowLeadingWildcard(false)
    // .useDisMax(true);
    // builder.setQuery(qb);
    //
    // SearchResponse searchresponse = builder.execute().actionGet();
    // SearchHit[] docs = searchresponse.getHits().getHits();
    // for (SearchHit sd : docs) {
    // // to get explanation you'll need to enable this when querying:
    // // System.out.println(sd.getExplanation().toString());
    //
    // // if we use in mapping: "_source" : {"enabled" : false}
    // // we need to include all necessary fields in query and then to use doc.getFields()
    // // instead of doc.getSource()
    // Map<String, Object> searchSource = sd.getSource();
    // for (String key : searchSource.keySet()) {
    // logger.info((String) searchSource.get(key));
    // }
    // }
    // client.close();
    //
    // return "elastic";
    // }
}
