package de.htwkleipzig.mmdb.service.impl;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.htwkleipzig.mmdb.model.Author;
import de.htwkleipzig.mmdb.service.AuthorService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/integration/spring-integration-context.xml" })
public class AuthorImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorImplTest.class);

    @Autowired
    AuthorService authorService;

    @Test
    public void saveAuthorTest() {
        LOGGER.debug("saveAuthorTest");
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
        Assert.assertEquals(true, authorService.save(author));
    }

    @Test
    public void saveAuthor2Test() {
        LOGGER.debug("saveAuthor2Test()");
        Author author = new Author();
        author.setAuthorId("OliDerMickrige");
        author.setEmail("oli@dermickrige.com");
        author.setLastname("Der Mickrige");
        author.setName("Oli");
        List<String> paperIds = new ArrayList<String>();
        paperIds.add("paper3");
        paperIds.add("paper4");
        author.setPaperIds(paperIds);
        author.setTitle("NONE");
        author.setUniversityId("UniIdAwesome");
        LOGGER.debug("save the author");
        Assert.assertEquals(true, authorService.save(author));
    }

    @Test
    public void getAuthorTest() {
        LOGGER.debug("getAuthorTest()");
        Author author = authorService.get("OliDerMickrige");
        Assert.assertNotNull(author);
    }

    @Test
    public void updateAuthorTest() {
        LOGGER.debug("updateAuthorTest()");
        Author author = authorService.get("OliDerMickrige");
        Assert.assertNotNull(author);

        LOGGER.debug("change the lastname for updating {}", author.toString());
        author.setLastname("nciht mehr so mickrig");
        LOGGER.debug("the lastname is {}", author.getLastname());
        Assert.assertEquals(true, authorService.updateAuthor(author));

    }

    @Test
    public void getAuthorAfterUpdateTest() {
        LOGGER.debug("getAuthorTest()");
        Author author = authorService.get("OliDerMickrige");
        LOGGER.debug("and the author is {}", author.toString());
        Assert.assertNotNull(author);
    }

    @Test
    @Ignore
    public void deleteAuthorTest() {
        LOGGER.debug("deleteAuthorTest()");
        Assert.assertEquals(true, authorService.delete("OliDerMickrige"));
        Assert.assertEquals(true, authorService.delete("LarsDerGrosse"));
        Assert.assertEquals(false, authorService.delete("hurz"));
    }
}
