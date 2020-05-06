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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.io.input.BOMInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.equinor.modelshare.Account;
import com.equinor.modelshare.Group;
import com.equinor.modelshare.ModelshareFactory;
import com.equinor.modelshare.Token;
import com.equinor.modelshare.User;

/**
 * This type handles both the 
 * @author Torkild U. Resheim, Itema AS
 */
@Component
@Profile(value = { "!Azure" })
public class LocalRepositoryAccessControl extends RepositoryAccessControl {

	protected Path passwordFilePath;
	protected long passwordFileModified;
	
	static Logger log = LoggerFactory.getLogger(LocalRepositoryAccessControl.class.getName());
	
	private List<Account> accounts = new ArrayList<>();

	@Autowired
	public LocalRepositoryAccessControl(@Value("${repository.root}")Path root) {
		super(root);
		passwordFilePath = repositoryRootPath.resolve(".passwd");
		if (!passwordFilePath.toFile().exists()) {
			log.error("Hashed password data file does could not be found at " + passwordFilePath);
		}
		log.debug("Creating new repository access control");
		watchPasswordFile();
	}

	/**
	 * Registers for changes on the .passwd-file and reloads user data if the
	 * file has changed.
	 */
	private void watchPasswordFile() {
		Runnable run = () -> {
			try (WatchService watcher = FileSystems.getDefault().newWatchService()) {
				passwordFilePath.getParent().register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
				log.debug("Setting up watch service on " + passwordFilePath);
				while (true) {
					// wait for key to be signaled
					WatchKey key;
					try {
						key = watcher.take();
					} catch (InterruptedException x) {
						return;
					}
					for (WatchEvent<?> event : key.pollEvents()) {
						WatchEvent.Kind<?> kind = event.kind();
						if (kind == StandardWatchEventKinds.OVERFLOW) {
							continue;
						}
						@SuppressWarnings("unchecked")
						WatchEvent<Path> ev = (WatchEvent<Path>) event;
						Path changed = ev.context();
						if (changed.equals(repositoryRootPath.relativize(passwordFilePath))) {
							log.info("Password file has changed, reloading");
							readLocalAccounts();
						}
					}
					boolean valid = key.reset();
					if (!valid) {
						break;
					}
				}
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		};
		Thread thread = new Thread(run);
		thread.setDaemon(true);
		thread.start();
	}		

	private Group getGroup(List<Account> accounts, String name) {
		if (name.isEmpty()){
			return null;
		}
		for (Account account : accounts) {
			if (account.getIdentifier().equals(name) && account instanceof Group) {
				return (Group) account;
			}
		}
		log.error("Group \"" + name + "\" not found.");
		return null;
	}

	/**
	 * Returns a list of users and groups that may log into the system. The list
	 * is guaranteed to be up to date.
	 * 
	 * @return a list of accounts
	 * @throws IOException
	 */
	public List<Account> getAuthorizedAccounts() {
		long changed = passwordFilePath.toFile().lastModified();
		if (accounts == null || changed !=  passwordFileModified) {
			readLocalAccounts();
		}
		return accounts;
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
	public User createUser(String identifier, String name, String email, String organisation, String group, String password){
		synchronized (passwordFilePath) {
			User newUser = ModelshareFactory.eINSTANCE.createUser();
			newUser.setIdentifier(identifier);
			newUser.setName(name);
			newUser.setEmail(email);
			newUser.setOrganisation(organisation);
			newUser.setGroup(getGroup(group));
			if (password==null){
				SecureRandom random = new SecureRandom();
				String key = new BigInteger(130, random).toString(32);
				
				Token token = ModelshareFactory.eINSTANCE.createToken();
				token.setKey(key);
				LocalDateTime plusHours = LocalDateTime.now().plusDays(1);
				token.setTimeout(plusHours.atZone(ZoneId.systemDefault()).toEpochSecond());
				newUser.setResettoken(token);				
			} else {
				newUser.setPassword(password);
			}
			getAuthorizedAccounts().add(newUser);
			writeAccounts();			
			return newUser;
		}
	}

	private List<Group> getGroups() {
		return getAuthorizedAccounts()
				.stream()
				.filter(c -> c instanceof Group)
				.map(c -> (Group)c)
				.collect(Collectors.toList());
	}
	
	private Group getGroup(String id) {
		Optional<Group> o = getGroups()
				.stream()
				.filter(g -> g.getIdentifier().equals(id))
				.findFirst();
		return o.get();
	}

	private Account getAccount(String id) {
		Optional<Account> o = getAuthorizedAccounts()
				.stream()
				.filter(g -> g.getIdentifier().equals(id))
				.findFirst();
		return o.get();
	}

	/**
	 * Reads all the user accounts and updates the shared list.
	 * <pre>
	 * identifier:password>:[group]:[organization]:[local user]:[password reset token]:
	 * identifier:x:[group]:
	 * </pre>
	 */
	private void readLocalAccounts() {
		
		List<Account> newAccounts = new ArrayList<>();
		// make sure the supervisor account is always present;
		newAccounts.add(SUPERVISOR_GROUP);

		/*
		 * The password file format
		 * <identifier>:<password>:[<group>]:[<organization>]:[<local user>]:[<password reset token>]:
		 * <identifier>:x:[<group>]:
		 */
		synchronized (passwordFilePath) {
			String in = null;
			passwordFileModified = passwordFilePath.toFile().lastModified();			
			try (BufferedReader br = new BufferedReader(
					new InputStreamReader(
							new BOMInputStream(
									new FileInputStream(passwordFilePath.toFile())), UTF8))) {
				while ((in = br.readLine()) != null) {
					String[] split = in.split(":");
					// continue if we lack the required fields or someone has
					// added an extra supervisor account
					if (split.length < 4 || split[0].equals(SUPERVISOR_GROUP.getIdentifier())) {
						continue;
					}
					String groupName = split[2];
					// "x" as password indicates that this is a group
					if (split[1].equals("x")) {
						Group group = ModelshareFactory.eINSTANCE.createGroup();
						group.setIdentifier(split[0]);
						if (!groupName.isEmpty()){
							group.setGroup(getGroup(newAccounts, groupName));
						}
						group.setName(split[3]);
						newAccounts.add(group);
					} else {
						User user = ModelshareFactory.eINSTANCE.createUser();
						user.setIdentifier(split[0]);
						user.setEmail(split[0]);
						user.setPassword(split[1]);
						// if the password is "changeme" the user must be forced to change it
						if (split[1].equals("changeme")){
							user.setForceChangePassword(true);
						}
						if (!groupName.isEmpty()){
							user.setGroup(getGroup(newAccounts, groupName));
						}
						user.setName(split[3]);
						if (split.length > 4) {
							String value = split[4];
							user.setOrganisation(value.equals("") ? null : value);
						}
						if (split.length > 5) {
							user.setLocalUser(split[5]);
						}
						if (split.length > 6) {
							String[] resetToken = split[6].split("/");
							Token token = ModelshareFactory.eINSTANCE.createToken();
							token.setKey(resetToken[0]);
							token.setTimeout(Long.parseLong(resetToken[1]));
							user.setResettoken(token);
						}
						newAccounts.add(user);
						if (split[1].length() == 0) {
							user.setForceChangePassword(true);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			accounts = newAccounts;
		}
		log.info("Done reading .passwd " + accounts.size() + " accounts found");
	}

	/**
	 * Sets the password of the user with the specified identifier.
	 * 
	 * @param id
	 *            the user identifier
	 * @param hash
	 *            the password hash
	 */
	public void setPassword(String id, String hash) {
		synchronized (passwordFilePath) {
			List<Account> accounts = getAuthorizedAccounts();
			for (Account account : accounts) {
				if (account.getIdentifier().equals(id) && account instanceof User) {
					((User) account).setPassword(hash);
				}
			}
			writeLocalAccounts(accounts);
		}
	}
	
	/**
	 * Writes the accounts file.
	 */
	public void writeAccounts(){
		synchronized (passwordFilePath) {
			List<Account> accounts = getAuthorizedAccounts();
			writeLocalAccounts(accounts);
		}		
	}

	private void writeLocalAccounts(List<Account> accounts) {
		try (OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(passwordFilePath.toFile()), UTF8)) {
			for (Account account : accounts) {
				if (account instanceof Group) {
					// just in case - an extra supervisor account can be injected
					if (account.equals(SUPERVISOR_GROUP) || account.getIdentifier().equals(SUPERVISOR_GROUP.getIdentifier())){
						continue;
					}
					fw.write(account.getIdentifier() + ":x:");
					if (account.getGroup() != null) {
						fw.write(account.getGroup().getIdentifier() + ":");
					} else {
						fw.write(":");
					}
					fw.write(account.getName());
				} else if (account instanceof User) {
					fw.write(account.getIdentifier() + ":");
//					if (((User) account).isForceChangePassword()){
//						fw.write("changeme:");
//					} else {
						fw.write(((User) account).getPassword() + ":");
//					}
					if (account.getGroup() != null) {
						fw.write(account.getGroup().getIdentifier() + ":");
					} else {
						fw.write(":");
					}
					fw.write(account.getName());
					fw.write(":");
					if (((User) account).getOrganisation() != null) {
						fw.write(((User) account).getOrganisation() + ":");
					} else {
						fw.write(":");
					}
					if (((User) account).getLocalUser() != null) {
						fw.write(((User) account).getLocalUser()+ ":");
					} else {
						fw.write(":");
					}
					Token resettoken = ((User) account).getResettoken();
					if (resettoken != null) {
						fw.write(resettoken.getKey()+"/"+resettoken.getTimeout());
					}
				}
				fw.write(System.lineSeparator());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteUser(String identifier) {
		Account a = getAccount(identifier);
		synchronized (passwordFilePath) {
			getAuthorizedAccounts().remove(a);
			writeAccounts();			
		}
	}
}
