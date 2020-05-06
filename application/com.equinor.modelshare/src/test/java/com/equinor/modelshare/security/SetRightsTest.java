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
package com.equinor.modelshare.security;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumSet;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.equinor.modelshare.Access;
import com.equinor.modelshare.User;
import com.equinor.modelshare.Group;
import com.equinor.modelshare.ModelshareFactory;

public class SetRightsTest {

	@ClassRule
	public static TemporaryFolder tempFolder = new TemporaryFolder();
	
	private static Group adminGroup;
	private static Group userGroup;
	private static RepositoryAccessControl ra;
	
	@BeforeClass
	public static void setUp(){
		ra = new LocalRepositoryAccessControl(tempFolder.getRoot().toPath());
		adminGroup = ModelshareFactory.eINSTANCE.createGroup();
		adminGroup.setIdentifier("administrators");
		userGroup = ModelshareFactory.eINSTANCE.createGroup();
		userGroup.setIdentifier("users");
	}
	
	@Test
	public void testSetDownloadRights_ByCreatingAccessFile() throws IOException {
		File modelFolder = tempFolder.newFolder("Model_B");
		File model = new File(modelFolder, "model_b");
		String ident = "read.access";
		
		Assert.assertEquals(EnumSet.noneOf(Access.class), ra.getAccess(EnumSet.noneOf(Access.class), model.toPath(), ident));

		User user = ModelshareFactory.eINSTANCE.createUser();
		user.setName("Test Banan");
		user.setIdentifier(ident);
		user.setEmail(ident);
		user.setGroup(userGroup);
		
		ra.modifyRights(model.toPath(), user, EnumSet.of(Access.VIEW, Access.READ));
		EnumSet<Access> newRights = ra.getAccess(EnumSet.noneOf(Access.class), model.toPath(), ident);
		Assert.assertEquals(EnumSet.of(Access.VIEW, Access.READ), newRights);
	}
	
	@Test
	public void testSetDownloadRightsByChangingExistingAccessFile() throws IOException {
		File modelFolder = tempFolder.newFolder("Model_C");
		File model = new File(modelFolder, "model_c");
		File accessFile = new File(modelFolder, ".model_c.access");
		
		FileWriter writer = new FileWriter(accessFile);
		writer.write("t1 +v -r -w");
		writer.close();
				
		User u1 = ModelshareFactory.eINSTANCE.createUser();
		u1.setName("Test1");
		u1.setIdentifier("t1");
		u1.setEmail("t1@test.net");
		u1.setGroup(userGroup);
		
		User u2 = ModelshareFactory.eINSTANCE.createUser();
		u2.setName("Test1");
		u2.setIdentifier("t2");
		u2.setEmail("t2@test.net");
		u2.setGroup(userGroup);

		// test initial state
		EnumSet<Access> rights = ra.getAccess(EnumSet.noneOf(Access.class), model.toPath(), "t1");
		Assert.assertEquals(EnumSet.of(Access.VIEW, Access.NO_READ, Access.NO_WRITE), rights);

		// partial modification
		ra.modifyRights(model.toPath(), u1, EnumSet.of(Access.NO_VIEW));
		EnumSet<Access> newRights1 = ra.getAccess(EnumSet.noneOf(Access.class), model.toPath(), "t1");
		Assert.assertEquals(EnumSet.of(Access.NO_VIEW, Access.NO_READ, Access.NO_WRITE), newRights1);

		// complete modification
		ra.modifyRights(model.toPath(), u1, EnumSet.of(Access.VIEW, Access.READ, Access.WRITE));
		EnumSet<Access> newRights2 = ra.getAccess(EnumSet.noneOf(Access.class), model.toPath(), "t1");
		Assert.assertEquals(EnumSet.of(Access.VIEW, Access.WRITE, Access.READ), newRights2);

		// add account to .access
		ra.modifyRights(model.toPath(), u2, EnumSet.of(Access.VIEW));
		EnumSet<Access> newRights3 = ra.getAccess(EnumSet.noneOf(Access.class), model.toPath(), "t2");
		Assert.assertEquals(EnumSet.of(Access.VIEW), newRights3);
		
		// modify newly added account
		ra.modifyRights(model.toPath(), u2, EnumSet.of(Access.READ));
		EnumSet<Access> newRights4 = ra.getAccess(EnumSet.noneOf(Access.class), model.toPath(), "t2");
		Assert.assertEquals(EnumSet.of(Access.VIEW, Access.READ), newRights4);
	}
	

}
