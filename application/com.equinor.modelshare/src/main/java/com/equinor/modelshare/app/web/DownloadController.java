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
import java.io.InputStream;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.equinor.modelshare.User;
import com.equinor.modelshare.repository.ModelRepository;

/**
 * @author Torkild U. Resheim, Itema AS
 */
@Controller
public class DownloadController extends AbstractController {

	static Logger log = Logger.getLogger(DownloadController.class.getName());
	
	static Logger downloadLog = Logger.getLogger("downloadLogger");
	
	@Autowired
	private ModelRepository modelrepository;

	/**
	 * Download for users with an account
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public String getFile(@RequestParam(value = "asset", required = true) String asset, HttpServletResponse response,
			Principal principal) {
		try {
			User user = getUser(principal);
			Path path = Paths.get(asset);
			String name = path.getFileName().toString();

			try (ServletOutputStream outputStream = response.getOutputStream();
				InputStream is = modelrepository.downloadModel(user, path)) {
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attachment; filename=\""+name+"\"");
				org.apache.commons.io.IOUtils.copy(is, outputStream);
				response.flushBuffer();
				Object[] messageArgs = { asset, principal.getName() };
				downloadLog.info(MessageFormat.format("Model {0} was downloaded by {1}", messageArgs));
			}catch (AccessDeniedException e){
				log.log(Level.SEVERE,
						MessageFormat.format("You do not have access to this file. Filename was '{0}'", asset), e);
				return "download";
			}
		} catch (IOException ex) {
			log.log(Level.SEVERE,
					MessageFormat.format("Error writing file to output stream. Filename was '{0}'", asset), ex);
			return "download";
		}
		return null;
	}
}
