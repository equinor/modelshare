package com.statoil.modelshare.util;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.statoil.modelshare.ModelshareFactory;
import com.statoil.modelshare.TaskInformation;

public class ParseUtility {
	
	private static TaskInformation taskInfo;

	public static TaskInformation parseStaskXMI(File input) throws ParserConfigurationException, SAXException, IOException {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(input);
			doc.getDocumentElement().normalize();

			NodeList nodes = doc.getElementsByTagName("*");
			Node node = nodes.item(0);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				NamedNodeMap attributes = node.getAttributes();
				Node descrNode = attributes.getNamedItem("description");
				String description = descrNode.getNodeValue();

				Node task = attributes.getNamedItem("name");
				String taskName = task.getNodeValue();

				taskInfo = ModelshareFactory.eINSTANCE.createTaskInformation();
				taskInfo.setDescription(description);
				taskInfo.setName(taskName);
			}

		return taskInfo;
	}
	

}
