package de.htwkleipzig.mmdb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.htwkleipzig.mmdb.model.Author;
import de.htwkleipzig.mmdb.service.AuthorService;
import de.htwkleipzig.mmdb.service.impl.AuthorServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/integration/spring-integration-context.xml" })
public class AuthorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorServiceImpl.class);

    @Autowired
    AuthorService authorService;

    @Test
    public void saveAuthorTest() {
        LOGGER.debug("create author");
        Author author = new Author();
        author.setAuthorId("LarsDerGrosse");
        author.setEmail("lars@dergrosse.com");
        author.setLastname("Der Grosse");
        author.setName("Lars");
        List<String> paperIds = new ArrayList<String>();
        paperIds.add("paper1");
        paperIds.add("paper2");
        author.setPaperIds(paperIds);
        author.setTitle("Von und Zu");
        author.setUniversityId("UniId");
        LOGGER.debug("save the author");
        try {
            authorService.saveAthuor(author);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        LOGGER.debug("fine");
    }
}
