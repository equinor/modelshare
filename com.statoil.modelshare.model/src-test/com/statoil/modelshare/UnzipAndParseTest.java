package com.statoil.modelshare;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import com.statoil.modelshare.util.ParseUtility;
import com.statoil.modelshare.util.UnzipUtility;

public class UnzipAndParseTest {

	@Test
	public void testUnzipAndParse() {
		File zipFile = Paths.get("test-resources/itema.stask").toAbsolutePath().toFile();
		String tempDir = System.getProperty("java.io.tmpdir");
		UnzipUtility.unzip(zipFile.getAbsolutePath(), tempDir);
		
		List<File> unzippedFiles = UnzipUtility.getunzippedFiles();
		File file1 = unzippedFiles.get(0);
		assertEquals("Simple_Flexible_Riser.task.xmi", file1.getName());
		
		TaskInformation taskInfo = ParseUtility.parseStaskXMI(file1);
		assertNotNull(taskInfo);
		assertEquals("Simple_Flexible_Riser", taskInfo.getName()); 
		assertFalse(taskInfo.getDescription().isEmpty());
	}
}
