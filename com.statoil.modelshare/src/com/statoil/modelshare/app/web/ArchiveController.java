package com.statoil.modelshare.app.web;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.statoil.modelshare.Client;
import com.statoil.modelshare.Folder;
import com.statoil.modelshare.Model;
import com.statoil.modelshare.ModelshareFactory;
import com.statoil.modelshare.app.config.RepositoryConfig;
import com.statoil.modelshare.app.service.AssetProxy;
import com.statoil.modelshare.controller.ModelRepository;

@Controller
@RequestMapping("/")
public class ArchiveController extends AbstractController {

	static Logger log = Logger.getLogger(ArchiveController.class.getName());

	@Autowired
	private ModelRepository modelrepository;

	@Autowired
	private RepositoryConfig repositoryConfig;
		
	/**
	 * Use to copy the model to a local directory. 
	 */
	@RequestMapping(value = "/copy", method = RequestMethod.GET)
	public String getFile(ModelMap map, @RequestParam(value = "asset", required = true) String asset,
			HttpServletResponse response, Principal principal) {

		
		Model currentModel = null;
		try {
			currentModel = modelrepository.getMetaInformation(Paths.get(URLDecoder.decode(asset, "UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Client user = modelrepository.getUser(principal.getName());
		Path rootPath = Paths.get(modelrepository.getRoot(user).getPath());
		Path path = Paths.get(asset);
		Path resolvedPath = rootPath.resolve(path);

		map.addAttribute("currentModel", currentModel);
		map.addAttribute("downloadTerms", repositoryConfig.getDownloadTerms());
		map.addAttribute("tasks", currentModel.getTaskInformation());

		map.addAttribute("client", user);
		map.addAttribute("viewOnly",hasViewOnlyAccess(asset, user));
		map.addAttribute("activeMenuItem", asset);
		map.addAttribute("title", "Model archive");
		// common
		AssetProxy n = getMenuItem(user, asset);
		map.addAttribute("node", n);
		map.addAttribute("crumbs", getBreadCrumb(n));
		map.addAttribute("topLevel", getRootNodes(n));
		try {
			String localCopy = modelrepository.localCopy(user, resolvedPath);			
			map.addAttribute("success", localCopy);
		} catch (AccessDeniedException ioe) {
			String msg = "You don't have access to copying a model!";
			log.log(Level.SEVERE, msg, ioe);
			map.addAttribute("error", msg);
			return "folder";		
		} catch (IOException ioe) {
			String msg = "File system error!";
			log.log(Level.SEVERE, msg, ioe);
			map.addAttribute("error", msg);
			return "folder";
		}		
		return "content";
	}
	
	@RequestMapping(value = { "/showModel" }, method = RequestMethod.GET)
	public String doShow(ModelMap map, Principal principal,
			@RequestParam(value = "item", required=true) String asset) {
		try {
			Client user = modelrepository.getUser(principal.getName());

			map.addAttribute("downloadTerms", repositoryConfig.getDownloadTerms());

			map.addAttribute("client", user);
			map.addAttribute("viewOnly",hasViewOnlyAccess(asset, user));
			map.addAttribute("activeMenuItem", asset);
			map.addAttribute("title", "Model archive");
			// common
			AssetProxy n = getMenuItem(user, asset);
			map.addAttribute("node", n);
			map.addAttribute("currentModel", n.getAsset());
			map.addAttribute("crumbs", getBreadCrumb(n));
			map.addAttribute("topLevel", getRootNodes(n));
		} catch (Exception ioe) {
			String msg = "Cannot show item "+asset;
			log.log(Level.SEVERE, msg, ioe);
			map.addAttribute("error", msg);
			return "content";
		}		
		return "content";
	}
	
	@RequestMapping(value = { "/archive" }, method = RequestMethod.GET)
	public String viewArchive(ModelMap map, Principal principal,
			@RequestParam(value = "item", required = false) String asset) {
		try {
			Client user = modelrepository.getUser(principal.getName());
			map.addAttribute("client", user);
			map.addAttribute("activeMenuItem", asset); //XXX: Remove
			map.addAttribute("currentFolder", asset);
			map.addAttribute("title", "Model archive");
			// common
			AssetProxy n = getMenuItem(user, asset);
			map.addAttribute("node", n);
			map.addAttribute("crumbs", getBreadCrumb(n));
			map.addAttribute("topLevel", getRootNodes(n));
		} catch (Exception e) {
			String msg = "Could not load assets";
			log.log(Level.SEVERE, msg, e);
			map.addAttribute("error", msg);
			return "errorpage";
		}
		return "archive";
	}
	
	/**
	 * Shows the "create folder" form.
	 * 
	 * @param map
	 *            attributes for the page
	 * @param asset
	 *            the current folder
	 * @param principal
	 *            the logged in user
	 * @return the template to render
	 */
	@RequestMapping(value = "/folder", method = RequestMethod.GET)
	public String folder(ModelMap map, Principal principal,
			@RequestParam(value = "item", required = false) String asset) {		
		Client user = modelrepository.getUser(principal.getName());
		map.addAttribute("owner", user);
		map.addAttribute("currentFolder", asset);
		// common
		AssetProxy n = getMenuItem(user, asset);
		map.addAttribute("node", n);
		map.addAttribute("crumbs", getBreadCrumb(n));
		map.addAttribute("topLevel", getRootNodes(n));
		return "folder";
	}

	/**
	 * Shows the "upload" form.
	 * 
	 * @param map
	 *            attributes for the page
	 * @param asset
	 *            the current folder
	 * @param principal
	 *            the logged in user
	 * @return the template to render
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String upload(ModelMap map,Principal principal,
			@RequestParam(value = "item", required = false) String asset) {		
		Client user = modelrepository.getUser(principal.getName());
		map.addAttribute("owner", user);
		map.addAttribute("currentFolder", asset);
		// common
		AssetProxy n = getMenuItem(user, asset);
		map.addAttribute("node", n);
		map.addAttribute("crumbs", getBreadCrumb(n));
		map.addAttribute("topLevel", getRootNodes(n));
		return "upload";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadModel(ModelMap map, Principal principal,
			@RequestParam(value = "model", required = true) MultipartFile file,
			@RequestParam(value = "picture") MultipartFile picture,
			@RequestParam(value = "path", required = true) String path, 
			@RequestParam(value = "usage") String usage,
			@RequestParam(value = "name", required = true) String name) {
		
		try ( 	BufferedInputStream ms = new BufferedInputStream(file.getInputStream());
				BufferedInputStream ps = picture.isEmpty() ? null: new BufferedInputStream(picture.getInputStream())) {
			Client user = modelrepository.getUser(principal.getName());
			map.addAttribute("owner", user);
			map.addAttribute("currentFolder", path);
			map.addAttribute("topLevel", getRootItems(user));
			if (file.isEmpty()){
				map.addAttribute("error", "A model file must be specified!");
				return "upload";	
			}
			Model model = ModelshareFactory.eINSTANCE.createModel();
			model.setRelativePath(Paths.get(URLDecoder.decode(path, "UTF-8"), file.getOriginalFilename()).toString());
			model.setName(name.isEmpty() ? file.getOriginalFilename(): name);
			model.setOwner(user.getName());
			model.setMail(user.getEmail());
			// organization cannot be blank
			model.setOrganisation(user.getOrganisation() == null ? "" : user.getOrganisation());
			model.setUsage(usage == null ? "" : usage);
			modelrepository.uploadModel(user, ms, ps, model);
			return "redirect:showModel?item=" + model.getRelativePath().replace('\\', '/');
		} catch (AccessDeniedException ioe) {
			String msg = "You don't have access to upload a new model!";
			log.log(Level.SEVERE, msg, ioe);
			map.addAttribute("error", msg);
			return "upload";		
		} catch (Exception ioe) {
			String msg = "Could not upload model: "+ioe.getMessage();
			log.log(Level.SEVERE, msg, ioe);
			map.addAttribute("error", msg);
			return "upload";		
		}
	}

	@RequestMapping(value = "/createFolder", method = RequestMethod.POST)
	public String uploadFolder(ModelMap map, Principal principal,
			@RequestParam("path") String path, 
			@RequestParam("name") String name, 
			@RequestParam("picture") MultipartFile picture)
			throws UnsupportedEncodingException {

		Client user = modelrepository.getUser(principal.getName());
		map.addAttribute("owner", user);
		map.addAttribute("currentFolder", path);
		map.addAttribute("topLevel", getRootItems(user));
		
		if (name.isEmpty()){
			map.addAttribute("error", "A folder name must be specified");
			return "folder";	
		}
		
		try (BufferedInputStream ps = picture.isEmpty() ? null: new BufferedInputStream(picture.getInputStream())){
			Path parentPath = Paths.get(modelrepository.getRoot(user).getPath(), path);
			Folder parentFolder = ModelshareFactory.eINSTANCE.createFolder();
			parentFolder.setPath(URLDecoder.decode(parentPath.toString(), "UTF-8"));
			modelrepository.createFolder(user, parentFolder, ps, name);
			// upon success
			return "redirect:archive.html?item=" + path + '/' + name;
		} catch (AccessDeniedException ioe) {
			String msg = "You don't have access to creating a new folder!";
			log.log(Level.SEVERE, msg, ioe);
			map.addAttribute("error", msg);
			return "folder";		
		} catch (Exception ioe) {
			String msg = "Could not create folder: "+ioe.getMessage();
			log.log(Level.SEVERE, msg, ioe);
			map.addAttribute("error", msg);
			return "folder";
		}
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

}
