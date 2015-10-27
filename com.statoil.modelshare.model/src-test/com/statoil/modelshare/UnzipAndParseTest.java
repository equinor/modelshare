package com.statoil.modelshare;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.statoil.modelshare.util.ParseUtility;
import com.statoil.modelshare.util.UnzipUtility;

public class UnzipAndParseTest {

	@Test
	public void testUnzipAndParse() throws ParserConfigurationException, SAXException, IOException {
		File zipFile = Paths.get("test-resources/itema.stask").toAbsolutePath().toFile();
		Path tempPath = Files.createTempDirectory("modelshare");
		UnzipUtility unzipper = new UnzipUtility();
		unzipper.unzip(zipFile.toPath(), tempPath);
		
		List<File> unzippedFiles = unzipper.getunzippedFiles();
		File file1 = unzippedFiles.get(0);
		assertEquals("Simple_Flexible_Riser.task.xmi", file1.getName());
		
		TaskInformation taskInfo = ParseUtility.parseStaskXMI(file1);
		assertNotNull(taskInfo);
		assertEquals("Simple_Flexible_Riser", taskInfo.getName()); 
		assertFalse(taskInfo.getDescription().isEmpty());
	}
}
