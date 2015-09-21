package com.statoil.modelshare.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import com.statoil.modelshare.Access;
import com.statoil.modelshare.Account;
import com.statoil.modelshare.Client;
import com.statoil.modelshare.Folder;
import com.statoil.modelshare.Model;
import com.statoil.modelshare.ModelshareFactory;
import com.statoil.modelshare.security.RepositoryAccessControl;

/**
 * @author Torkild U. Resheim, Itema AS
 */
public class ModelRepositoryImpl implements ModelRepository {

	private Path rootPath;
	private RepositoryAccessControl ra;

	@SuppressWarnings("unused")
	private ModelRepositoryImpl() {
		// NOOP - use constructor specifying repository root instead
	}

	/**
	 * Creates a new model repository. Files are retrieved and stored at the
	 * given location.
	 * 
	 * @param path
	 *            path to the repository root.
	 */
	public ModelRepositoryImpl(Path path) {
		rootPath = path.toAbsolutePath();
		ra = new RepositoryAccessControl(rootPath);
		System.out.println("Using root folder at " + rootPath);
	}

	private void fillFolderContents(Folder folder, Client user) throws IOException {
		File file = rootPath.resolve(folder.getName()).toFile();
		if (!file.exists()) {
			return;
		}

		// List all files except those that are hidden
		File[] listFiles = file.listFiles((FilenameFilter) (dir, name) -> {
			return (!name.startsWith("."));
		});

		// Recurse into subfolders and add files
		for (File child : listFiles) {
			// Ignore those where the user have no access
			if (hasDisplayAccess(user, child.toPath())) {
				if (child.isDirectory()) {
					Folder newFolder = ModelshareFactory.eINSTANCE.createFolder();
					newFolder.setName(child.getName());
					folder.getAssets().add(newFolder);
					fillFolderContents(newFolder, user);
				} else {
					Model newFile = ModelshareFactory.eINSTANCE.createModel();
					newFile.setName(child.getName());
					folder.getAssets().add(newFile);
				}
			}
		}
	}

	public boolean hasDisplayAccess(Client user, Path path) throws IOException {
		EnumSet<Access> rights = ra.getRights(path, user);
		return ((rights.contains(Access.READ) || rights.contains(Access.WRITE) || rights.contains(Access.VIEW)));
	}

	public Folder getRoot(Client user) {
		Folder root = ModelshareFactory.eINSTANCE.createFolder();
		root.setName("");
		try {
			fillFolderContents(root, user);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return root;
	}

	@Override
	public void createFolder(Folder parentFolder, String name) {
		Folder newFolder = ModelshareFactory.eINSTANCE.createFolder();
		newFolder.setName(name);
		parentFolder.getAssets().add(newFolder);
	}
	
	@Override
	public void deleteFolder(Folder parentFolder, Folder folder) {
		parentFolder.getAssets().remove(folder);
	}
	
	@Override
	public void uploadFile(Folder folder, File file, String owner, String organisation, String usage) {
		Model model = ModelshareFactory.eINSTANCE.createModel();
		model.setOwner(owner);
		model.setLastUpdated(new Date());
		model.setName(file.getName());
		model.setOrganisation(organisation);
		model.setPath(folder.getName() + File.separator + file.getName());
		model.setUsage(usage);
		folder.getAssets().add(model);
	}
	
	@Override
	public List<Client> getClients() {
		List<Client> users = new ArrayList<>();
		List<Account> accounts = ra.getAccounts();
		for (Account account : accounts) {
			if (account instanceof Client) {
				users.add((Client) account);
			}
		}
		return users;
	}

	@Override
	public void setPassword(String name, String hash) {
		ra.setPassword(name, hash);
	}

	@Override
	public Client getUser(String id) {
		for (Client user : getClients()) {
			if (user.getIdentifier().equals(id)) {
				return user;
			}
		}
		return null;
	}

}
