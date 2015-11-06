package com.statoil.modelshare.app.web;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.statoil.modelshare.app.service.ArchiveService;


@Controller
public class HomeController {
	private ArchiveService service = new ArchiveService();
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printWelcome(ModelMap model, Principal principal) {
		model.addAttribute("title", "Modelshare");
		model.addAttribute("locations", service.getLocations(principal));
		return "index";
	}
}