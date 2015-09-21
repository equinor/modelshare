package com.statoil.modelshare.app.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SigninController {

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String login(ModelMap model, @RequestParam(value = "logout", required = false) String logout) {
		return "signin";
	}
	@RequestMapping(value = "/signout", method = RequestMethod.GET)
	public String logout(Model model) {
		return "signin";
	}
}