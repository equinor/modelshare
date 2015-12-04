package com.statoil.modelshare.app.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.statoil.modelshare.User;
import com.statoil.modelshare.controller.ModelRepository;

/**
 * @author Torkild U. Resheim, Itema AS
 */
@Controller
public class ImageController {

	@Autowired
	private ModelRepository repository;

	@RequestMapping(value = "/pictures", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<FileSystemResource> picture(@RequestParam(value = "asset", required = true) String asset,
			Principal principal) {
		User user = repository.getUser(principal.getName());
		try {
			File file = repository.getFile(user, Paths.get(asset));
			if (file.exists() && file.isFile()) {
				return ResponseEntity.ok().contentLength(file.length()).contentType(MediaType.IMAGE_JPEG)
						.body(new FileSystemResource(file));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}