package com.statoil.modelshare.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.statoil.modelshare.Client;
import com.statoil.modelshare.Folder;

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
	public Folder getRoot(Client user);

	/**
	 * Returns a list of all users that have access to the repository.
	 * 
	 * @return a list of users
	 */
	public List<Client> getClients();

	/**
	 * Returns the user with the given identifier if found, otherwise
	 * <code>null</code> is returned.
	 * 
	 * @param id
	 *            the identifier
	 * @return the user or <code>null</code>
	 */
	public Client getUser(String id);

	/**
	 * Sets the password has for the specified user.
	 * 
	 * @param id
	 *            user identifier
	 * @param hash
	 *            password hash
	 */
	public void setPassword(String id, String hash);

	/**
	 * Convenience method for determining whether the user has view access to
	 * the given path.
	 * 
	 * @param user
	 *            the user to test for
	 * @param path
	 *            the path to the resource
	 * @return
	 */
	public boolean hasDisplayAccess(Client user, Path path) throws IOException ;	
	
	/**
	 * Uploads a file based on information given in the view
	 */
	public void uploadFile(Folder folder, File file, String owner, String organisation, String usage);
	
	/**
	 * Creates a folder on the given parent folder and the name of the new folder
	 */
	public void createFolder(Folder parentFolder, String name);
	
	/**
	 * Delete a folder given the parent folder
	 */
	public void deleteFolder(Folder parentFolder, Folder folder);
}
