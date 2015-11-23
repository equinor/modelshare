package com.statoil.modelshare.app.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import com.statoil.modelshare.Asset;
import com.statoil.modelshare.Folder;
import com.statoil.modelshare.Model;
import com.statoil.modelshare.TaskInformation;

public class AssetProxy {
	
	private Asset asset;
	private AssetProxy parent;

	public AssetProxy(AssetProxy parent, Asset asset) {
		this.asset = asset;
		this.parent = parent;
	}

	public boolean isLeaf() {
		return (asset instanceof Model);
	}
	
	public Stream<AssetProxy> stream(){
		if (this.isLeaf()) {
			return Stream.of(this);
		} else {
			return this.getChildren()
					.stream()
					.map(child -> child.stream())
					.reduce(Stream.of(this),
					(s1, s2) -> Stream.concat(s1, s2));
		}		
	}
	
	public AssetProxy getParent(){
		return parent;
	}

	public List<AssetProxy> getChildren() {
		if (asset instanceof Folder){
			List<AssetProxy> list = new ArrayList<>();
			for (Asset a : ((Folder)asset).getAssets()) {
				list.add(new AssetProxy(this,a));
			}
			return list;
		}
		return Collections.emptyList();
	}

	public String getRelativePath() {
		return asset.getRelativePath();
	}
	
	public String getName(){
		return asset.getName();
	}
	
	public List<TaskInformation> getTasks(){
		return ((Model)asset).getTaskInformation();
	}

	public Model getAsset() {
		return (Model) asset;
	}
}
