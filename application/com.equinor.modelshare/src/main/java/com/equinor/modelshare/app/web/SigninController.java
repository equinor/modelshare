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

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Torkild U. Resheim, Itema AS
 */
@Controller
@Profile(value = { "!Azure" })
public class SigninController extends AbstractController {

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String login(ModelMap model, @RequestParam(value = "logout", required = false) String logout) {
		return "signin";
	}
	@RequestMapping(value = "/signout", method = RequestMethod.GET)
	public String logout(Model model) {
		return "signin";
	}
}