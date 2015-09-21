package com.statoil.modelshare.app.web;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.statoil.modelshare.Model;
import com.statoil.modelshare.app.service.ArchiveService;
import com.statoil.modelshare.app.service.MenuItem;

@Controller
@RequestMapping("/")
public class ArchiveController {
	private ArchiveService service = new ArchiveService();
	
	@RequestMapping(value={"/archive", "/showModel"}, method = RequestMethod.GET)
	/*
	 * TODO: 
	 * - Implement collapsible node tree, preferably with CSS
	 * - Handle exceptions
	 * 
	 */
		public String doShow (ModelMap model, @RequestParam(value = "item", required = false) String item, Principal principal) throws FileNotFoundException, UnsupportedEncodingException {
			Model currentModel = service.getModelFromAssets(item);
			if(currentModel!=null){
				model.addAttribute("currentModel", currentModel);
			}
			model.addAttribute("title", "Model archive");
			MenuItem menuItem = service.getMenuItemsFromAssets(principal.getName());
			model.addAttribute("node", menuItem);
		return "archive";
	}

}