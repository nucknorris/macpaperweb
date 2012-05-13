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

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloButton.class);

    // @Autowired
    // private HelloService helloService;
    /**
     * @uml.property  name="elasticService"
     * @uml.associationEnd  readOnly="true"
     */
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

        LOGGER.info("starting list test");

        GetResponse resp = elasticService.get("jsonmap");
        Map<String, Object> source = resp.getSource();
        if (source != null && !source.isEmpty()) {
            LOGGER.info("source ist nicht null oder empty");
            if (source.containsKey("paper2")) {
                LOGGER.info("paper sollte existieren");
                String paper = (String) source.get("paper2");
                LOGGER.info("paper wird ausgespuckt");
                Gson gson = new Gson();

                // convert java object to JSON format,
                // and returned as JSON formatted string
                Paper fromJson = gson.fromJson(paper, Paper.class);
                if (fromJson != null) {
                    LOGGER.info("sollte richtig gemapped worden sein");
                    LOGGER.info("title: {}", fromJson.getTitle());
                    LOGGER.info("number of authors: {}", fromJson.getAuthors().size());
                }
                return paper;
            }
        }
        LOGGER.info("paper existiert wohl nicht");
        return null;
    }
}
