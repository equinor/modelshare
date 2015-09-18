package com.statoil.modelshare.app.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.Properties;

public class ModelInformation {
	
	private Path path;
	private File metaFile;

	public ModelInformation(Path path) throws FileNotFoundException {
		this.path = path;
		this.metaFile = getMetaFile();
	}
	
	public String getOwner() throws IOException {
		return getFromMetaFile("owner");
	}
	
	public String getOrganisation() throws IOException {
		return getFromMetaFile("organisation");
	}
	
	public String getMail() throws IOException {
		return getFromMetaFile("mail");
	}
	
	public String getLastUpdated() throws IOException {
		return getFromMetaFile("lastupdated");
	}
	
	public String getUsage() throws IOException {
		return getFromMetaFile("usage");
	}
	
	private File getMetaFile() throws FileNotFoundException {
		Path modelPath = path.getFileName();
		String metaName = "." + modelPath.getFileName().toString() + ".meta";
		if (!metaName.isEmpty()) {
			Path filePath = path.getParent().resolve(metaName);
			return filePath.toFile();
		}
		throw new FileNotFoundException("Could not find the model and / or meta files");
	}
	
	
	private String getFromMetaFile(String wantedKey) throws IOException {
		try {
			FileInputStream fileInput = new FileInputStream(this.metaFile);
			Properties properties = new Properties();
			properties.loadFromXML(fileInput);
			fileInput.close();
			
			Enumeration<Object> enuKeys = properties.keys();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				if (wantedKey.equals(key)) {
					return properties.getProperty(key);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "<Key not found>";
	}

	
}
