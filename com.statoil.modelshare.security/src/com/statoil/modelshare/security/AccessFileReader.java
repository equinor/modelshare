package com.statoil.modelshare.security;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 
 * @author Torkild U. Resheim, Itema AS
 */
public class AccessFileReader {
	
	/** Singleton instance */
	private static AccessFileReader shared;
	
	protected final Path root;
	
	public AccessFileReader() {
		root = Paths.get("models");
	}

	public AccessFileReader(String rootPath) {
		root = Paths.get(rootPath);
	}

	public static Path getSystemRoot() {
		return getSharedInstance().root;
	}

	public synchronized static AccessFileReader getSharedInstance() {
		if (shared == null){
			shared = new AccessFileReader();
		}
		return shared;
	}

	/**
	 * Alternative method for creating the singleton instance where the path
	 * to the model collection root is specified. Only the first call to this
	 * method will set up the path.
	 * 
	 * @param rootPath the path to the model collection root
	 * @return
	 */
	public synchronized static AccessFileReader getSharedInstance(String rootPath) {
		if (shared == null){
			shared = new AccessFileReader(rootPath);
		}
		return shared;
	}

}
