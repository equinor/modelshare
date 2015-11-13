package com.statoil.modelshare.app.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.emf.common.util.EList;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.statoil.modelshare.Asset;
import com.statoil.modelshare.Folder;
import com.statoil.modelshare.Model;
import com.statoil.modelshare.ModelshareFactory;
import com.statoil.modelshare.app.config.RepositoryConfig;
import com.statoil.modelshare.controller.ModelRepository;

public class ArchiveService {

	private ModelRepository repository;
	private Folder root;

	// ToDo: Refactor out of service after
	public List<MenuItem> getTopLevel(Principal principal) {
		List<MenuItem> topLevel = null;
		MenuItem menuItem = getMenuItemsFromAssets(principal.getName());
		if (menuItem != null) {
			topLevel = menuItem.getChildren();
		}
		return topLevel;
	}

	public MenuItem getMenuItemsFromAssets(String userId) {
		checkRepository();
		root = repository.getRoot(repository.getUser(userId));
		EList<Asset> eContents = root.getAssets();
		MenuItem rootItem = new MenuItem("Root", "", "");
		rootItem.addChildren(getMenuItemsFromAssets(eContents, 0));
		return rootItem;
	}

	private void checkRepository() {
		if (repository == null) {
			ApplicationContext ctx = new AnnotationConfigApplicationContext(RepositoryConfig.class);
			repository = ctx.getBean(ModelRepository.class);
			((ConfigurableApplicationContext) ctx).close();
		}
	}

	private List<MenuItem> getMenuItemsFromAssets(EList<Asset> eContents, int num) {
		List<MenuItem> items = new ArrayList<MenuItem>();
		if (eContents != null) {
			for (Asset eObject : eContents) {
				MenuItem item = null;
				if (eObject instanceof Folder) {
					item = createMenuItem(eObject, false);
					if (((Folder) eObject).getAssets() != null) {
						item.addChildren(getMenuItemsFromAssets(((Folder) eObject).getAssets(), num));
					}
				} else {
					item = createMenuItem(eObject, true);
				}
				items.add(item);
			}
		}
		return items;
	}

	public Model getModelFromAssets(String encodedPath) {
		checkRepository();
		if (encodedPath != null) {
			try {
				return repository.getMetaInformation(Paths.get(URLDecoder.decode(encodedPath, "UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private MenuItem createMenuItem(Asset eObject, boolean leaf) {
		String path = eObject.getPath();
		String relativePath = path.replace(root.getPath(), "");
		relativePath = relativePath.substring(1);
		return new MenuItem(eObject.getName(), new ArrayList<MenuItem>(), eObject.getPath(), relativePath, leaf);
	}

	public void saveFile(MultipartFile myFile, Model model)
			throws IllegalStateException, IOException, ParserConfigurationException, SAXException {
		if (myFile.getOriginalFilename().indexOf(".") == 0) {
			throw new RuntimeException("This is not a valid file name.");
		}
		checkRepository();
		File sourceFile = new File(myFile.getOriginalFilename());
		myFile.transferTo(sourceFile);
		repository.uploadFile(sourceFile, model);
	}

	public void createFolder(String path, String name) {
		checkRepository();
		Folder parentFolder = ModelshareFactory.eINSTANCE.createFolder();
		parentFolder.setPath(path);
		repository.createFolder(parentFolder, name);
	}

}
