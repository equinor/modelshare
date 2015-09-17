package com.statoil.modelshare.app.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/")
public class HelpController {

	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public String printHelp(ModelMap model) {
		model.addAttribute("title", "Help");
		return "help";
	}
	
}
