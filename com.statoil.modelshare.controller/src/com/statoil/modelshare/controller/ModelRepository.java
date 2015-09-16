package com.statoil.modelshare.controller;

import java.io.File;

import com.statoil.modelshare.Folder;
import com.statoil.modelshare.Model;
import com.statoil.modelshare.ModelshareFactory;

/**
 * 
 * @author Torkild U. Resheim, Itema AS
 */
public class ModelRepository {

	/**
	 * This dummy implementation will list all files and folders in the users home directory.
	 */
	public static Folder getRoot() {
		Folder root = ModelshareFactory.eINSTANCE.createFolder();
		root.setName("Root");
		File home = new File(System.getProperty("user.home")).getAbsoluteFile();		
		File[] listFiles = home.listFiles();
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
