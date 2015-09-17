package com.statoil.modelshare.app.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.statoil.modelshare.app.service.MenuItem;
import com.statoil.modelshare.app.service.ModelFileService;

@Controller
@RequestMapping("/")
public class ModelFileController {
	private ModelFileService service = new ModelFileService();
	private String location = "Documents/Models";
	
	
	@RequestMapping(value = "/archive", method = RequestMethod.GET)
	public String archive(ModelMap model) {
		model.addAttribute("title", "Archive");
		model.addAttribute("node", service.getMenuItems(location));
		model.addAttribute("menuItem", new MenuItem());
		return "archive";
	}
	
	@RequestMapping(value="/archive/doShow", method = RequestMethod.GET)
		public String doShow (ModelMap model, @RequestParam String path) {
		if(path != null && !"".equals(path)){
			model.addAttribute("menuItem", service.getAssetFromMenuItem(path));
		}else{
			model.addAttribute("menuItem", new MenuItem());
		}
		
		model.addAttribute("title", "Archive");
		model.addAttribute("node", service.getMenuItems(location));
		return "archive";
	}

}