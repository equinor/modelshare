package com.statoil.modelshare;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.junit.Test;

public class ModelCreationTest {

	@Test
	public void testCreatingModels() {
		Folder folder = ModelshareFactory.eINSTANCE.createFolder();
		folder.setName("A1");
		
		Model model1 = ModelshareFactory.eINSTANCE.createModel();
		model1.setName("itema.stask");
		model1.setOwner("Lars Larsen");
		model1.setOrganisation("Statoil ASA");
		model1.setMail("lars.larsen@statoil.com");
		Path staskPath = Paths.get("test-resources/itema.stask").toAbsolutePath();
		model1.setPath(staskPath.toString());
		model1.setUsage("Dette er en stask som skal brukes med møye..");
		model1.setLastUpdated(new Date());
		folder.getAssets().add(model1);
		
		Model model2 = ModelshareFactory.eINSTANCE.createModel();
		model2.setName("koko.geo");
		model2.setOwner("Tor Larsen");
		model2.setOrganisation("Statoil ASA");
		model2.setMail("tor.larsen@statoil.com");
		model2.setPath("A1/koko.geo");
		model1.setUsage("Dette er en geometrimodell som skal brukes med tøy og bøy..");
		model2.setLastUpdated(new Date());
		folder.getAssets().add(model2);
		
		assertEquals(2, folder.getAssets().size());
		assertTrue(model1.isStask());
		assertFalse(model2.isStask());
		assertEquals(3, model1.getTaskInformation().size());
		TaskInformation taskInformation1 = model1.getTaskInformation().get(0);
		assertEquals("Simple_Flexible_Riser", taskInformation1.getName());
	}
}
