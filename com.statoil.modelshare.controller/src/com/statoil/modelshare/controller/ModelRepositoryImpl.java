package com.statoil.modelshare.controller;

import java.io.File;

import com.statoil.modelshare.Folder;
import com.statoil.modelshare.Model;
import com.statoil.modelshare.ModelshareFactory;

/**
 * 
 * @author Torkild U. Resheim, Itema AS
 */
public class ModelRepositoryImpl implements ModelRepository {

	private Folder root;
	private File rootFolder;
	
	public ModelRepositoryImpl(){
		this(System.getProperty("user.home"));		
	}

	public ModelRepositoryImpl(String path) {
		root = ModelshareFactory.eINSTANCE.createFolder();
		root.setName("Root");		
		rootFolder = new File(path).getAbsoluteFile();		
		System.out.println("Using root folder at "+rootFolder);
	}

	/**
	 * This dummy implementation will list all files and folders in the users home directory.
	 */
	public Folder getRoot() {
		File[] listFiles = rootFolder.listFiles();
		for (File child : listFiles) {
			if (child.isDirectory()){
				Folder folder = ModelshareFactory.eINSTANCE.createFolder();
				folder.setName(child.getName());
				root.getAssets().add(folder);
			} else {
				Model file = ModelshareFactory.eINSTANCE.createModel();
				file.setName(child.getName());
				root.getAssets().add(file);			
			}
		}
		return root;
	}

}
