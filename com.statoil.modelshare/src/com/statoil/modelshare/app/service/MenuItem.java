package com.statoil.modelshare.app.service;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
	    private String name;
	    private ArrayList<MenuItem> children;
	    private String path;
	    private boolean leaf;

	    public MenuItem(String name, ArrayList<MenuItem> children, String path, boolean leaf) {
	        this.name = name;
	        this.children = children;
	        this.path = path;
	        this.leaf = leaf;
	    }
	    
	    public MenuItem(String name) {
	        this.name = name;
	        this.children = new ArrayList<MenuItem>();
	        this.path = null;
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
	} 
