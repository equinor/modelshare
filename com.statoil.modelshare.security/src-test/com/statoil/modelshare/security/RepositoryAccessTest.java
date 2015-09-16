package com.statoil.modelshare.security;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.statoil.modelshare.Access;
import com.statoil.modelshare.Account;
import com.statoil.modelshare.Group;
import com.statoil.modelshare.ModelshareFactory;

public class RepositoryAccessTest {
	
	static List<Account> accounts = new ArrayList<>();
	private static Group adminGroup;
	private static Group userGroup;
	private static Group userGroup2;
	EnumSet<Access> access = EnumSet.noneOf(Access.class);
	
	@BeforeClass
	public static void setUp(){
		RepositoryAccess.getSharedInstance("test-resources/models/");
		adminGroup = ModelshareFactory.eINSTANCE.createGroup();
		adminGroup.setIdentifier("administrators");
		userGroup = ModelshareFactory.eINSTANCE.createGroup();
		userGroup.setIdentifier("users");
		userGroup2 = ModelshareFactory.eINSTANCE.createGroup();
		userGroup2.setIdentifier("users2");
	}
	
	@Test
	public void testGetModelRoot() {
		Path path = RepositoryAccess.getModelRoot();
		File[] listFiles = path.toFile().listFiles();
		Assert.assertEquals(3, listFiles.length);
	}
	
	@Test
	public void testInternalNoAccess() throws IOException{
		Assert.assertEquals(EnumSet.noneOf(Access.class), RepositoryAccess.getAccess(access,RepositoryAccess.getModelRoot(), "no.access"));
	}
	
	@Test
	public void testInternalReadAccess() throws IOException{
		Assert.assertEquals(EnumSet.of(Access.READ), RepositoryAccess.getAccess(access,RepositoryAccess.getModelRoot(), "read.access"));
	}

	@Test
	public void testInternalViewAccess() throws IOException{
		Assert.assertEquals(EnumSet.of(Access.VIEW), RepositoryAccess.getAccess(access,RepositoryAccess.getModelRoot(), "view.access"));
	}

	@Test
	public void testInternalWriteAccess() throws IOException{
		Assert.assertEquals(EnumSet.of(Access.WRITE), RepositoryAccess.getAccess(access,RepositoryAccess.getModelRoot(), "write.access"));
	}
	
//	@Test
//	public void testInternalInheritedNoAccess() throws IOException{
//		Assert.assertEquals(EnumSet.noneOf(Access.class), AccessFileReader.getAccess(access,Paths.get("Model_A/Model_A1/Model_A1.1"), "no.access"));
//	}
//
//	@Test
//	public void testInternalInheritedAdminAccess() throws IOException{
//		Assert.assertEquals(EnumSet.allOf(Access.class), AccessFileReader.getAccess(access,Paths.get("Model_A/Model_A1/Model_A1.1"), "administrator"));
//	}
	
	//------ 
	
	@Test
	public void testAdminAccess() throws IOException{
		Assert.assertEquals(EnumSet.allOf(Access.class), RepositoryAccess.getRights(RepositoryAccess.getModelRoot(), adminGroup));
	}

	@Test
	public void testUserAccess() throws IOException{
		Assert.assertEquals(EnumSet.of(Access.READ, Access.VIEW), RepositoryAccess.getRights(RepositoryAccess.getModelRoot(), userGroup));
	}

	@Test
	public void testSecretAccess() throws IOException{
		Assert.assertEquals(EnumSet.noneOf(Access.class), RepositoryAccess.getRights(Paths.get("Secret"), userGroup));
		Assert.assertEquals(EnumSet.allOf(Access.class), RepositoryAccess.getRights(Paths.get("Secret"), adminGroup));
	}

	@Test
	public void testInheritedSecretAccess() throws IOException{
		Assert.assertEquals(EnumSet.noneOf(Access.class), RepositoryAccess.getRights(Paths.get("Secret/SubSecret"), userGroup));
		Assert.assertEquals(EnumSet.allOf(Access.class), RepositoryAccess.getRights(Paths.get("Secret/SubSecret"), adminGroup));
		Assert.assertEquals(EnumSet.noneOf(Access.class), RepositoryAccess.getRights(Paths.get("Secret/SubSecret/model.stask"), userGroup));
		Assert.assertEquals(EnumSet.allOf(Access.class), RepositoryAccess.getRights(Paths.get("Secret/SubSecret/model.stask"), adminGroup));
	}

	@Test
	public void testSecretAccessOverride() throws IOException{
		Assert.assertEquals(EnumSet.of(Access.READ, Access.VIEW), RepositoryAccess.getRights(Paths.get("Secret/SubSecret/model.stask"), userGroup2));
	}

}
