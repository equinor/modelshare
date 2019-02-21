package com.equinor.modelshare.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.equinor.modelshare.Model;
import com.equinor.modelshare.ModelshareFactory;
import com.equinor.modelshare.TaskDetails;
import com.equinor.modelshare.TaskFolder;

public class ParseUtility {

	static Logger log = LoggerFactory.getLogger(ParseUtility.class.getName());

	public static void parseSimaModel(Path path, Model model) throws IOException {
		try {

			log.info("Parsing SIMA model at "+path);
			
			Path tempPath = Files.createTempDirectory("modelshare");
			UnzipUtility unzipper = new UnzipUtility();
			unzipper.unzip(path, tempPath);
			List<File> unzippedFiles = unzipper.getunzippedFiles();

			// start parsing the folder structure if present
			Optional<File> folder = unzippedFiles
					.stream()
					.filter(f -> f.getName().equals("folders.xmi"))
					.findFirst();
			if (folder.isPresent()) {
				unzippedFiles.remove(folder.get());
				parseTaskFolderXMI(folder.get(), model);
			}
						
			// start parsing the folder structure if present
			Optional<File> meta = unzippedFiles
					.stream()
					.filter(f -> f.getName().equals("meta.properties"))
					.findFirst();
			if (meta.isPresent()) {
				unzippedFiles.remove(meta.get());
				Properties p = new Properties();
				p.load(Files.newInputStream(meta.get().toPath(), StandardOpenOption.READ));
				model.setSimaVersion(p.getProperty("SIMAVersion"));
			}

			// collect already parsed task files
			List<TaskDetails> td = new ArrayList<>();
			model.getTaskFolders()
				.forEach(t -> t.getTaskDetails()
						.forEach(d -> td.add(d)));

			// find files not in already in the model
			List<File> unparsed = unzippedFiles
				.stream()
				.filter(f -> {					
					return !td.stream()
							.anyMatch(t -> (t.getIdentifier().equals(f.getName())));
				}).collect(Collectors.toList());

			// parse remaining files
			for (File file : unparsed) {
				model.getTaskDetails().add(parseStaskXMI(file));
			}
			

		} catch (Exception e) {
			throw new IOException("Could not parse SIMA workspace file", e);
		}
	}

	private static TaskDetails parseStaskXMI(File input)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(input);
		doc.getDocumentElement().normalize();

		NodeList nodes = doc.getElementsByTagName("*");
		Node node = nodes.item(0);
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			NamedNodeMap attributes = node.getAttributes();
			Node descrNode = attributes.getNamedItem("description");
			String description = (descrNode != null) ? descrNode.getNodeValue() : "";

			Node task = attributes.getNamedItem("name");
			String taskName = task.getNodeValue();

			TaskDetails taskInfo = ModelshareFactory.eINSTANCE.createTaskDetails();
			taskInfo.setDescription(description);
			taskInfo.setName(taskName);
			taskInfo.setIdentifier(input.getName());
			return taskInfo;
		}
		return null;
	}

	private static void parseTaskFolderXMI(File input, Model model)
			throws ParserConfigurationException, SAXException, IOException {
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(input);
		doc.getDocumentElement().normalize();

		NodeList nodes = doc.getElementsByTagName("sima:TaskFolder");
		for (int a = 0; a < nodes.getLength(); a++) {
			Node node = nodes.item(a);
			NamedNodeMap attributes = node.getAttributes();
			Node dn = attributes.getNamedItem("description");
			String d = (dn != null) ? dn.getNodeValue() : "";

			Node fn = attributes.getNamedItem("name");
			String n = fn.getNodeValue();

			TaskFolder f = ModelshareFactory.eINSTANCE.createTaskFolder();
			f.setDescription(d);
			f.setName(n);
			model.getTaskFolders().add(f);
			
			// load all referenced tasks details
			List<String> references = getReferences(node);
			for (String string : references) {
				File ref = new File(input.getParentFile(),string);
				f.getTaskDetails().add(parseStaskXMI(ref));
			}
		}
	}

	private static List<String> getReferences(Node node) {
		ArrayList<String> refs = new ArrayList<>();
		NodeList references = node.getChildNodes();
		for (int i = 0; i < references.getLength(); i++) {
			Node reference = references.item(i);
			if ("tasks".equals(reference.getNodeName())){
				NamedNodeMap at = reference.getAttributes();
				Node r = at.getNamedItem("href");
				refs.add(r.getNodeValue().substring(0, r.getNodeValue().indexOf('#')));
			}
		}
		return refs;
	}

}
