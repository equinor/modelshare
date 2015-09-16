package com.statoil.modelshare.security;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.List;

import com.statoil.modelshare.Access;
import com.statoil.modelshare.Account;

/**
 * 
 * @author Torkild U. Resheim, Itema AS
 */
public class RepositoryAccessControl {

	/** Singleton instance */
	private static RepositoryAccessControl shared;

	protected final Path root;

	public RepositoryAccessControl(Path rootPath) {
		root = rootPath.toAbsolutePath();
	}

	/**
	 * Alternative method for creating the singleton instance where the path to
	 * the model collection root is specified. Only the first call to this
	 * method will set up the path.
	 * 
	 * @param rootPath
	 *            the path to the model collection root
	 * @return the shared instance
	 */
	public synchronized static RepositoryAccessControl getSharedInstance(Path rootPath) {
		if (shared == null) {
			shared = new RepositoryAccessControl(rootPath);
		}
		return shared;
	}

	EnumSet<Access> getAccess(EnumSet<Access> access, Path path, String ident) throws IOException {
		String name = null;
		if (path.toAbsolutePath().toFile().isDirectory()) {
			name = path.getFileName() + File.separator + ".access";
		} else {
			name = "." + path.getFileName().toString() + ".access";
		}
		Path filePath = root.resolve(path.getParent().resolve(name));
		File file = filePath.toFile();
		if (!file.exists()) {
			return access;
		}
		FileReader fr = new FileReader(file);
		BufferedReader bfr = new BufferedReader(fr);
		try {
			String in = null;
			while ((in = bfr.readLine()) != null) {
				if (!in.startsWith("#") && in.length() > 0) {
					String[] split = in.split("\\s+");
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
		} finally {
			bfr.close();
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
	 * used.
	 * </p>
	 * 
	 * @param path
	 *            the path to the file or folder, relative to the model storage
	 *            root
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
			Path r = root;
			Path p = root.relativize(root.resolve(path));
			int nameCount = p.getNameCount();
			for (int i = 0; i <= nameCount; i++) {
				access = getAccess(access, r, a.getIdentifier());
				if (i < nameCount) {
					r = r.resolve(p.getName(i));
				}
			}
		}
		return access;
	}

}
