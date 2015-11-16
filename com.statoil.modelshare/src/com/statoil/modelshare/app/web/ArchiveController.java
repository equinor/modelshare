package com.statoil.modelshare.app.web;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
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
import com.statoil.modelshare.app.config.RepositoryConfig;
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

	@Autowired
	private RepositoryConfig repositoryConfig;
	
	/**
	 * Use to copy the model to a local directory. 
	 */
	@RequestMapping(value = "/copy", method = RequestMethod.GET)
	public String getFile(ModelMap model, @RequestParam(value = "asset", required = true) String asset,
			HttpServletResponse response, Principal principal) {

		Model currentModel = service.getModelFromAssets(asset);
		Client user = modelrepository.getUser(principal.getName());
		Path rootPath = Paths.get(modelrepository.getRoot(user).getPath());
		Path path = Paths.get(asset);
		Path resolvedPath = rootPath.resolve(path);

		model.addAttribute("currentModel", currentModel);
		model.addAttribute("downloadTerms", repositoryConfig.getDownloadTerms());
		model.addAttribute("tasks", currentModel.getTaskInformation());

		model.addAttribute("client", user);
		model.addAttribute("viewOnly",hasViewOnlyAccess(asset, user));
		model.addAttribute("activeMenuItem", asset);
		model.addAttribute("title", "Model archive");
		MenuItem menuItem = service.getMenuItemsFromAssets(principal.getName());
		model.addAttribute("node", menuItem);
		model.addAttribute("topLevel", service.getTopLevel(principal));
		model.addAttribute("crumbs", getBreadCrumb(asset, true));
		
		try {
			String localCopy = modelrepository.localCopy(user, resolvedPath);			
			model.addAttribute("success", localCopy);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Could not copy file", e);
			model.addAttribute("error", e.getMessage());
		}
		
		return "content";
	}
	
	@RequestMapping(value = { "/showModel" }, method = RequestMethod.GET)
	public String doShow(ModelMap model, @RequestParam(value = "item", required = false) String asset,
			@RequestParam(required = false) boolean leaf, @RequestParam(required = false) boolean showFiles,
			@RequestParam(required = false) boolean showNewFolder,
			@RequestParam(required = false) boolean showUploadFile, Principal principal) {
		try {
			Model currentModel = service.getModelFromAssets(asset);
			Client user = modelrepository.getUser(principal.getName());

			if (leaf) {
				model.addAttribute("currentModel", currentModel);
				model.addAttribute("downloadTerms", repositoryConfig.getDownloadTerms());
				model.addAttribute("tasks", currentModel.getTaskInformation());
			} else {
				model.addAttribute("currentFolder", asset);
				model.addAttribute("showNewFolder", showNewFolder);
				model.addAttribute("showUploadFile", showUploadFile);

				Folder models = currentModel.getFolder();
				model.addAttribute("models", models);
			}

			model.addAttribute("client", user);
			model.addAttribute("viewOnly",hasViewOnlyAccess(asset, user));
			model.addAttribute("activeMenuItem", asset);
			model.addAttribute("title", "Model archive");
			MenuItem menuItem = service.getMenuItemsFromAssets(principal.getName());
			model.addAttribute("node", menuItem);
			model.addAttribute("topLevel", service.getTopLevel(principal));
			model.addAttribute("crumbs", getBreadCrumb(asset, leaf));
		} catch (Exception e) {
			String msg = "Error found when checking access rights";
			log.log(Level.SEVERE, msg, e);
			model.addAttribute("error", msg);
			return "errorpage";
		}
		return "content";
	}
	
	@RequestMapping(value = { "/archive" }, method = RequestMethod.GET)
	public String viewArchive(ModelMap model, @RequestParam(value = "item", required = false) String item,
			Principal principal) {
		try {
			Client client = modelrepository.getUser(principal.getName());
			model.addAttribute("client", client);
			model.addAttribute("activeMenuItem", item);
			model.addAttribute("title", "Model archive");
			MenuItem menuItem = service.getMenuItemsFromAssets(principal.getName());
			model.addAttribute("node", findActiveNode(item, menuItem));
			model.addAttribute("topLevel", service.getTopLevel(principal));
			model.addAttribute("crumbs", getBreadCrumb(item, false));
		} catch (Exception e) {
			String msg = "Could not load assets";
			log.log(Level.SEVERE, msg, e);
			model.addAttribute("error", msg);
			return "errorpage";
		}
		return "archive";
	}

	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public String importParse(ModelMap modelMap, @RequestParam("uploadFile") MultipartFile file,
			@RequestParam("path") String path, @RequestParam("usage") String usage, Principal principal) {
		try {
			Client user = modelrepository.getUser(principal.getName());
			Path rootPath = Paths.get(modelrepository.getRoot(user).getPath());
			Path uploadPath = Paths.get(path);
			Path resolvedPath = rootPath.resolve(uploadPath);
			Model model = ModelshareFactory.eINSTANCE.createModel();
			model.setPath(URLDecoder.decode(resolvedPath.toString(), "UTF-8"));
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
	public String importParse(@RequestParam("path") String path, @RequestParam("name") String name, Principal principal)
			throws UnsupportedEncodingException {

		Client user = modelrepository.getUser(principal.getName());
		Path rootPath = Paths.get(modelrepository.getRoot(user).getPath());
		Path resolvedPath = rootPath.resolve(path);
		service.createFolder(URLDecoder.decode(resolvedPath.toString(), "UTF-8"), name);

		return "redirect:archive.html?item=" + path + File.separator + name;
	}

	private boolean hasViewOnlyAccess(String item, Client client) {
		boolean hasReadAccess;
		try {
			hasReadAccess = modelrepository.hasDownloadAccess(client, Paths.get(item));
			boolean hasDisplayAccess = modelrepository.hasViewAccess(client, Paths.get(item));
			boolean viewOnly = (!hasReadAccess) && (hasDisplayAccess);
			return viewOnly;
		} catch (IOException e) {
			log.log(Level.SEVERE, "Could not determine access rights", e);
			e.printStackTrace();
			return true;
		}
	}

	private List<MenuItem> getBreadCrumb(String item, boolean leaf) {
		ArrayList<MenuItem> crumbs = new ArrayList<MenuItem>();
		String[] parts = item.split("/");
		String relativePath = "";

		for (int i = 0; i < parts.length; i++) {
			relativePath += (relativePath == "") ? parts[i] : "/" + parts[i];
			Boolean leafNode = (parts.length == i + 1) ? leaf : false;
			crumbs.add(new MenuItem(parts[i], null, "", relativePath, leafNode));
		}
		return crumbs;
	}

	private MenuItem findActiveNode(String activeMenuItem, MenuItem menuItem) {
		MenuItem node = null;
		for (MenuItem mItem : menuItem.getChildren()) {
			if (Objects.equals(mItem.getRelativePath(), activeMenuItem)) {
				node = mItem;
				break;
			}

		}
		return node;
	}

}
