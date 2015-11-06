package com.statoil.modelshare.app.web;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.statoil.modelshare.app.service.ArchiveService;


@Controller
@RequestMapping("/")
public class HelpController {
	private ArchiveService service = new ArchiveService();
	
	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public String printHelp(ModelMap model, Principal principal) {
		model.addAttribute("title", "Help");
		model.addAttribute("topLevel", service.getTopLevel(principal));
		return "help";
	}
	
}
