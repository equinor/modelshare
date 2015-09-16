package com.statoil.modelshare.controller;

import com.statoil.modelshare.Folder;
import com.statoil.modelshare.User;

/**
 * This type represents the model repository. It's content can be accessed by
 * calling {@link #getRoot(User)} which will return a filtered hierarchy of
 * folders and models. Only those assets that are accessible to the specified
 * user will be available.
 * 
 * @author Torkild U. Resheim, Itema AS
 */
public interface ModelRepository {
	/**
	 * Returns the root folder with all it's subfolders and contents filtered
	 * for the particular user.
	 * 
	 * @param user
	 *            the logged in user
	 * @return the root folder
	 */
	public Folder getRoot(User user);
}
