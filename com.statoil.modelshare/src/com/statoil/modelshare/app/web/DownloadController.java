package com.statoil.modelshare.app.web;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.statoil.modelshare.Client;
import com.statoil.modelshare.controller.ModelRepository;

/**
 * @author Torkild U. Resheim, Itema AS
 */
@Controller
public class DownloadController {

	static Logger log = Logger.getLogger(DownloadController.class.getName());

	@Autowired
	private ModelRepository modelrepository;

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public String getFile(@RequestParam(value = "asset", required = true) String asset, HttpServletResponse response,
			Principal principal) {
		try {
			Client user = modelrepository.getUser(principal.getName());
			String name = asset.substring(asset.lastIndexOf('/')+1);
			Path path = Paths.get(asset);
			try {
				InputStream is = modelrepository.getFileStream(user, path);
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attachment; filename=\""+name+"\"");
				org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}catch (AccessDeniedException e){
				return "download";
			}
		} catch (IOException ex) {
			log.log(Level.SEVERE,
					MessageFormat.format("Error writing file to output stream. Filename was '{0}'", asset), ex);
			throw new RuntimeException("IOError writing file to output stream");
		}
		return "download";
	}
}
