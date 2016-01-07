package com.statoil.modelshare.security;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.statoil.modelshare.Access;
import com.statoil.modelshare.Account;
import com.statoil.modelshare.Group;
import com.statoil.modelshare.ModelshareFactory;
import com.statoil.modelshare.Token;
import com.statoil.modelshare.User;

/**
 * 
 * @author Torkild U. Resheim, Itema AS
 */
public class RepositoryAccessControl {

	/** Character set to use for all file operations */
	private static final Charset UTF8 = Charset.forName("UTF-8");
	
	protected Path repositoryRootPath;
	protected Path passwordFilePath;
	protected long passwordFileModified;
	
	public static final Group SUPERVISOR = ModelshareFactory.eINSTANCE.createGroup(); 

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
		SUPERVISOR.setIdentifier("supervisor");
		SUPERVISOR.setName("Supervisor");
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
					Optional<Account> account = getAccounts()
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

	EnumSet<Access> getAccess(EnumSet<Access> access, Path path, String ident) throws IOException {
		if (ident.equals(SUPERVISOR.getIdentifier())){
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
	 * Parses the access string and modifies the given set.
	 * 
	 * @param access the enumeration set to modify
	 * @param split the string to parse
	 */
	private void parseAccessString(EnumSet<Access> access, String[] split) {
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


	private Group getGroup(List<Account> accounts, String name) {
		if (name.isEmpty()){
			return null;
		}
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
	 * is guaranteed to be up to date.
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
			getAccounts().add(newUser);
			writeAccountsFile();			
			return newUser;
		}
	}

	private List<Group> getGroups() {
		return getAccounts()
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
		Optional<Account> o = getAccounts()
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
	private void readAccounts() {
		
		List<Account> newAccounts = new ArrayList<>();
		// make sure the supervisor account is always present;
		newAccounts.add(SUPERVISOR);

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
					if (split.length < 4 || split[0].equals(SUPERVISOR.getIdentifier())) {
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
			List<Account> accounts = getAccounts();
			for (Account account : accounts) {
				if (account.getIdentifier().equals(id) && account instanceof User) {
					((User) account).setPassword(hash);
				}
			}
			writeAccounts(accounts);
		}
	}
	
	/**
	 * Writes the accounts file.
	 */
	public void writeAccountsFile(){
		synchronized (passwordFilePath) {
			List<Account> accounts = getAccounts();
			writeAccounts(accounts);
		}		
	}

	private void writeAccounts(List<Account> accounts) {
		try (OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(passwordFilePath.toFile()), UTF8)) {
			for (Account account : accounts) {
				if (account instanceof Group) {
					// just in case - an extra supervisor account can be injected
					if (account.equals(SUPERVISOR) || account.getIdentifier().equals(SUPERVISOR.getIdentifier())){
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
	public synchronized void modifyRights(Path path, Account account, EnumSet<Access> modification) throws FileNotFoundException, IOException {
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

	public void deleteUser(String identifier) {
		Account a = getAccount(identifier);
		synchronized (passwordFilePath) {
			getAccounts().remove(a);
			writeAccountsFile();			
		}
	}
}
