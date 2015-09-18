package com.statoil.modelshare.app.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

public class ModelInformationTest {

	final Path root = Paths.get("test-resources/").toAbsolutePath();
	final Path staskPath = Paths.get("test-resources/metatest.stask").toAbsolutePath();
	
	@Test
	public void testGetModelRoot() {
		File[] listFiles = root.toFile().listFiles();
		Assert.assertEquals(2, listFiles.length);
	}

	@Test
	public void testReadingMetaFile() throws IOException {
		ModelInformation m = new ModelInformation(staskPath);
		assertEquals("Lars Larsen", m.getOwner());
		assertEquals("Statoil ASA", m.getOrganisation());
		assertEquals("lars.larsen@statoil.com", m.getMail());
		assertEquals("22.05.2015 12:42", m.getLastUpdated());
		assertEquals("Denne modellen må lastes ned og den må kjøres på følgende måte bla bla bla...", m.getUsage());
	}
}
