package com.statoil.modelshare.security;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.statoil.modelshare.Account;
import com.statoil.modelshare.Client;
import com.statoil.modelshare.Group;

public class PasswordFileTest {
	private static final Path root = Paths.get("test-resources/").toAbsolutePath();
	private static RepositoryAccessControl ra;
	
	@BeforeClass
	public static void setUp(){
		ra = new RepositoryAccessControl(root);
	}

	@Test
	public final void testReadPasswordFile(){
		Assert.assertEquals(6, ra.getAccounts().size());
	}
	
	@Test
	public final void testReadGroup(){
		Account account = ra.getAccounts().get(0);
		Assert.assertTrue(account instanceof Group);
		Assert.assertEquals("users", account.getIdentifier());
		Assert.assertEquals("Users", account.getName());
		Assert.assertEquals(null, account.getGroup());
	}

	@Test
	public final void testReadInheritedGroup(){
		Account account = ra.getAccounts().get(1);
		Assert.assertTrue(account instanceof Group);
		Assert.assertEquals("sub", account.getIdentifier());
		Assert.assertEquals("Sub", account.getName());
		Assert.assertEquals("users", account.getGroup().getIdentifier());
	}
	
	@Test
	public final void testBasicUser(){
		Account account = ra.getAccounts().get(2);
		Assert.assertTrue(account instanceof Client);
		Assert.assertEquals("a@company-a", account.getIdentifier());
		Assert.assertEquals("User A", account.getName());
		Assert.assertEquals("users", account.getGroup().getIdentifier());		
		Assert.assertEquals("a@company-a", ((Client)account).getEmail());
		Assert.assertEquals(null, ((Client)account).getOrganisation());
		Assert.assertEquals(null, ((Client)account).getLocalUser());
	}

	@Test
	public final void testUserWithOrganization(){
		Account account = ra.getAccounts().get(3);
		Assert.assertTrue(account instanceof Client);
		Assert.assertEquals("b@company-b", account.getIdentifier());
		Assert.assertEquals("User B", account.getName());
		Assert.assertEquals("users", account.getGroup().getIdentifier());
		Assert.assertEquals("b@company-b", ((Client)account).getEmail());
		Assert.assertEquals("Company B", ((Client)account).getOrganisation());
		Assert.assertEquals(null, ((Client)account).getLocalUser());
	}

	@Test
	public final void testUserWithLocalUser(){
		Account account = ra.getAccounts().get(4);
		Assert.assertTrue(account instanceof Client);
		Assert.assertEquals("c@company-c", account.getIdentifier());
		Assert.assertEquals("User C", account.getName());
		Assert.assertEquals("users", account.getGroup().getIdentifier());
		Assert.assertEquals("c@company-c", ((Client)account).getEmail());
		Assert.assertEquals(null, ((Client)account).getOrganisation());
		Assert.assertEquals("LocalUserC", ((Client)account).getLocalUser());
	}
	
	@Test
	public final void testUserWithLocalUserAndOrganization(){
		Account account = ra.getAccounts().get(5);
		Assert.assertTrue(account instanceof Client);
		Assert.assertEquals("d@company-d", account.getIdentifier());
		Assert.assertEquals("User D", account.getName());
		Assert.assertEquals("users", account.getGroup().getIdentifier());
		Assert.assertEquals("d@company-d", ((Client)account).getEmail());
		Assert.assertEquals("Company D", ((Client)account).getOrganisation());
		Assert.assertEquals("LocalUserD", ((Client)account).getLocalUser());
	}
}
