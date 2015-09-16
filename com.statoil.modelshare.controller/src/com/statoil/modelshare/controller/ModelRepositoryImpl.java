package com.statoil.modelshare.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;

import com.statoil.modelshare.Access;
import com.statoil.modelshare.Folder;
import com.statoil.modelshare.Model;
import com.statoil.modelshare.ModelshareFactory;
import com.statoil.modelshare.User;
import com.statoil.modelshare.security.RepositoryAccessControl;

/**
 * @author Torkild U. Resheim, Itema AS
 */
public class ModelRepositoryImpl implements ModelRepository {

	private Folder root;
	private File rootFolder;
	private RepositoryAccessControl ra;

	public ModelRepositoryImpl() {
		this(System.getProperty("user.home") + File.separator + "modelshare" + File.separator + "repository" + File.separator);
	}

	public ModelRepositoryImpl(String path) {
		root = ModelshareFactory.eINSTANCE.createFolder();
		root.setName("");
		rootFolder = new File(path).getAbsoluteFile();
		ra = RepositoryAccessControl.getSharedInstance(Paths.get(path).toAbsolutePath());
		System.out.println("Using root folder at " + rootFolder);
	}

	public void fillFolderContents(Folder folder, User user) throws IOException {
		File file = rootFolder.toPath().resolve(folder.getName()).toFile();
		if (!file.exists()) {
			return;
		}
		Path path = rootFolder.toPath().relativize(file.toPath());
		// Do not handle folders where the user have no access
		EnumSet<Access> rights = ra.getRights(path, user);			
		if (!(rights.contains(Access.READ) 
				|| rights.contains(Access.WRITE) 
				|| rights.contains(Access.VIEW))) return;

		
		File[] listFiles = file.listFiles();
		for (File child : listFiles) {
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

	public Folder getRoot(User user) {
		if (root.getAssets().isEmpty()) {
			try {
				fillFolderContents(root, user);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return root;
	}

}
