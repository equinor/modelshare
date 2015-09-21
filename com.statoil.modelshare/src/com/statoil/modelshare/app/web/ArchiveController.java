package com.statoil.modelshare.app.web;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.statoil.modelshare.app.service.ModelInformation;
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
		public String doShow (ModelMap model, @RequestParam String item) throws FileNotFoundException, UnsupportedEncodingException {
			if(item!=null && item != ""){	
				ModelInformation modelInformation = service.getModelInformation(item);
				model.addAttribute("modelInformation", modelInformation);
			}
			MenuItem menuItem = service.getMenuItems();
			model.addAttribute("node", menuItem);
		return "archive";
	}

}