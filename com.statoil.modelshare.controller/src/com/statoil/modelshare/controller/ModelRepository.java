package com.statoil.modelshare.controller;

import com.statoil.modelshare.Folder;
import com.statoil.modelshare.User;

public interface ModelRepository {
	/**
	 * Returns the root folder with all it's contents filtered for the
	 * particular user.
	 * 
	 * @param user
	 *            the logged in user
	 * @return the root folder
	 */
	public Folder getRoot(User user);
}
