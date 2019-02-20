package com.statoil.modelshare.app.web;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.mylyn.wikitext.parser.MarkupParser;
import org.eclipse.mylyn.wikitext.parser.builder.HtmlDocumentBuilder;
import org.eclipse.mylyn.wikitext.markdown.MarkdownLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import com.statoil.modelshare.app.RepositoryConfiguration;

@Controller
public class HomeController extends AbstractController {

	@Autowired
	private RepositoryConfiguration repositoryConfig;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printWelcome(ModelMap map, Principal principal) {
		return "redirect:./pages/index.md";
	}

	@RequestMapping(value = {"/pages/*.md", "/pages/help/*.md"}, method = RequestMethod.GET)
	public String markdown(HttpServletRequest request, ModelMap map, Principal principal) {

		addCommonItems(map, principal);

		String resource = ((String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE)).substring(1);
		Path root = repositoryConfig.getRootFolder();
		Path path = root.resolve(resource);
		
		map.addAttribute("text", parse(path));
		
		return "page";
	}

	private String parse(Path markdownFilePath) {
		StringWriter sw = new StringWriter();
		MarkupParser parser = new MarkupParser();
		parser.setMarkupLanguage(new MarkdownLanguage());
		HtmlDocumentBuilder builder = new HtmlDocumentBuilder(sw);
		builder.setEmitAsDocument(false);
		parser.setBuilder(builder);
		try {
			parser.parse(new StringReader(new String(Files.readAllBytes(markdownFilePath))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sw.toString();
	}

	@RequestMapping(value = "/pages/**", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<FileSystemResource> picture(HttpServletRequest request,
			Principal principal) {

		String resource = ((String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE)).substring(1);
		Path root = repositoryConfig.getRootFolder();
		Path path = root.resolve(resource);
		
		File file = path.toAbsolutePath().toFile();

		if (file.exists() && file.isFile()) {
			return ResponseEntity.ok().contentLength(file.length()).contentType(MediaType.IMAGE_JPEG)
					.body(new FileSystemResource(file));
		}
		return null;
	}
}
