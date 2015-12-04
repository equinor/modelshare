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
	
	private static final EnumSet<Access> NO_ACCESS = EnumSet.of(Access.NO_VIEW, Access.NO_READ, Access.NO_WRITE);
	static List<Account> accounts = new ArrayList<>();
	private static Group adminGroup;
	private static Group userGroup;
	private static Group userGroup2;
	EnumSet<Access> access = EnumSet.noneOf(Access.class);
	private static final Path root = Paths.get("test-resources/models/").toAbsolutePath();
	private static RepositoryAccessControl ra;
	
	@BeforeClass
	public static void setUp(){
		ra = new RepositoryAccessControl(root);
		adminGroup = ModelshareFactory.eINSTANCE.createGroup();
		adminGroup.setIdentifier("administrators");
		userGroup = ModelshareFactory.eINSTANCE.createGroup();
		userGroup.setIdentifier("users");
		userGroup2 = ModelshareFactory.eINSTANCE.createGroup();
		userGroup2.setIdentifier("users2");
	}
	
	@Test
	public void testGetModelRoot() {
		File[] listFiles = root.toFile().listFiles();
		Assert.assertEquals(3, listFiles.length);
	}
	
	@Test
	public void testInternalNoAccess() throws IOException{
		Assert.assertEquals(NO_ACCESS, ra.getAccess(access,root, "no.access"));
	}
	
	@Test
	public void testInternalReadAccess() throws IOException{
		Assert.assertEquals(EnumSet.of(Access.NO_VIEW, Access.READ, Access.NO_WRITE), ra.getAccess(access,root, "read.access"));
	}

	@Test
	public void testInternalViewAccess() throws IOException{
		Assert.assertEquals(EnumSet.of(Access.VIEW, Access.NO_READ, Access.NO_WRITE), ra.getAccess(access,root, "view.access"));
	}

	@Test
	public void testInternalWriteAccess() throws IOException{
		Assert.assertEquals(EnumSet.of(Access.NO_VIEW, Access.NO_READ, Access.WRITE), ra.getAccess(access,root, "write.access"));
	}
			
	@Test
	public void testAdminAccess() throws IOException{
		Assert.assertEquals(EnumSet.range(Access.VIEW, Access.WRITE), ra.getRights(root, adminGroup));
	}

	@Test
	public void testUserAccess() throws IOException{
		Assert.assertEquals(EnumSet.of(Access.VIEW, Access.READ, Access.NO_WRITE), ra.getRights(root, userGroup));
	}
	
	@Test
	public void testInheritedAccess() throws IOException{
		Assert.assertEquals(EnumSet.range(Access.VIEW,Access.WRITE), ra.getRights(Paths.get("Model_A/Model_A1/Model_A1.1"), adminGroup));
	}

	@Test
	public void testSecretAccess() throws IOException{
		Assert.assertEquals(NO_ACCESS, ra.getRights(Paths.get("Secret"), userGroup));
		Assert.assertEquals(EnumSet.range(Access.VIEW,Access.WRITE), ra.getRights(Paths.get("Secret"), adminGroup));
	}

	@Test
	public void testInheritedSecretAccess() throws IOException{
		Assert.assertEquals(NO_ACCESS, ra.getRights(Paths.get("Secret/SubSecret"), userGroup));
		Assert.assertEquals(EnumSet.range(Access.VIEW,Access.WRITE), ra.getRights(Paths.get("Secret/SubSecret"), adminGroup));
		Assert.assertEquals(EnumSet.of(Access.NO_VIEW, Access.NO_READ, Access.NO_WRITE), ra.getRights(Paths.get("Secret/SubSecret/model.stask"), userGroup));
		Assert.assertEquals(EnumSet.range(Access.VIEW,Access.WRITE), ra.getRights(Paths.get("Secret/SubSecret/model.stask"), adminGroup));
	}

	@Test
	public void testSecretAccessOverride() throws IOException{
		Assert.assertEquals(EnumSet.of(Access.READ, Access.VIEW, Access.NO_WRITE), ra.getRights(Paths.get("Secret/SubSecret/model.stask"), userGroup2));
	}
	
	@Test
	public void testBug_132() throws IOException {
		Path root = Paths.get("test-resources/bug_132/").toAbsolutePath();
		RepositoryAccessControl rac = new RepositoryAccessControl(root);

		// a@user
		Account user = rac.getAccounts().get(6);
		Assert.assertEquals("ghe@itema.no", user.getIdentifier());
		Assert.assertEquals(EnumSet.of(Access.READ, Access.VIEW, Access.WRITE),
				rac.getRights(Paths.get("SIMA Models"), user));

	}
	
	
}
