package com.statoil.modelshare.app.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.statoil.modelshare.app.service.ModelFileService;

@Controller
@RequestMapping("/")
public class ModelFileController {

	
	
	@RequestMapping(value = "/archive", method = RequestMethod.GET)
  public String archive(ModelMap model) {
		model.addAttribute("title", "Archive");
		model.addAttribute("modelFiles", ModelFileService.getModelFiles());
    return "archive";
  }

}