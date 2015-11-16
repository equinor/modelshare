package com.statoil.modelshare.security;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Stream;

import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.statoil.modelshare.Access;
import com.statoil.modelshare.Account;
import com.statoil.modelshare.Client;
import com.statoil.modelshare.Group;
import com.statoil.modelshare.ModelshareFactory;

/**
 * 
 * @author Torkild U. Resheim, Itema AS
 */
public class RepositoryAccessControl {

	protected Path repositoryRootPath;
	protected Path passwordFilePath;
	protected long passwordFileModified;

	static Log log = LogFactory.getLog(RepositoryAccessControl.class.getName());
	
	private List<Account> accounts = new ArrayList<>();

	@SuppressWarnings("unused")
	private RepositoryAccessControl() {
		// NOOP - use constructor specifying repository root instead
	}

	/**
	 * Path to the repository root.
	 * 
	 * @param root
	 *            the path to the repository root
	 */
	public RepositoryAccessControl(Path root) {
		// Just making sure - we must have an absolute path.
		repositoryRootPath = root.toAbsolutePath();
		passwordFilePath = repositoryRootPath.resolve(".passwd");
		log.debug("Creating new repository access control");
		watch();
	}

	/**
	 * Registers for changes on the .passwd-file and reloads user data if the
	 * file has changed.
	 */
	private void watch() {
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
							readAccounts();
						}
					}
					boolean valid = key.reset();
					if (!valid) {
						break;
					}
				}
			} catch (IOException e) {
				log.fatal(e.getMessage());
			}
		};
		Thread thread = new Thread(run);
		thread.setDaemon(true);
		thread.start();
	}

	EnumSet<Access> getAccess(EnumSet<Access> access, Path path, String ident) throws IOException {
		String name = null;
		if (path.toAbsolutePath().toFile().isDirectory()) {
			name = path.getFileName() + File.separator + ".access";
		} else {
			name = "." + path.getFileName().toString() + ".access";
		}
		Path filePath = repositoryRootPath.resolve(path.getParent().resolve(name));
		File file = filePath.toFile();
		if (!file.exists()) {
			return access;
		}
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new BOMInputStream(
								new FileInputStream(file))))) {
			String in = null;
			while ((in = br.readLine()) != null) {
				if (!in.startsWith("#") && in.length() > 0) {
					String[] split = in.trim().split("\\s+");
					if (split.length != 4)
						throw new IOException("Invalid file format " + filePath);
					if (ident.equals(split[0])) {
						for (int i = 1; i < 4; i++) {
							String a = split[i];
							switch (a) {
							case "+r":
								access.add(Access.READ);
								break;
							case "-r":
								access.remove(Access.READ);
								break;
							case "+w":
								access.add(Access.WRITE);
								break;
							case "-w":
								access.remove(Access.WRITE);
								break;
							case "+v":
								access.add(Access.VIEW);
								break;
							case "-v":
								access.remove(Access.VIEW);
								break;
							default:
								// Anything else, for instance "- - -" for
								// simply passing through overlying access
								// rights will be a NOP.
								break;
							}
						}
					}
				}
			}
		}
		return access;
	}

	/**
	 * This method will for each account derived from the initial go into each
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

	public void setDownloadRights(Path path, Client client) throws IOException {
		String name = null;
		if (path.toAbsolutePath().toFile().isDirectory()) {
			name = path.getFileName() + File.separator + ".access";
		} else {
			name = "." + path.getFileName().toString() + ".access";
		}
		Path filePath = repositoryRootPath.resolve(path.getParent().resolve(name));
		File accessFile = filePath.toFile();
		if (accessFile.exists()) {
			List<AccessRight> accessRights = new ArrayList<>();
			boolean foundClient = false;
			try (BufferedReader reader = Files.newBufferedReader(filePath)) {
				Stream<String> lines = reader.lines();
				Iterator<String> iterator = lines.iterator();
				while (iterator.hasNext()) {
					String line = iterator.next();
					StringTokenizer tokenizer = new StringTokenizer(line);
					String ident = "";
					String view = "";
					String read = "";
					String write = "";
					while (tokenizer.hasMoreTokens()) {
						String token = tokenizer.nextToken();
						if (token.contains("-r") || token.contains("+r")) {
							read = token;
						} else if (token.contains("-v") || token.contains("+v")) {
							view = token;
						} else if (token.contains("-w") || token.contains("+w")) {
							write = token;
						} else {
							ident = token;
						}
					}
					if (ident.contentEquals(client.getIdentifier())) {
						foundClient = true;
						read = "+r";
					}
					AccessRight aRight = new AccessRight(ident, view, read, write);
					accessRights.add(aRight);
				}
			}

			if (!foundClient) {
				AccessRight newAccessRight = new AccessRight(client.getIdentifier(), "+v", "+r", "-w");
				accessRights.add(newAccessRight);
			}

			try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
				for (AccessRight accessRight : accessRights) {
					writer.write(accessRight.name + " " + accessRight.view + " " + accessRight.read + " "
							+ accessRight.write);
					writer.newLine();
				}
				writer.flush();
				writer.close();
			}

		} else {
			File newAccessFile = Files.createFile(filePath).toFile();
			BufferedWriter writer = Files.newBufferedWriter(newAccessFile.toPath());
			writer.write(client.getIdentifier() + " " + "+v +r -w");
			writer.flush();
			writer.close();
		}
	}

	private class AccessRight {
		private String name;
		private String view;
		private String read;
		private String write;

		public AccessRight(String name, String view, String read, String write) {
			this.name = name;
			this.view = view;
			this.read = read;
			this.write = write;
		}
	}

	private Group getGroup(List<Account> accounts, String name) {
		for (Account account : accounts) {
			if (account.getIdentifier().equals(name) && account instanceof Group) {
				return (Group) account;
			}
		}
		log.fatal("Group \"" + name + "\" not found.");
		return null;
	}

	/**
	 * Returns a list of users and groups that may log into the system. The list
	 * is guaranteed to be up to date, the underlying data is re-read on every
	 * call of this method.
	 * 
	 * @return a list of accounts
	 * @throws IOException
	 */
	public List<Account> getAccounts() {
		long changed = passwordFilePath.toFile().lastModified();
		if (accounts == null || changed !=  passwordFileModified) {
			readAccounts();
		}
		return accounts;
	}

	/**
	 * Reads all the user accounts and updates the shared list.
	 */
	private void readAccounts() {
		
		List<Account> newAccounts = new ArrayList<>();

		synchronized (passwordFilePath) {
			String in = null;
			passwordFileModified = passwordFilePath.toFile().lastModified();			
			try (BufferedReader br = new BufferedReader(
					new InputStreamReader(
							new BOMInputStream(
									new FileInputStream(passwordFilePath.toFile()))))) {
				while ((in = br.readLine()) != null) {
					String[] split = in.split(":");
					if (split.length < 4) {
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
						Client user = ModelshareFactory.eINSTANCE.createClient();
						user.setIdentifier(split[0]);
						user.setEmail(split[0]);
						user.setPassword(split[1]);
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
	public synchronized void setPassword(String id, String hash) {
		synchronized (passwordFilePath) {
			List<Account> accounts = getAccounts();
			for (Account account : accounts) {
				if (account.getIdentifier().equals(id) && account instanceof Client) {
					((Client) account).setPassword(hash);
				}
			}
			try (FileWriter fw = new FileWriter(passwordFilePath.toFile())) {
				for (Account account : accounts) {
					if (account instanceof Group) {
						fw.write(account.getIdentifier() + ":x:");
						if (account.getGroup() != null) {
							fw.write(account.getGroup().getIdentifier() + ":");
						} else {
							fw.write(":");
						}
						fw.write(account.getName());
					} else if (account instanceof Client) {
						fw.write(account.getIdentifier() + ":");
						fw.write(((Client) account).getPassword() + ":");
						if (account.getGroup() != null) {
							fw.write(account.getGroup().getIdentifier() + ":");
						} else {
							fw.write(":");
						}
						fw.write(account.getName());
						fw.write(":");
						if (((Client) account).getOrganisation() != null) {
							fw.write(((Client) account).getOrganisation() + ":");
						} else {
							fw.write(":");
						}
						if (((Client) account).getLocalUser() != null) {
							fw.write(((Client) account).getLocalUser());
						}
					}
					fw.write(System.lineSeparator());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
