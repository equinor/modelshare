package com.statoil.modelshare.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

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

	/**
	 * Returns a list of users that have access to the repository.
	 * 
	 * @return a list of users
	 */
	public List<User> getUsers();

	/**
	 * Sets the password has for the specified user.
	 * 
	 * @param name
	 *            user identifier
	 * @param hash
	 *            password hash
	 */
	public void setPassword(String name, String hash);

	/**
	 * Returns <code>true</code> if the user has view access to the given path.
	 * 
	 * @param user the user to test for
	 * @param path the path to the resource
	 * @return
	 */
	public boolean hasDisplayAccess(User user, Path path) throws IOException ;
}
