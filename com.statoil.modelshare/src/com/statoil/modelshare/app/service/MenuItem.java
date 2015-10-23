package com.statoil.modelshare.app.service;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
	    private String name;
	    private ArrayList<MenuItem> children;
	    private String path;
	    private String relativePath;
	    private boolean leaf;

	    public MenuItem(String name, ArrayList<MenuItem> children, String path, String relativePath, boolean leaf) {
	        this.name = name;
	        this.children = children;
	        this.path = path;
			this.relativePath = relativePath;
	        this.leaf = leaf;
	    }
	    
	    public MenuItem(String name, String path, String relativePath) {
	        this.name = name;
	        this.children = new ArrayList<MenuItem>();
	        this.path = path;
	        this.relativePath = relativePath;
	        this.leaf = false;
		}

		public void addChildren(List<MenuItem> items) {
	    	children.addAll(items);
	    }
		
	    public String getName() {
	        return name;
	    }

	    public List<MenuItem> getChildren() {
	        return children;
	    }

		public String getPath() {
			return path;
		}

		public boolean isLeaf() {
			return leaf;
		}

		public String getRelativePath() {
			return relativePath;
		}

	} 
