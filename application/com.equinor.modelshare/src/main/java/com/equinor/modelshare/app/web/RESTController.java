/*******************************************************************************
 * Copyright © 2020 Equinor ASA
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package com.equinor.modelshare.app.web;

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

import com.equinor.modelshare.Asset;
import com.equinor.modelshare.Group;
import com.equinor.modelshare.Model;
import com.equinor.modelshare.User;
import com.equinor.modelshare.app.service.AssetProxy;
import com.equinor.modelshare.repository.ModelRepository;

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
			@RequestParam(value = "pk", required = true) String pk,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "value", required = true) String value) {
		try {
			User user = getUser(principal);
			AssetProxy n = getAssetAtPath(user, pk);
			Asset asset = n.getAsset();
			switch (name) {
			case "assetOwner":
				if (asset instanceof Model){
					User owner = modelrepository.getUser(value);
					((Model) asset).setOwner(owner.getName());
					((Model) asset).setMail(owner.getIdentifier());
					modelrepository.updateAsset(asset);
				}
				break;
			case "assetName":
				asset.setName(value);
				modelrepository.updateAsset(asset);
				break;
			case "assetDescription":
				asset.setDescription(value);
				modelrepository.updateAsset(asset);
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
		List<User> users = modelrepository.getAuthorizedUsers();
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
