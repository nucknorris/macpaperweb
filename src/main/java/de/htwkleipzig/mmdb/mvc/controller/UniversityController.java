/**
 * 
 */
package de.htwkleipzig.mmdb.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.htwkleipzig.mmdb.model.University;
import de.htwkleipzig.mmdb.service.UniversityService;

/**
 * @author spinner0815, men0x
 * 
 */
@Controller
@RequestMapping("/university")
public class UniversityController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UniversityController.class);

	@Autowired
	private UniversityService universityService;

	public UniversityController() {
	}

	@RequestMapping(value = "")
	public String elasticstart(Model model) {
		LOGGER.info("startpage university");
		model.addAttribute("attribute", "university");
		return "university";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String saveUniversity(
			@ModelAttribute("university") University university,
			BindingResult bindingResult) {
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
}
