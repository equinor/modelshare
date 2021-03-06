/*******************************************************************************
 * Copyright © 2020 Equinor ASA
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
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
