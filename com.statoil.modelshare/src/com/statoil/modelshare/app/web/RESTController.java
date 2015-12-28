package com.statoil.modelshare.app.web;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.statoil.modelshare.Group;
import com.statoil.modelshare.Model;
import com.statoil.modelshare.User;
import com.statoil.modelshare.app.service.AssetProxy;
import com.statoil.modelshare.controller.ModelRepository;

@RestController
public class RESTController extends AbstractController {

	static Logger log = Logger.getLogger(RESTController.class.getName());

	@Autowired
	private ModelRepository modelrepository;

	/**
	 * Simple key-value type for mapping into select widgets.
	 */
	class KV {

		String name;
		String value;

		public KV(String id, String name) {
			super();
			this.value = id;
			this.name = name;
		}

		public String getText() {
			return name;
		}

		public String getValue() {
			return value;
		}

	}
	/**
	 * REST API for modifying asset values.
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, produces = "application/json")
	public String edit(ModelMap map, Principal principal,
			@RequestParam(value = "pk", required = true) String asset,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "value", required = true) String value) {
		try {
			User user = modelrepository.getUser(principal.getName());
			AssetProxy n = getAssetAtPath(user, asset);
			Model model = n.getAsset();
			switch (name) {
			case "assetOwner":
				User owner = modelrepository.getUser(value);
				model.setOwner(owner.getName());
				model.setMail(owner.getIdentifier());
				modelrepository.updateModel(model);
				break;
			case "assetName":
				model.setName(value);
				modelrepository.updateModel(model);
				break;
			case "assetDescription":
				model.setDescription(value);
				modelrepository.updateModel(model);
				break;
			default:
				break;
			}
		} catch (AccessDeniedException ioe) {
			String msg = "You don't have access to modify this model!";
			log.log(Level.SEVERE, msg, ioe);
			map.addAttribute("error", msg);
		} catch (IOException e) {
			String msg = "Could not modify : "+e.getMessage();
			log.log(Level.SEVERE, msg, e);
			map.addAttribute("error", msg);
		}
		return "{}";
	}
	
	/**
	 * REST-API for returning the list of users as a JSON array. Used by the
	 * x-editable widget to populate choices in a select/drop-down.
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
	public List<?> getUsers(ModelMap map, Principal principal) {
		List<KV> values = new ArrayList<>();
		List<User> users = modelrepository.getUsers();
		for (User user : users) {
			KV kv = new KV(user.getIdentifier(), user.getName());
			values.add(kv);
		}
		return values;
	}
	
	/**
	 * REST-API for returning the list of groups as a JSON array. Used by the
	 * x-editable widget to populate choices in a select/drop-down.
	 */
	@RequestMapping(value = "/groups", method = RequestMethod.GET, produces = "application/json")
	public List<?> getGroups(ModelMap map, Principal principal) {
		List<KV> values = new ArrayList<>();
		List<Group> groups = modelrepository.getGroups();
		for (Group user : groups) {
			KV kv = new KV(user.getIdentifier(), user.getName());
			values.add(kv);
		}
		return values;
	}
}
