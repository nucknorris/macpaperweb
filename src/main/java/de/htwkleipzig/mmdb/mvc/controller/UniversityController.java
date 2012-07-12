/**
 * 
 */
package de.htwkleipzig.mmdb.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.htwkleipzig.mmdb.model.Author;
import de.htwkleipzig.mmdb.model.University;
import de.htwkleipzig.mmdb.service.AuthorService;
import de.htwkleipzig.mmdb.service.UniversityService;
import de.htwkleipzig.mmdb.util.OwnHash;

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

    @Autowired
    private AuthorService authorService;

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
    public String authorCreate(Model model) {
        LOGGER.debug("create a new university");

        model.addAttribute("university", new University());
        return "university";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String saveUniversity(@ModelAttribute("university") University university, BindingResult bindingResult) {
        LOGGER.debug("Received request to update a university");

        if (bindingResult.hasErrors()) {
            LOGGER.debug("error:" + bindingResult.getAllErrors());
        }
        if (university.getUniversityId().isEmpty()) {
            LOGGER.debug("university id is empty");
            university.setUniversityId(OwnHash.ownHash(university.getName(), university.getCity()));
        }
        if (university.getAuthorIds() == null) {
            LOGGER.debug("university id is empty");
            List<String> authorIds = new ArrayList<String>();
            authorIds.add("");
            university.setAuthorIds(authorIds);
        } else {
            // store universityId in author

            for (String authorId : university.getAuthorIds()) {
                Author author = authorService.get(authorId);
                author.setUniversityId(university.getUniversityId());
                authorService.updateAuthor(author);
            }
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
        LOGGER.info("getting all universities");
        return universityService.getAll().toString();
    }

    @RequestMapping(value = "/delete")
    public String deletePaper(@RequestParam("universityId") String universityId, HttpServletResponse resp) {
        LOGGER.debug("Received request to delete university");
        universityService.delete(universityId);
        LOGGER.debug("university with universityId {} deleted", universityId);
        return "redirect:/management/";
    }

}
