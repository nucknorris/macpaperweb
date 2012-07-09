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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.htwkleipzig.mmdb.model.Author;
import de.htwkleipzig.mmdb.service.AuthorService;

/**
 * @author spinner0815, men0x
 * 
 */
@Controller
@RequestMapping("/author")
public class AuthorController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AuthorController.class);

	@Autowired
	private AuthorService authorService;

	public AuthorController() {
	}

	@RequestMapping(value = "/{recievedAuthorID}")
	public String elasticstart(
			@PathVariable("recievedAuthorID") String recievedAuthorID,
			Model model) {
		LOGGER.info("startpage author {}", recievedAuthorID);
		model.addAttribute("recievedUniversityID", recievedAuthorID);
		Author author = authorService.get(recievedAuthorID);
		model.addAttribute("author", author);
		return "author";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String saveUniversity(@ModelAttribute("author") Author author,
			BindingResult bindingResult) {
		LOGGER.debug("Received request to update a author");

		if (bindingResult.hasErrors()) {
			LOGGER.debug("error:" + bindingResult.getAllErrors());
		}

		LOGGER.debug("author: {}", author.getAuthorId());
		LOGGER.debug("name: {}", author.getName());
		authorService.updateAuthor(author);
		LOGGER.debug("author saved");
		return "redirect:/author/" + author.getAuthorId() + "/";
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String testJSP() {

		return "test";
	}
	
	   @RequestMapping(value = "/authorpopup", method = RequestMethod.GET)
	    public String authorPopup() {

	        return "authorpopup";
	    }
}
