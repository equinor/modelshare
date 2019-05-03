package com.equinor.modelshare.app.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import com.equinor.modelshare.Asset;
import com.equinor.modelshare.Folder;
import com.equinor.modelshare.Model;
import com.equinor.modelshare.TaskDetails;
import com.equinor.modelshare.TaskFolder;

public class AssetProxy {
	
	private final Asset asset;
	private final AssetProxy parent;

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
	
	public String getPicturePath(){
		return asset.getPicturePath();
	}
	
	public String getFormattedDescription(){
		return asset.getFormattedDescription();
	}
	
	public String getName(){
		return asset.getName();
	}
	
	public List<TaskDetails> getTasks(){
		return ((Model)asset).getTaskDetails();
	}

	public List<TaskFolder> getFolders(){
		return ((Model)asset).getTaskFolders();
	}

	public Asset getAsset() {
		return asset;
	}
	
	public String toString(){
		return asset.getRelativePath();
	}
}
