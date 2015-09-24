package com.statoil.modelshare.app.web;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.statoil.modelshare.Client;
import com.statoil.modelshare.Folder;
import com.statoil.modelshare.Model;
import com.statoil.modelshare.ModelshareFactory;
import com.statoil.modelshare.app.service.ArchiveService;
import com.statoil.modelshare.app.service.MenuItem;
import com.statoil.modelshare.controller.ModelRepository;

@Controller
@RequestMapping("/")
public class ArchiveController {
	
	private ArchiveService service = new ArchiveService();
	static Logger log = Logger.getLogger(ArchiveController.class.getName());

	@Autowired
	private ModelRepository modelrepository;
	
	@RequestMapping(value={"/archive", "/showModel"}, method = RequestMethod.GET)
		public String doShow (ModelMap model, @RequestParam(value = "item", required = false) String item, 
				@RequestParam(required = false) boolean leaf,
				@RequestParam(required = false) boolean showFiles,
				@RequestParam(required = false) boolean showNewFolder,
				@RequestParam(required = false) boolean showUploadFile,
				Principal principal) {
		try {
			Model currentModel = service.getModelFromAssets(item);
			Client client = modelrepository.getUser(principal.getName());
			if(leaf){
				model.addAttribute("currentModel", currentModel);
			}else{
				model.addAttribute("currentFolder", item);
				model.addAttribute("showNewFolder", showNewFolder);
				model.addAttribute("showUploadFile", showUploadFile);
				
				Folder models = currentModel.getFolder();
				model.addAttribute("models", models);
			}
			model.addAttribute("attributes", currentModel.getTaskInformation());
			model.addAttribute("client", client);
			model.addAttribute("activeMenuItem", item);
			model.addAttribute("title", "Model archive");
			MenuItem menuItem = service.getMenuItemsFromAssets(principal.getName());
			model.addAttribute("node", menuItem);
		} catch (UnsupportedEncodingException ue) {
			String msg = "Error found when encoding file URL";
			log.log(Level.SEVERE, msg, ue);
			model.addAttribute("error", msg);
			return "errorpage";
		}
		return "archive";
	}
	
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST) 
    public String importParse(ModelMap modelMap, @RequestParam("uploadFile") MultipartFile file, 
    		@RequestParam("path") String path,
    		@RequestParam("usage") String usage, 
    		Principal principal) {
		try {
			Client user = modelrepository.getUser(principal.getName());
			Model model = ModelshareFactory.eINSTANCE.createModel();
			model.setPath(URLDecoder.decode(path, "UTF-8"));
			model.setName(file.getOriginalFilename());
			model.setOwner(user.getName());
			model.setMail(user.getEmail());
			model.setOrganisation(user.getOrganisation());
			model.setUsage(usage);
		service.saveFile(file, model);
		} catch (SAXException se) {
			String msg = "Parsing of the content of the file " + file.getName() + " failed";
			log.log(Level.SEVERE, msg, se);
			modelMap.addAttribute("error", msg);
			return "errorpage";
		} catch (ParserConfigurationException pe) {
			String msg = "Errors found in file " + file.getName() + " when parsing content";
			log.log(Level.SEVERE, msg, pe);
			modelMap.addAttribute("error", msg);
			return "errorpage";
		} catch (IOException ioe) {
			String msg = "Errors saving file " + file.getName();
			log.log(Level.SEVERE, msg, ioe);
			modelMap.addAttribute("error", msg);
			return "errorpage";
		}
		return "redirect:showModel?item=" + path + File.separator + file.getOriginalFilename() + "&leaf=true";
    }

	@RequestMapping(value = "/createFolder", method = RequestMethod.POST) 
    public String importParse(@RequestParam("path") String path, 
    		@RequestParam("name") String name) throws UnsupportedEncodingException {
		service.createFolder(URLDecoder.decode(path, "UTF-8"), name);
        return "redirect:archive.html?item=" + path + File.separator + name; 
    }

}