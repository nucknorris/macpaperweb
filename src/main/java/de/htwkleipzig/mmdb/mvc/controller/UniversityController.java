/**
 * 
 */
package de.htwkleipzig.mmdb.mvc.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.htwkleipzig.mmdb.model.University;
import de.htwkleipzig.mmdb.service.UniversityService;

/**
 * @author spinner0815, men0x
 * 
 */
@Controller
@RequestMapping("/university")
public class UniversityController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UniversityController.class);

    @Autowired
    private UniversityService universityService;

    public UniversityController() {
    }

    @RequestMapping(value = "/{recievedUniversityID}")
    public String elasticstart(@PathVariable("recievedUniversityID") String recievedUniversityID, Model model) {
        LOGGER.info("startpage university {}", recievedUniversityID);
        model.addAttribute("recievedUniversityID", recievedUniversityID);
        University university = universityService.get(recievedUniversityID);
        model.addAttribute("university", university);
        return "university";
    }

    /**
     * @param name
     * @param lastName
     * @param Model
     * @return
     */
    @RequestMapping(value = "/createUniversity")
    public String universityCreate(String name, String city, String postcode, String street, String street2,
            String housenumber, String country, String authorids, Model model) {

        University university = new University();
        university.setUniversityId(name + country);

        List<String> authorIds = new ArrayList<String>();
        authorids = authorids.replaceAll(" ", "");
        String[] splittetAuthors = authorids.split(",");
        authorIds.addAll(Arrays.asList(splittetAuthors));

        university.setAuthorIds(authorIds);
        university.setCity(city);
        university.setCountry(country);
        university.setHousenumber(housenumber);
        university.setName(name);
        university.setPostcode(postcode);
        university.setStreet(street);
        university.setStreet2(street2);
        universityService.save(university);
        model.addAttribute("university", university);
        return "university";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String saveUniversity(@ModelAttribute("university") University university, BindingResult bindingResult) {
        LOGGER.debug("Received request to update a university");

        if (bindingResult.hasErrors()) {
            LOGGER.debug("error:" + bindingResult.getAllErrors());
        }

        LOGGER.debug("university: {}", university.getUniversityId());
        LOGGER.debug("title: {}", university.getName());
        universityService.updateUniversity(university);
        LOGGER.debug("university saved");
        return "redirect:/university/" + university.getUniversityId() + "/";
    }

    @RequestMapping(value = "/alluniversities")
    @ResponseBody
    public String getAllUniversities() {
        LOGGER.info("all university shit");
        return universityService.getAll().toString();
    }
}
