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
public class HomeController extends AbstractController {

	@Autowired
	private RepositoryConfig repositoryConfig;

	@Autowired
	private ModelRepository modelrepository;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printWelcome(ModelMap map, Principal principal) {
		map.addAttribute("title", "Modelshare");
		Client user = modelrepository.getUser(principal.getName());
		map.addAttribute("topLevel", modelrepository.getRoot(user).getAssets());
		String text = repositoryConfig.getWelcomeText();
		if (null == text){
			text = "Welcome text have not been specified. Please set the <b>welcome.text</b> property in the service configuration.";
		}
		map.addAttribute("text", text);
		return "index";
	}
}
