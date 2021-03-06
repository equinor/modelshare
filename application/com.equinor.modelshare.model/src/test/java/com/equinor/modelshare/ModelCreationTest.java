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
		model1.setOrganisation("Equinor ASA");
		model1.setMail("lars.larsen@equinor.com");
		Path staskPath = Paths.get("test-resources/itema.stask").toAbsolutePath();
		model1.setPath(staskPath.toString());
		model1.setUsage("Dette er en stask som skal brukes med møye..");
		model1.setLastUpdated(new Date());
		folder.getAssets().add(model1);
		
		Model model2 = ModelshareFactory.eINSTANCE.createModel();
		model2.setName("koko.geo");
		model2.setOwner("Tor Larsen");
		model2.setOrganisation("Equinor ASA");
		model2.setMail("tor.larsen@equinor.com");
		model2.setPath("A1/koko.geo");
		model2.setUsage("Dette er en geometrimodell som skal brukes med tøy og bøy..");
		model2.setLastUpdated(new Date());
		folder.getAssets().add(model2);
		
		assertEquals(2, folder.getAssets().size());
		assertTrue(model1.isStask());
		assertFalse(model2.isStask());
	}
}
