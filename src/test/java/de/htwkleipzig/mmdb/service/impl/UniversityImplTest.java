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

import de.htwkleipzig.mmdb.model.University;
import de.htwkleipzig.mmdb.service.UniversityService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/integration/spring-integration-context.xml" })
public class UniversityImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniversityImplTest.class);

    @Autowired
    UniversityService universityService;

    @Test
    public void saveUniversityTest() {
        LOGGER.debug("saveUniversityTest");
        University university = new University();
        university.setUniversityId("htwkLeipzig");
        university.setName("htwk Leipzig");
        university.setStreet("dahinten");
        university.setHousenumber("12");
        university.setCity("Leipzig");
        university.setCountry("Germany");
        university.setPostcode("04277");
        List<String> authorIds = new ArrayList<String>();
        authorIds.add("author1");
        authorIds.add("author2");
        university.setAuthorIds(authorIds);
        LOGGER.debug("save the university");
        Assert.assertEquals(true, universityService.save(university));
    }

    @Test
    public void saveUniversity2Test() {
        LOGGER.debug("saveUniversity2Test()");
        University university = new University();
        university.setUniversityId("UniDarmstadt");
        university.setName("uni Darmstadt");
        university.setStreet("andere");
        university.setStreet2("Seite");
        university.setHousenumber("23");
        university.setCity("Darmstadt");
        university.setCountry("Germany");
        university.setPostcode("63364");
        List<String> authorIds = new ArrayList<String>();
        authorIds.add("author3");
        authorIds.add("author2");
        university.setAuthorIds(authorIds);
        LOGGER.debug("save the university");
        Assert.assertEquals(true, universityService.save(university));
    }

    @Test
    public void getUniversityTest() {
        LOGGER.debug("getUniversityTest()");
        University university = universityService.get("htwkLeipzig");
        Assert.assertNotNull(university);
    }

    @Test
    public void updateUniversityTest() {
        LOGGER.debug("updateUniversityTest()");
        University university = universityService.get("htwkLeipzig");
        Assert.assertNotNull(university);

        LOGGER.debug("change the lastname for updating {}", university.toString());
        university.setStreet("doch nicht dahinten");
        LOGGER.debug("the lastname is {}", university.getStreet());
        Assert.assertEquals(true, universityService.updateUniversity(university));

    }

    @Test
    public void getUniversityAfterUpdateTest() {
        LOGGER.debug("getUniversityTest()");
        University university = universityService.get("htwkLeipzig");
        LOGGER.debug("and the university is {}", university.toString());
        Assert.assertNotNull(university);
    }

    @Test
    @Ignore
    public void deleteUniversityTest() {
        LOGGER.debug("deleteUniversityTest()");
        Assert.assertEquals(true, universityService.delete("htwkLeipzig"));
        Assert.assertEquals(true, universityService.delete("UniDarmstadt"));
        Assert.assertEquals(false, universityService.delete("hurz"));
    }
}
