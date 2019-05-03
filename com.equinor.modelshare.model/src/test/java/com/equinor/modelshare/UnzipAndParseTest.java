package com.equinor.modelshare;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.equinor.modelshare.util.ParseUtility;

public class UnzipAndParseTest {
	
	@Test
	public void testFolderFormat() throws IOException{
		Model model = ModelshareFactory.eINSTANCE.createModel();
		Path task = Paths.get("src/test/resources/folders.stask").toAbsolutePath();
		ParseUtility.parseSimaModel(task, model);
		assertEquals(5, model.getTaskFolders().size());
		assertEquals(3, model.getTaskFolders().get(0).getTaskDetails().size());
		assertEquals(4, model.getTaskFolders().get(1).getTaskDetails().size());
		assertEquals(5, model.getTaskFolders().get(2).getTaskDetails().size());
		assertEquals(2, model.getTaskFolders().get(3).getTaskDetails().size());
		assertEquals(2, model.getTaskFolders().get(4).getTaskDetails().size());
		assertEquals(6, model.getTaskDetails().size());
	}
}
