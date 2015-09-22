package com.statoil.modelshare.app.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.statoil.modelshare.Model;
import com.statoil.modelshare.ModelshareFactory;
import com.statoil.modelshare.app.service.ArchiveService;
import com.statoil.modelshare.app.service.MenuItem;

@Controller
@RequestMapping("/")
public class ArchiveController {
	private ArchiveService service = new ArchiveService();
	
	@RequestMapping(value={"/archive", "/showModel"}, method = RequestMethod.GET)
		public String doShow (ModelMap model, @RequestParam(value = "item", required = false) String item, 
				@RequestParam(required = false) boolean leaf,
				@RequestParam(required = false) boolean showNewFolder,
				@RequestParam(required = false) boolean showUploadFile,
				Principal principal) 
				throws FileNotFoundException, UnsupportedEncodingException {
			Model currentModel = service.getModelFromAssets(item);
			if(leaf){
				model.addAttribute("currentModel", currentModel);
			}else{
				model.addAttribute("currentFolder", item);
				model.addAttribute("showNewFolder", showNewFolder);
				model.addAttribute("showUploadFile", showUploadFile);
			}
			model.addAttribute("activeMenuItem", item);
			model.addAttribute("title", "Model archive");
			MenuItem menuItem = service.getMenuItemsFromAssets(principal.getName());
			model.addAttribute("node", menuItem);
		return "archive";
	}
	
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST) 
    public String importParse(@RequestParam("uploadFile") MultipartFile file, 
    		@RequestParam("path") String path,
    		@RequestParam("owner") String owner,
    		@RequestParam("email") String email,
    		@RequestParam("organisation") String organisation,
    		@RequestParam("usage") String usage) throws UnsupportedEncodingException {
			Model model = ModelshareFactory.eINSTANCE.createModel();
			model.setPath(URLDecoder.decode(path, "UTF-8"));
			model.setOwner(owner);
			model.setMail(email);
			model.setOrganisation(organisation);
			model.setUsage(usage);
		service.saveFile(file, model);
        return "redirect:archive.html?item=" + URLEncoder.encode(path, "UTF-8") + File.separator + file.getOriginalFilename(); 
    }

	@RequestMapping(value = "/createFolder", method = RequestMethod.POST) 
    public String importParse(@RequestParam("path") String path, 
    		@RequestParam("name") String name) throws UnsupportedEncodingException {
		service.createFolder(URLDecoder.decode(path, "UTF-8"), name);
        return "redirect:archive.html?item=" + path + File.separator + name; 
    }	
}