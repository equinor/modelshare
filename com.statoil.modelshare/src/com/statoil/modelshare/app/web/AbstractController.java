package com.statoil.modelshare.app.web;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;


import com.statoil.modelshare.Client;
import com.statoil.modelshare.Folder;
import com.statoil.modelshare.app.service.AssetProxy;
import com.statoil.modelshare.controller.ModelRepository;

/**
 * @author Torkild U. Resheim, Itema AS
 */
public abstract class AbstractController {
	
	@ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFoundException() {
        return "404";
    }

	@Autowired
	private ModelRepository modelrepository;

	protected List<AssetProxy> getRootItems(Client user) {
		AssetProxy root = new AssetProxy(null, modelrepository.getRoot(user));
		return root.getChildren();
	}

	protected List<AssetProxy> getBreadCrumb(AssetProxy asset) {
		ArrayList<AssetProxy> crumbs = new ArrayList<AssetProxy>();
		AssetProxy parent = asset;
		while (parent != null && !parent.getRelativePath().equals("/")) {
			if (!parent.isLeaf()){
				crumbs.add(0, parent);
			}
			parent = parent.getParent();
		}
		return crumbs;
	}
	
	protected List<AssetProxy> getRootNodes(AssetProxy asset){
		while(asset.getParent()!=null){
			asset = asset.getParent();
		}
		return asset.getChildren();
	}

	/**
	 * Returns an asset for the given path and user. If the user does not have
	 * access to the asset or the asset does not exist; and
	 * {@link ResourceNotFoundException} will be thrown.
	 * 
	 * @param user
	 *            the user to get the asset for
	 * @param path
	 *            the relative path to the asset
	 * @return the asset proxy
	 */
	protected AssetProxy getAssetAtPath(Client user, String path) {
		Path p = Paths.get(path);
		if (p.isAbsolute()){
			throw new IllegalArgumentException("The path is not relative: "+path);
		}
		Folder root2 = modelrepository.getRoot(user);
		AssetProxy root = new AssetProxy(null, root2);
		List<AssetProxy> list = root
				.stream()
				.filter(m -> path.equals(m.getRelativePath()))
				.collect(Collectors.toList());
		if (list.isEmpty()) {
			throw new ResourceNotFoundException("The asset \""+path+"\" is not available.");
		} else {
			return list.get(0);
		}
	}

}
