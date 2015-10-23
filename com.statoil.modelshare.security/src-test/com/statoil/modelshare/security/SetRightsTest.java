package com.statoil.modelshare.security;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumSet;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.statoil.modelshare.Access;
import com.statoil.modelshare.Client;
import com.statoil.modelshare.Group;
import com.statoil.modelshare.ModelshareFactory;

public class SetRightsTest {

	@ClassRule
	public static TemporaryFolder folder = new TemporaryFolder();
	
	private static Group adminGroup;
	private static Group userGroup;
	EnumSet<Access> access = EnumSet.noneOf(Access.class);
	private static RepositoryAccessControl ra;
	
	@BeforeClass
	public static void setUp(){
		ra = new RepositoryAccessControl(folder.getRoot().toPath());
		adminGroup = ModelshareFactory.eINSTANCE.createGroup();
		adminGroup.setIdentifier("administrators");
		userGroup = ModelshareFactory.eINSTANCE.createGroup();
		userGroup.setIdentifier("users");
	}
	
	@Test
	public void testSetDownloadRights_ByCreatingAccessFile() throws IOException {
		File modelBFolder = folder.newFolder("Model_B");
		File testFile = new File(modelBFolder, "testmodel.obj");
		String ident = "read.access";
		
		Assert.assertEquals(EnumSet.noneOf(Access.class), ra.getAccess(access,testFile.toPath(), ident));

		Client user = ModelshareFactory.eINSTANCE.createClient();
		user.setName("Test Banan");
		user.setIdentifier(ident);
		user.setEmail(ident);
		user.setGroup(userGroup);
		
		ra.setDownloadRights(testFile.toPath(), user);
		EnumSet<Access> newRights = ra.getAccess(access,testFile.toPath(), ident);
		Assert.assertEquals(EnumSet.of(Access.VIEW, Access.READ), newRights);
	}
	
	@Test
	public void testSetDownloadRightsByChangingExistingAccessFile() throws IOException {
		File modelBFolder = folder.newFolder("Model_C");
		File testFile = new File(modelBFolder, "testmodel.obj");
		File accessFile = new File(modelBFolder, ".testmodel.obj.access");
		String ident = "read.access";
		
		FileWriter writer = new FileWriter(accessFile);
		writer.write(ident + " +v -r -w");
		writer.flush();
		writer.close();
		
		EnumSet<Access> rights = ra.getAccess(access,testFile.toPath(), ident);
		Assert.assertEquals(EnumSet.of(Access.VIEW), rights);
		
		Client user = ModelshareFactory.eINSTANCE.createClient();
		user.setName("Test Testesen");
		user.setIdentifier(ident);
		user.setEmail(ident);
		user.setGroup(userGroup);
		
		ra.setDownloadRights(testFile.toPath(), user);
		EnumSet<Access> newRights = ra.getAccess(access,testFile.toPath(), ident);
		Assert.assertEquals(EnumSet.of(Access.VIEW, Access.READ), newRights);
	}
	

}
