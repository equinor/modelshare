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
	public static TemporaryFolder tempFolder = new TemporaryFolder();
	
	private static Group adminGroup;
	private static Group userGroup;
	EnumSet<Access> access = EnumSet.noneOf(Access.class);
	private static RepositoryAccessControl ra;
	
	@BeforeClass
	public static void setUp(){
		ra = new RepositoryAccessControl(tempFolder.getRoot().toPath());
		adminGroup = ModelshareFactory.eINSTANCE.createGroup();
		adminGroup.setIdentifier("administrators");
		userGroup = ModelshareFactory.eINSTANCE.createGroup();
		userGroup.setIdentifier("users");
	}
	
	@Test
	public void testSetDownloadRights_ByCreatingAccessFile() throws IOException {
		File modelFolder = tempFolder.newFolder("Model_B");
		File model = new File(modelFolder, "testmodel.obj");
		String ident = "read.access";
		
		Assert.assertEquals(EnumSet.noneOf(Access.class), ra.getAccess(access,model.toPath(), ident));

		Client user = ModelshareFactory.eINSTANCE.createClient();
		user.setName("Test Banan");
		user.setIdentifier(ident);
		user.setEmail(ident);
		user.setGroup(userGroup);
		
		ra.setDownloadRights(model.toPath(), user);
		EnumSet<Access> newRights = ra.getAccess(access,model.toPath(), ident);
		Assert.assertEquals(EnumSet.of(Access.VIEW, Access.READ), newRights);
	}
	
	@Test
	public void testSetDownloadRightsByChangingExistingAccessFile() throws IOException {
		File modelFolder = tempFolder.newFolder("Model_C");
		File model = new File(modelFolder, "testmodel.obj");
		File accessFile = new File(modelFolder, ".testmodel.obj.access");
		String ident = "read.access";
		
		FileWriter writer = new FileWriter(accessFile);
		writer.write(ident + " +v -r -w");
		writer.flush();
		writer.close();
		
		EnumSet<Access> rights = ra.getAccess(access,model.toPath(), ident);
		Assert.assertEquals(EnumSet.of(Access.VIEW), rights);
		
		Client user = ModelshareFactory.eINSTANCE.createClient();
		user.setName("Test Testesen");
		user.setIdentifier(ident);
		user.setEmail(ident);
		user.setGroup(userGroup);
		
		ra.setDownloadRights(model.toPath(), user);
		EnumSet<Access> newRights = ra.getAccess(access,model.toPath(), ident);
		Assert.assertEquals(EnumSet.of(Access.VIEW, Access.READ), newRights);
	}
	

}
