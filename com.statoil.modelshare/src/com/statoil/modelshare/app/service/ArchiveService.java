package com.statoil.modelshare.app.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.statoil.modelshare.Asset;
import com.statoil.modelshare.Folder;
import com.statoil.modelshare.Model;
import com.statoil.modelshare.ModelshareFactory;
import com.statoil.modelshare.app.config.RepositoryConfig;
import com.statoil.modelshare.controller.ModelRepository;

public class ArchiveService {
	
	private ModelRepository repository;

	public MenuItem getMenuItemsFromAssets(String userId) throws UnsupportedEncodingException {
		if (repository==null) {
			ApplicationContext ctx = new AnnotationConfigApplicationContext(RepositoryConfig.class);
			repository = ctx.getBean(ModelRepository.class);
			((ConfigurableApplicationContext)ctx).close();
		}

		Folder root = repository.getRoot(repository.getUser(userId));
		EList<Asset> eContents = root.getAssets();
		MenuItem rootItem = new MenuItem("Root", "");
		rootItem.addChildren(getMenuItemsFromAssets(eContents, 0));
		
		return rootItem;
	}

	private List<MenuItem> getMenuItemsFromAssets(EList<Asset> eContents, int num) throws UnsupportedEncodingException {
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
	public Model getModelFromAssets(String path) throws UnsupportedEncodingException {
		Model model = ModelshareFactory.eINSTANCE.createModel();
		model.setPath(URLDecoder.decode(path, "UTF-8"));
		return model;
	}	

	private MenuItem createMenuItem(Asset eObject, boolean leaf) throws UnsupportedEncodingException {
		return new MenuItem(eObject.getName(), new ArrayList<MenuItem>(), getPath(eObject), leaf);
	}

	private String getPath(Asset eObject) throws UnsupportedEncodingException {
		String path = URLEncoder.encode(eObject.getFolder().getName()+"/"+eObject.getName(), "UTF-8");
		return path;
	}
		
}
