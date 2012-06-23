package de.htwkleipzig.mmdb.service.impl;

import java.util.ArrayList;
import java.util.Date;
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

import de.htwkleipzig.mmdb.model.Paper;
import de.htwkleipzig.mmdb.service.PaperService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/integration/spring-integration-context.xml" })
public class PaperImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaperImplTest.class);

    @Autowired
    PaperService paperService;

    @Test
    public void savePaperTest() {
        LOGGER.debug("savePaperTest");
        Paper paper = new Paper();
        paper.setPaperId("paperId");
        paper.setTitle("title");
        paper.setPaperAbstract("paperAbstract");
        List<String> authorIds = new ArrayList<String>();
        authorIds.add("author1");
        authorIds.add("author2");
        paper.setAuthors(authorIds);

        paper.setCreateDate(new Date(System.currentTimeMillis()));
        List<String> keywords = new ArrayList<String>();
        keywords.add("keyword1");
        keywords.add("keyword2");
        paper.setKeywords(keywords);
        paper.setKindOf("kindOf");
        paper.setContent("content");
        paper.setLatexBib("latexBib");
        paper.setUploadDate(new Date(System.currentTimeMillis()));
        paper.setFileName("filename");
        LOGGER.debug("save the paper");
        Assert.assertEquals(true, paperService.save(paper));
    }

    @Test
    public void savePaper2Test() {
        LOGGER.debug("savePaperTest2");
        Paper paper = new Paper();
        paper.setPaperId("paperId2");
        paper.setTitle("title2");
        paper.setPaperAbstract("paperAbstract2");
        List<String> authorIds = new ArrayList<String>();
        authorIds.add("author3");
        authorIds.add("author2");
        paper.setAuthors(authorIds);

        paper.setCreateDate(new Date(System.currentTimeMillis()));
        List<String> keywords = new ArrayList<String>();
        keywords.add("keyword3");
        keywords.add("keyword2");
        paper.setKeywords(keywords);
        paper.setKindOf("kindOf2");
        paper.setContent("content2");
        paper.setLatexBib("latexBib2");
        paper.setUploadDate(new Date(System.currentTimeMillis()));
        paper.setFileName("filename2");
        LOGGER.debug("save the paper");
        Assert.assertEquals(true, paperService.save(paper));
    }

    @Test
    public void getPaperTest() {
        LOGGER.debug("getPaperTest()");
        Paper paper = paperService.get("paperId");
        Assert.assertNotNull(paper);
    }

    @Test
    public void updatePaperTest() {
        LOGGER.debug("updatePaperTest()");
        Paper paper = paperService.get("paperId");
        Assert.assertNotNull(paper);

        LOGGER.debug("change the lastname for updating {}", paper.toString());
        paper.setTitle("hot damn title");
        LOGGER.debug("the lastname is {}", paper.getTitle());
        Assert.assertEquals(true, paperService.updatePaper(paper));

    }

    @Test
    public void getPaperAfterUpdateTest() {
        LOGGER.debug("getPaperTest()");
        Paper paper = paperService.get("paperId");
        LOGGER.debug("and the paper is {}", paper.toString());
        Assert.assertNotNull(paper);
    }

    @Test
    @Ignore
    public void deletePaperTest() {
        LOGGER.debug("deletePaperTest()");
        Assert.assertEquals(true, paperService.delete("paperId"));
        Assert.assertEquals(true, paperService.delete("paperId2"));
        Assert.assertEquals(false, paperService.delete("hurz"));
    }
}
