package com.statoil.modelshare.security;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.statoil.modelshare.Account;
import com.statoil.modelshare.User;
import com.statoil.modelshare.Group;

public class PasswordFileTest {
	private static final Path root = Paths.get("test-resources/users").toAbsolutePath();
	
	@ClassRule
	public static TemporaryFolder folder= new TemporaryFolder();

	private static RepositoryAccessControl ra;
	private static RepositoryAccessControl ra2;
	
	@BeforeClass
	public static void setUp() throws IOException{
		File tempFolder = folder.newFolder().getParentFile();
		Files.copy(root.resolve(".passwd"), tempFolder.toPath().resolve(".passwd"), StandardCopyOption.REPLACE_EXISTING);
		ra = new RepositoryAccessControl(root);
		ra2 = new RepositoryAccessControl(tempFolder.toPath());
	}

	@Test
	public final void testReadPasswordFile(){
		Assert.assertEquals(8, ra.getAccounts().size());
	}
	
	@Test
	public final void testReadGroup(){
		Account account = ra.getAccounts().get(1);
		Assert.assertTrue(account instanceof Group);
		Assert.assertEquals("users", account.getIdentifier());
		Assert.assertEquals("Users", account.getName());
		Assert.assertEquals(null, account.getGroup());
	}

	@Test
	public final void testReadInheritedGroup(){
		Account account = ra.getAccounts().get(2);
		Assert.assertTrue(account instanceof Group);
		Assert.assertEquals("sub", account.getIdentifier());
		Assert.assertEquals("Sub", account.getName());
		Assert.assertEquals("users", account.getGroup().getIdentifier());
	}
	
	@Test
	public final void testBasicUser(){
		Account account = ra.getAccounts().get(3);
		Assert.assertTrue(account instanceof User);
		Assert.assertEquals("a@company-a", account.getIdentifier());
		Assert.assertEquals("User A", account.getName());
		Assert.assertEquals("users", account.getGroup().getIdentifier());		
		Assert.assertEquals("a@company-a", ((User)account).getEmail());
		Assert.assertEquals(null, ((User)account).getOrganisation());
		Assert.assertEquals(null, ((User)account).getLocalUser());
	}

	@Test
	public final void testUserWithOrganization(){
		Account account = ra.getAccounts().get(4);
		Assert.assertTrue(account instanceof User);
		Assert.assertEquals("b@company-b", account.getIdentifier());
		Assert.assertEquals("User B", account.getName());
		Assert.assertEquals("users", account.getGroup().getIdentifier());
		Assert.assertEquals("b@company-b", ((User)account).getEmail());
		Assert.assertEquals("Company B", ((User)account).getOrganisation());
		Assert.assertEquals(null, ((User)account).getLocalUser());
	}

	@Test
	public final void testUserWithLocalUser(){
		Account account = ra.getAccounts().get(5);
		Assert.assertTrue(account instanceof User);
		Assert.assertEquals("c@company-c", account.getIdentifier());
		Assert.assertEquals("User C", account.getName());
		Assert.assertEquals("users", account.getGroup().getIdentifier());
		Assert.assertEquals("c@company-c", ((User)account).getEmail());
		Assert.assertEquals(null, ((User)account).getOrganisation());
		Assert.assertEquals("LocalUserC", ((User)account).getLocalUser());
	}
	
	@Test
	public final void testUserWithLocalUserAndOrganization(){
		Account account = ra.getAccounts().get(6);
		Assert.assertTrue(account instanceof User);
		Assert.assertEquals("d@company-d", account.getIdentifier());
		Assert.assertEquals("User D", account.getName());
		Assert.assertEquals("users", account.getGroup().getIdentifier());
		Assert.assertEquals("d@company-d", ((User)account).getEmail());
		Assert.assertEquals("Company D", ((User)account).getOrganisation());
		Assert.assertEquals("LocalUserD", ((User)account).getLocalUser());
	}
	
	@Test
	public final void testOverwritePasswordFile() throws IOException{
		// add an account by modifying the file directly
		Files.write(ra2.passwordFilePath, "e@company-e::users:User E:Company E:LocalUserE".getBytes(), StandardOpenOption.APPEND);
		Account account = ra2.getAccounts().get(5);
		ra2.setPassword(account.getIdentifier(), "xxxxx");
		// the manually added account should not have disappeared
		Assert.assertEquals(9, ra2.getAccounts().size());
	}
}
