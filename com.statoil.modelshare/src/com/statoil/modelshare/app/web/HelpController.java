package com.statoil.modelshare.app.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.statoil.modelshare.Client;
import com.statoil.modelshare.app.config.RepositoryConfig;
import com.statoil.modelshare.controller.ModelRepository;


@Controller
@RequestMapping("/")
public class HelpController extends AbstractController {

	@Autowired
	private ModelRepository modelrepository;

	@Autowired
	private RepositoryConfig repositoryConfig;
	
	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public String help(ModelMap map, Principal principal) {
		map.addAttribute("title", "Help");
		Client user = modelrepository.getUser(principal.getName());
		map.addAttribute("topLevel", modelrepository.getRoot(user).getAssets());
		String text = repositoryConfig.getHelpText();
		if (null == text){
			text = "Help text have not been specified. Please set the <b>help.text</b> property in the service configuration.";
		}
		map.addAttribute("text", text);
		return "help";
	}
	
}
