package com.statoil.modelshare.controller;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.xml.sax.SAXException;

import com.statoil.modelshare.Model;
import com.statoil.modelshare.ModelshareFactory;

public class UploadFileTest {

	@ClassRule
	public static TemporaryFolder folder = new TemporaryFolder();
	
	@Test
	public void testUploadingFile() throws FileNotFoundException, IOException, ParserConfigurationException, SAXException {
		ModelRepository repo = new ModelRepositoryImpl(folder.getRoot().toPath(), null);
		File testDir = new File(folder.getRoot().toString(), "TestUpload");
		
		// Create a model representation of the file
		File file = Paths.get("test-resources/itema.stask").toAbsolutePath().toFile();
		Model model = ModelshareFactory.eINSTANCE.createModel();
		model.setOwner("lars");
		model.setOrganisation("Statoil");
		model.setUsage("Bla bla bla...");
		model.setName(file.getName());
		model.setPath(folder.getRoot().getAbsolutePath() + File.separator + "itema.stask");
		
		// Upload the file
		repo.uploadFile(file, model);
		
		// Make sure it actually exists
		File staskFile = new File(testDir, "itema.stask");
		assertNotNull(staskFile);
	}
}
