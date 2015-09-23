package com.statoil.modelshare.app.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;

import javax.xml.parsers.ParserConfigurationException;

import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.statoil.modelshare.Client;
import com.statoil.modelshare.Model;
import com.statoil.modelshare.ModelshareFactory;
import com.statoil.modelshare.app.service.ArchiveService;
import com.statoil.modelshare.app.service.MenuItem;
import com.statoil.modelshare.controller.ModelRepository;

@Controller
@RequestMapping("/")
public class ArchiveController {
	private ArchiveService service = new ArchiveService();

	@Autowired
	private ModelRepository modelrepository;
	
	@RequestMapping(value={"/archive", "/showModel"}, method = RequestMethod.GET)
		public String doShow (ModelMap model, @RequestParam(value = "item", required = false) String item, 
				@RequestParam(required = false) boolean leaf,
				@RequestParam(required = false) boolean showNewFolder,
				@RequestParam(required = false) boolean showUploadFile,
				Principal principal) 
				throws FileNotFoundException, UnsupportedEncodingException {
			Model currentModel = service.getModelFromAssets(item);
			Client client = modelrepository.getUser(principal.getName());
			if(leaf){
				model.addAttribute("currentModel", currentModel);
			}else{
				model.addAttribute("currentFolder", item);
				model.addAttribute("showNewFolder", showNewFolder);
				model.addAttribute("showUploadFile", showUploadFile);
			}
			model.addAttribute("attributes", currentModel.getTaskInformation());
			model.addAttribute("client", client);
			model.addAttribute("activeMenuItem", item);
			model.addAttribute("title", "Model archive");
			MenuItem menuItem = service.getMenuItemsFromAssets(principal.getName());
			model.addAttribute("node", menuItem);
		return "archive";
	}
	
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST) 
    public String importParse(@RequestParam("uploadFile") MultipartFile file, 
    		@RequestParam("path") String path,
    		@RequestParam("usage") String usage, 
    		Principal principal) throws IllegalStateException, IOException, ParserConfigurationException, SAXException {
			Client user = modelrepository.getUser(principal.getName());
			Model model = ModelshareFactory.eINSTANCE.createModel();
			model.setPath(URLDecoder.decode(path, "UTF-8"));
			model.setName(file.getOriginalFilename());
			model.setOwner(user.getName());
			model.setMail(user.getEmail());
			model.setOrganisation(user.getOrganisation());
			model.setUsage(usage);
		service.saveFile(file, model);
		return "redirect:showModel?item=" + path + File.separator + file.getOriginalFilename() + "&leaf=true";
    }

	@RequestMapping(value = "/createFolder", method = RequestMethod.POST) 
    public String importParse(@RequestParam("path") String path, 
    		@RequestParam("name") String name) throws UnsupportedEncodingException {
		service.createFolder(URLDecoder.decode(path, "UTF-8"), name);
        return "redirect:archive.html?item=" + path + File.separator + name; 
    }

}