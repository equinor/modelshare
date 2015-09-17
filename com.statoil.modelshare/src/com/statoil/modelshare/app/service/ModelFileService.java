package com.statoil.modelshare.app.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.statoil.modelshare.Asset;
import com.statoil.modelshare.Folder;
import com.statoil.modelshare.Model;
import com.statoil.modelshare.ModelshareFactory;
import com.statoil.modelshare.User;
import com.statoil.modelshare.controller.ModelRepository;
import com.statoil.modelshare.controller.ModelRepositoryImpl;

public class ModelFileService {

	public MenuItem getMenuItems(String location) {
		Path path = Paths.get(System.getProperty("user.home"), location);
		ModelRepository repo = new ModelRepositoryImpl(path);
		
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
				if(isVisible(eObject)){
					boolean model = true;
					MenuItem item = null;
					if (eObject instanceof Folder){
						item = new MenuItem(eObject.getName(), new ArrayList<MenuItem>(), eObject.getFolder().getName(), model);
						if(((Folder) eObject).getAssets() != null){
							item.addChildren(getMenuItemsFromAssets(((Folder) eObject).getAssets(), num));
						}
					}else{
						model = false;
						item = new MenuItem(eObject.getName(), new ArrayList<MenuItem>(), eObject.getFolder().getName(), model);
					}
					items.add(item);
				}
			}
		}
		return items;
	}

	private boolean isVisible(Asset eObject) {
		return 0 != eObject.getName().indexOf(".");
	}

	public MenuItem getAssetFromMenuItem(String path) {
		/*
		 * TODO: Get correct model info from meta file (based on path)
		 * Should not be a MenuItem
		 */
		MenuItem item = new MenuItem("A cool item", new ArrayList<MenuItem>(), path, true);
		System.out.println("Item is " + item.getPath());
		return item;
	}

	private User getUser() {
		/*
		 * TODO: Get user from login
		 */
		User user = ModelshareFactory.eINSTANCE.createUser();
		user.setIdentifier("users");
		user.setName("user");
		return user;
	}
	
}
