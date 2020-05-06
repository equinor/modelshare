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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.equinor.modelshare.Access;
import com.equinor.modelshare.Account;
import com.equinor.modelshare.Group;
import com.equinor.modelshare.ModelshareFactory;
import com.equinor.modelshare.User;

public abstract class RepositoryAccessControl {

	static Logger log = LoggerFactory.getLogger(RepositoryAccessControl.class.getName());

	public static final Group SUPERVISOR_GROUP = ModelshareFactory.eINSTANCE.createGroup();
	static {
		// Just making sure - we must have an absolute path.
		SUPERVISOR_GROUP.setIdentifier("supervisor");
		SUPERVISOR_GROUP.setName("Supervisors");		
	}

	protected Path repositoryRootPath;
	
	/** Character set to use for all file operations */
	protected final Charset UTF8 = Charset.forName("UTF-8");
	
	public RepositoryAccessControl(Path path) {
		if (!path.isAbsolute()) {
			path = Paths.get(SystemUtils.USER_DIR, path.toString()).normalize();
		}
		repositoryRootPath = path;
	}

	/**
	 * Creates a new user account and adds it to the database. The database will
	 * be updated immediately. If the password has not been specified (set to
	 * <code>null</code>), a reset token will be created. The new user must then
	 * use the appropriate UI to reset his or her password.
	 * 
	 * @param identifier
	 *            the user identifier
	 * @param name
	 *            the user's full name
	 * @param email
	 *            the users's e-mail address
	 * @param organisation
	 *            the users's organisation
	 * @param password
	 *            the user's password hash
	 * @return the newly created user account
	 */
	public abstract User createUser(String identifier, String name, String identifier2, String organisation, String group,
			String password);

	/**
	 * Returns a list of users and groups that may log into the system. The list
	 * is guaranteed to be up to date.
	 * 
	 * @return a list of accounts
	 * @throws IOException
	 */
	public abstract List<Account> getAuthorizedAccounts();
	
	public abstract void writeAccounts();

	public abstract void deleteUser(String identifier);
	
	/**
	 * Sets the password of the user with the specified identifier.
	 * 
	 * @param id
	 *            the user identifier
	 * @param hash
	 *            the password hash
	 */
	public abstract void setPassword(String id, String hash);


	EnumSet<Access> getAccess(EnumSet<Access> access, Path path, String ident) throws IOException {
		if (ident.equals(SUPERVISOR_GROUP.getIdentifier())){
			// no need to check this further
			return EnumSet.of(Access.READ, Access.VIEW, Access.WRITE);
		}
		// determine the name of the .access file
		String name = null;
		if (path.toAbsolutePath().toFile().isDirectory()) {
			name = path.getFileName() + File.separator + ".access";
		} else {
			name = "." + path.getFileName().toString() + ".access";
		}
		Path filePath = repositoryRootPath.resolve(path.getParent().resolve(name));
		File accessFile = filePath.toFile();
		if (!accessFile.exists()) {
			return access;
		}
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new BOMInputStream(
								new FileInputStream(accessFile)), UTF8))) {
			String in = null;
			while ((in = br.readLine()) != null) {
				if (!in.startsWith("#") && in.length() > 0) {
					String[] split = in.trim().split("\\s+");
					if (ident.equals(split[0])) {
						parseAccessString(access, split);
					}
				}
			}
		}
		return access;
	}


	/**
	 * For all accounts listed for the specific asset a set is returned
	 * containing explicitly declared rights. This method is different from
	 * {@link #getRights(Path,Account)} in that it only includes all users with
	 * explicit access and also the complex set of access control flags. For
	 * example:
	 * 
	 * <pre>
	 * anon@ymous +r ?v -w
	 * </pre>
	 * 
	 * Which indicates that the user is granted read access, inherits view
	 * access and is refused write access.
	 * 
	 * @param path
	 *            path to the asset.
	 * @return a map of account and access rights
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @see #getRights(Path, Account)
	 */
	public synchronized Map<Account, EnumSet<Access>> getRights(Path path) throws FileNotFoundException, IOException {
		// determine the name to the .access file
		Path assetPath = repositoryRootPath.resolve(path);
		String name = null;
		if (assetPath.toFile().isDirectory()) {
			name = assetPath.getFileName() + File.separator + ".access";
		} else {
			name = "." + assetPath.getFileName().toString() + ".access";
		}
		Path accessFilePath = repositoryRootPath.resolve(assetPath.getParent().resolve(name));
		File file = accessFilePath.toFile();
		if (!file.exists()) {
			return new HashMap<>();
		}
	
		// collect rights for all accounts listed
		Map<Account, EnumSet<Access>> access = new HashMap<>();
	
		// read the list of accounts in the file
		List<String> identifiers = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new BOMInputStream(new FileInputStream(file)), UTF8))) {
			String in = null;
			while ((in = br.readLine()) != null) {
				if (!in.startsWith("#") && in.length() > 0) {
					String[] split = in.trim().split("\\s+");
					identifiers.add(split[0]);
					Optional<Account> account = getAuthorizedAccounts()
						.stream()
						.filter(a -> a.getIdentifier().equals(split[0]))
						.findFirst();
					if (account.isPresent()) {
						EnumSet<Access> rights = EnumSet.of(Access.INHERIT_VIEW, Access.INHERIT_READ, Access.INHERIT_WRITE);
						parseAccessString(rights, split);
						access.put(account.get(), rights);
					}
				}	
			}
		}
		return access;
	}

	/**
	 * This method will for each account derived from the initial, go into each
	 * path and accumulate access rights. This means that each sub-folder can
	 * modify the set of rights. Also that the immediate group or user account
	 * may override rights of the parent group.
	 * <p>
	 * If no access file exists for the path, the parent access rights will be
	 * used. If there are no rights applicable for the account, no access will
	 * be granted.
	 * </p>
	 * 
	 * @param path
	 *            the path to the file or folder, relative to the model storage
	 *            root.
	 * @param ident
	 *            the user identifier
	 * @param account
	 *            the account to get access rights for
	 * @return a set of access rights
	 * @throws IOException
	 */
	public synchronized EnumSet<Access> getRights(final Path path, Account account) throws IOException {		
		EnumSet<Access> access = EnumSet.noneOf(Access.class);
		List<Account> roles = account.getAllRoles();
		for (Account a : roles) {
			Path r = repositoryRootPath;
			Path p = repositoryRootPath.relativize(r.resolve(path));
			int nameCount = p.getNameCount();
			for (int i = 0; i <= nameCount; i++) {
				access = getAccess(access, r, a.getIdentifier());
				if (i < nameCount) {
					r = r.resolve(p.getName(i));
				}
			}
		}
		log.debug("Tested rights for " + account + " at " + path + " (" + access + ")");
		return access;
	}

	private void modify(EnumSet<Access> modification, EnumSet<Access> rights) {
		for (Access access : modification) {
			switch (access) {
			case NO_READ:
				rights.remove(Access.READ);
				rights.add(Access.NO_READ);
				break;
			case NO_WRITE:
				rights.remove(Access.WRITE);
				rights.add(Access.NO_WRITE);
				break;
			case NO_VIEW:
				rights.remove(Access.VIEW);
				rights.add(Access.NO_VIEW);
				break;
			case READ:
				rights.remove(Access.NO_READ);
				rights.add(Access.READ);
				break;
			case WRITE:
				rights.remove(Access.NO_WRITE);
				rights.add(Access.WRITE);
				break;
			case VIEW:
				rights.remove(Access.NO_VIEW);
				rights.add(Access.VIEW);
				break;
			case INHERIT_READ:
				rights.remove(Access.READ);
				rights.remove(Access.NO_READ);
				break;
			case INHERIT_WRITE:
				rights.remove(Access.WRITE);
				rights.remove(Access.NO_WRITE);
				break;
			case INHERIT_VIEW:
				rights.remove(Access.VIEW);
				rights.remove(Access.NO_VIEW);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Modifies the access rights an account has to a particular asset. If the
	 * account has no explicit access defined for the asset, the <i>rights</i>
	 * set is used as it is. However if access has been defined, the final set
	 * is the result of a modification. For example:
	 * <pre>
	 *                  a-user +r +v +w
	 * modification                  -w
	 * becomes          a-user +r +v -w
	 * </pre>
	 * 
	 * @param path
	 *            path to the asset
	 * @param account
	 *            the account to modify rights for
	 * @param modification
	 *            the modification set
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public synchronized void modifyRights(Path path, Account account, EnumSet<Access> modification)
			throws FileNotFoundException, IOException {
				// determine the name to the .access file
				Path assetPath = repositoryRootPath.resolve(path);
				String name = null;
				if (assetPath.toFile().isDirectory()) {
					name = assetPath.getFileName() + File.separator + ".access";
				} else {
					name = "." + assetPath.getFileName().toString() + ".access";
				}
				// read the old file and update the new file
				Path accessFilePath = repositoryRootPath.resolve(assetPath.getParent().resolve(name));
				Path newAccessFilePath = repositoryRootPath.resolve(assetPath.getParent().resolve(name+".new"));
				if (accessFilePath.toFile().exists()){
					try (BufferedReader br = new BufferedReader(
							new InputStreamReader(
									new BOMInputStream(
											new FileInputStream(accessFilePath.toFile())), UTF8));
						BufferedWriter bw = Files.newBufferedWriter(newAccessFilePath, StandardOpenOption.CREATE)) {
						String in = null;
						boolean found = false;
						// iterate over all entries in the file
						while ((in = br.readLine()) != null) {
							if (in.trim().startsWith("#")) {
								bw.write(in);
								bw.newLine();
							}
							String[] split = in.trim().split("\\s+");
							// modify the existing entry
							if (split[0].equals(account.getIdentifier())) {
								EnumSet<Access> rights = EnumSet.noneOf(Access.class);
								// parse the existing specification
								parseAccessString(rights, split);
								// modify existing rights
								modify(modification, rights);
								// write the modified set
								writeRights(account, rights, bw);
								found = true;
							} else {
								bw.write(in);
								bw.newLine();
							}
						}
						// the account did not exist in the file, add an entry
						if (!found) {
							writeRights(account, modification, bw);
						}
					}
				} else {
					try (BufferedWriter bw = Files.newBufferedWriter(newAccessFilePath, StandardOpenOption.CREATE)) {
						writeRights(account, modification, bw);				
					}
				}
				
				// replace the old access file with the new version
				Files.move(newAccessFilePath, accessFilePath, StandardCopyOption.REPLACE_EXISTING);
			}

	/**
	 * Parses the access string and modifies the given set.
	 * 
	 * @param access the enumeration set to modify
	 * @param split the string to parse
	 */
	protected void parseAccessString(EnumSet<Access> access, String[] split) {
		for (int i = 1; i < split.length; i++) {
			String a = split[i];
			switch (a) {
			case "+r":
				access.add(Access.READ);
				access.remove(Access.NO_READ);
				access.remove(Access.INHERIT_READ);
				break;
			case "-r":
				access.remove(Access.READ);
				access.add(Access.NO_READ);
				access.remove(Access.INHERIT_READ);
				break;
			case "+w":
				access.add(Access.WRITE);
				access.remove(Access.NO_WRITE);
				access.remove(Access.INHERIT_WRITE);
				break;
			case "-w":
				access.remove(Access.WRITE);
				access.add(Access.NO_WRITE);
				access.remove(Access.INHERIT_WRITE);
				break;
			case "+v":
				access.add(Access.VIEW);
				access.remove(Access.NO_VIEW);
				access.remove(Access.INHERIT_VIEW);
				break;
			case "-v":
				access.remove(Access.VIEW);
				access.add(Access.NO_VIEW);
				access.remove(Access.INHERIT_VIEW);
				break;
			default:
				// Anything else, for instance "- - -" for
				// simply passing through overlying access
				// rights will be a NOP.
				break;
			}
		}
	}

	private void writeRights(Account account, EnumSet<Access> rights, BufferedWriter bw) throws IOException {
		// empty rights set should not be written, it practically means inherit everything
		if (rights.isEmpty()) {
			return;
		}
		bw.write(account.getIdentifier());
		for (Access right : rights) {
			bw.write('\t');
			bw.write(right.getLiteral());
		}
		bw.newLine();
	}

}
