package de.htwkleipzig.mmdb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.htwkleipzig.mmdb.service.PaperService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/integration/spring-integration-context.xml" })
public class ParserTest {

    @Autowired
    PaperService paperService;

    @Test
    public void testPdfParser() {
        paperService.delete("809fe0e79e055d35ef11f6aedf83eff6");
    }
}
