package de.htwkleipzig.mmdb.vaadin;

import java.util.Map;

import org.elasticsearch.action.get.GetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.vaadin.ui.Button;

import de.htwkleipzig.mmdb.model.Paper;
import de.htwkleipzig.mmdb.service.ElasticsearchService;

public class HelloButton extends Button {

    private static final String INDEXTYPE = "indextype";

    private static final String PAPERINDEX = "paperindex";

    private static final Logger logger = LoggerFactory.getLogger(HelloButton.class);

    // @Autowired
    // private HelloService helloService;
    @Autowired
    private ElasticsearchService elasticService;

    public HelloButton() {
        super("Click me!");

        Injector.inject(this);

        addListener(new ClickListener() {

            public void buttonClick(ClickEvent event) {
                // String helloText = helloService.getHelloText();
                getApplication().getMainWindow().showNotification(getPaper("paper2"));
            }
        });

    }

    public String getPaper(String id) {

        logger.info("starting list test");

        GetResponse resp = elasticService.get(PAPERINDEX, INDEXTYPE, "jsonmap");
        Map<String, Object> source = resp.getSource();
        if (source != null && !source.isEmpty()) {
            logger.info("source ist nicht null oder empty");
            if (source.containsKey("paper2")) {
                logger.info("paper sollte existieren");
                String paper = (String) source.get("paper2");
                logger.info("paper wird ausgespuckt");
                Gson gson = new Gson();

                // convert java object to JSON format,
                // and returned as JSON formatted string
                Paper fromJson = gson.fromJson(paper, Paper.class);
                if (fromJson != null) {
                    logger.info("sollte richtig gemapped worden sein");
                    logger.info("title: {}", fromJson.getTitle());
                    logger.info("number of authors: {}", fromJson.getAuthors().size());
                }
                return paper;
            }
        }
        logger.info("paper existiert wohl nicht");
        return null;
    }
}
