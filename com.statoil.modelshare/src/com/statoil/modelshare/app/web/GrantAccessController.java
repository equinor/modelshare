package com.statoil.modelshare.app.web;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.statoil.modelshare.Client;
import com.statoil.modelshare.controller.ModelRepository;

@Controller
public class GrantAccessController {
	
	static Logger log = Logger.getLogger(GrantAccessController.class.getName());
	
	@Autowired
	private ModelRepository modelrepository;
	
	@RequestMapping(value = "/grantaccess", method = RequestMethod.GET)
	public String prepareAccesPage(ModelMap model, HttpServletRequest request) {
		String queryString = request.getQueryString();
		String filename = queryString.substring(queryString.lastIndexOf("/")+1, queryString.indexOf("&"));
		String user = queryString.substring(queryString.indexOf("user=")+5, queryString.lastIndexOf("&"));
		model.addAttribute("querystring", queryString);
		model.addAttribute("filename", filename);
		model.addAttribute("user", user);
		return "grantaccess";
	}
	
	@RequestMapping(value = "/grantaccess", method = RequestMethod.POST)
	public String setAccess(ModelMap model,
			@RequestParam("filename") String filename,
			@RequestParam("user") String user,
			@RequestParam("querystring") String query) {
		System.out.println("POST query = " + query);
		System.out.println("POST model file name = " + filename);
		System.out.println("POST model user = " + user);
		
		Client client = modelrepository.getUser(user);
		String item = query.substring(query.indexOf("repository/") + 11, query.indexOf("&"));
		System.out.println("Item path = " + item);
		try {
			if (!modelrepository.hasReadAccess(client, Paths.get(item))) {
				
				modelrepository.setDownloadRights(client, Paths.get(item));
				
			} else {
				System.out.println("Has access already...");
			}
		} catch (IOException e) {
			String msg = "Error found when checking or setting access rights";
			log.log(Level.SEVERE, msg, e);
			model.addAttribute("error", msg);
			return "errorpage";
		}
		
		return "archive?item";
	}
 	
}
