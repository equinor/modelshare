package com.statoil.modelshare.app.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.statoil.modelshare.Client;
import com.statoil.modelshare.Model;
import com.statoil.modelshare.controller.ModelRepository;

/**
 * @author Torkild U. Resheim, Itema AS
 */
@Controller
public class RequestController {

	static Logger log = Logger.getLogger(RequestController.class.getName());

	@Autowired
	private ModelRepository modelrepository;

	@RequestMapping(value = "/request", method = RequestMethod.GET)
	public String showRequestForm(ModelMap model, 
			@RequestParam(value = "asset", required = true) String asset,
			Principal principal) {
		try {
			Client user = modelrepository.getUser(principal.getName());
			Model downloadModel = getModelFromAssets(asset);
			model.put("from", user.getName());
			model.put("to", downloadModel.getMail());
			model.put("asset", asset);
		} catch (Exception e) {
			return "error";
		}
		return "request";
	}

	public Model getModelFromAssets(String encodedPath) throws UnsupportedEncodingException {
		if (encodedPath != null) {
			Model metaInformation = modelrepository
					.getMetaInformation(Paths.get(URLDecoder.decode(encodedPath, "UTF-8")));
			return metaInformation;
		}
		return null;
	}

	@RequestMapping(value = "/request", method = RequestMethod.POST)
	public String actOnRequestForm(ModelMap modelMap, 
			@RequestParam(value = "asset", required = true) String asset,
			@RequestParam(value = "message", required = true) String message, Principal principal,
			@RequestParam(value = "to", required = true) String mailTo) {

		if (isEmailWellformed(mailTo)) {
			// Act on the form and send e-mail to and from can be obtained from the
			// model as in showRequestForm.
			
		} else {
			String msg = "Missing well formed e-mail address";
			log.log(Level.SEVERE, msg);
			modelMap.addAttribute("error", msg);
			return "errorpage";
		}
		Model model = null;
		try {
			model = getModelFromAssets(asset);
		} catch (Exception e) {
			String msg = "Error getting model information..";
			log.log(Level.SEVERE, msg, e);
			modelMap.addAttribute("error", msg);
			return "errorpage";
		}
		return "redirect:showModel?item=" + model.getPath() + "&leaf=true";
	}

	private boolean isEmailWellformed(String mailTo) {
		if (mailTo.isEmpty() || !mailTo.contains("@")) {
			return false;
		}
		return true;
	}
}
