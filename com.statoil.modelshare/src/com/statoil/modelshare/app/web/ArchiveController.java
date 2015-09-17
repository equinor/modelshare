package com.statoil.modelshare.app.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.statoil.modelshare.app.service.ModelInformation;
import com.statoil.modelshare.app.service.ArchiveService;

@Controller
@RequestMapping("/")
public class ArchiveController {
	private ArchiveService service = new ArchiveService();
	
	@RequestMapping(value={"/archive", "/showModel"}, method = RequestMethod.GET)
	/*
	 * TODO: 
	 * - Implement collapsible node tree, preferably with CSS
	 * 
	 */
		public String doShow (ModelMap model, @RequestParam String item) {
			ModelInformation modelInformation = service.getModelInformation(item);
			model.addAttribute("modelInformation", modelInformation);
			model.addAttribute("node", service.getMenuItems());
		return "archive";
	}

}