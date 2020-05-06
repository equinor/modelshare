/*******************************************************************************
 * Copyright © 2020 Equinor ASA
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package com.equinor.modelshare.app.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
			list.sort(new Comparator<AssetProxy>() {

				@Override
				public int compare(AssetProxy o1, AssetProxy o2) {
					if (o1.isLeaf() && !o2.isLeaf()) {
						return 1;
					}
					if (!o1.isLeaf() && o2.isLeaf()) {
						return -1;			
					}
					return o1.getName().compareTo(o2.getName());
				}
			});
			
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
