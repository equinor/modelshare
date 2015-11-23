package com.statoil.modelshare.app.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.statoil.modelshare.Client;
import com.statoil.modelshare.Folder;
import com.statoil.modelshare.app.service.AssetProxy;
import com.statoil.modelshare.controller.ModelRepository;

public abstract class AbstractController {

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

	protected AssetProxy getMenuItem(Client user, String path) {
		Folder root2 = modelrepository.getRoot(user);
		AssetProxy root = new AssetProxy(null, root2);
		List<AssetProxy> list = root.stream().filter(m -> path.equals(m.getRelativePath()))
				.collect(Collectors.toList());
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

}
