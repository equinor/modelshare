package com.statoil.modelshare.app.service;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
	    private String name;
	    private ArrayList<MenuItem> children;
	    private String path;
	    private boolean leaf;

	    public MenuItem(String name) {
	        this.name = name;
	        this.children = new ArrayList<MenuItem>();
	    }

	    public MenuItem(String name, ArrayList<MenuItem> children, String path, boolean leaf) {
	        this.name = name;
	        this.children = children;
	        this.path = path;
	        this.leaf = leaf;
	    }
	    
	    public MenuItem() {
	        this.name = null;
	        this.children = null;
	        this.path = null;
	        this.leaf = false;
		}

		public void addChild(MenuItem item) {
	    	children.add(item);
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

		public void setPath(String path) {
			this.path = path;
		}

		public boolean isLeaf() {
			return leaf;
		}

		public void setLeaf(boolean leaf) {
			this.leaf = leaf;
		}
	    
	    
	} 
