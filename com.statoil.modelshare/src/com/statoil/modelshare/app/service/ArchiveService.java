package com.statoil.modelshare.app.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.statoil.modelshare.Asset;
import com.statoil.modelshare.Folder;
import com.statoil.modelshare.ModelshareFactory;
import com.statoil.modelshare.User;
import com.statoil.modelshare.controller.ModelRepository;
import com.statoil.modelshare.controller.ModelRepositoryImpl;

public class ArchiveService {

	public MenuItem getMenuItems() {
		
		ModelRepository repo = new ModelRepositoryImpl(getRepositoryPath());
		Folder root = repo.getRoot(getUser());
	
		EList<Asset> eContents = root.getAssets();
		MenuItem rootItem = new MenuItem("Root");
		rootItem.addChildren(getMenuItemsFromAssets(eContents, 0));

		return rootItem;
	}

	private List<MenuItem> getMenuItemsFromAssets(EList<Asset> eContents, int num) {
		List<MenuItem> items = new ArrayList<MenuItem>();
		if(eContents != null){
			for (Asset eObject : eContents) {
				MenuItem item = null;
				if (eObject instanceof Folder){
					item = createMenuItem(eObject, false);
					if(((Folder) eObject).getAssets() != null){
						item.addChildren(getMenuItemsFromAssets(((Folder) eObject).getAssets(), num));
					}
				}else{
					item = createMenuItem(eObject, true);
				}
				items.add(item);
			}
		}
		return items;
	}

	private MenuItem createMenuItem(Asset eObject, boolean leaf) {
		return new MenuItem(eObject.getName(), new ArrayList<MenuItem>(), eObject.getFolder().getName(), leaf);
	}

	private Path getRepositoryPath() {
		// TODO: Get correct repository path
		
		return  Paths.get(System.getProperty("user.home"), "Documents/Models");
	}

	public ModelInformation getModelInformation(String item) throws FileNotFoundException {
		Path path = new File(item).toPath();
		ModelInformation modelInfo = new ModelInformation(path);
		return modelInfo;
	}

	private User getUser() {
		// TODO: Get correct user info from login

		User user = ModelshareFactory.eINSTANCE.createUser();
		user.setIdentifier("users");
		user.setName("user");
		return user;
	}
	
}
