package com.statoil.modelshare.app.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.Principal;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaMetadataKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.statoil.modelshare.Client;
import com.statoil.modelshare.controller.ModelRepository;

/**
 * @author Torkild U. Resheim, Itema AS
 */
@Controller
public class ImageController {

	private static TikaConfig tika;

	@Autowired
	private ModelRepository repository;

	@RequestMapping(value = "/pictures", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<InputStreamResource> picture(@RequestParam(value = "asset", required = true) String asset,
			Principal principal) {
		Client user = repository.getUser(principal.getName());
		try {
			File file = repository.getFile(user, Paths.get(asset));
			if (file.exists() && file.isFile()) {
				return ResponseEntity.ok().contentLength(file.length()).contentType(getMimeType(file))
						.body(new InputStreamResource(Files.newInputStream(file.toPath(), StandardOpenOption.READ)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private MediaType getMimeType(File file) {
		try {
			if (tika == null) {
				tika = new TikaConfig();
			}
			Metadata metadata = new Metadata();
			metadata.set(TikaMetadataKeys.RESOURCE_NAME_KEY, file.getName());
			BufferedInputStream bis = new BufferedInputStream(
					Files.newInputStream(file.toPath(), StandardOpenOption.READ));
			return MediaType.parseMediaType(tika.getDetector().detect(bis, metadata).toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (TikaException e) {
			throw new RuntimeException(e);
		}
	}
}