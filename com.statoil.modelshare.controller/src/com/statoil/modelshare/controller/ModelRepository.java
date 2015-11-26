package com.statoil.modelshare.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.List;

import com.statoil.modelshare.Client;
import com.statoil.modelshare.Folder;
import com.statoil.modelshare.Model;

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
	 * for the particular user. If the user don't have view access to an item,
	 * it will not be part of the collection.
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
	public boolean hasViewAccess(Client user, Path path) throws IOException;

	/**
	 * Convenience method for determining whether the user has download access
	 * to the given path.
	 * 
	 * @param user
	 *            the user to test for
	 * @param path
	 *            the path to the resource
	 * @return
	 */
	public boolean hasDownloadAccess(Client user, Path path) throws IOException;

	/**
	 * Convenience method to set download rights on a file / folder for a user
	 * 
	 * @param owner
	 *            the model owner
	 * @param user
	 *            the user to set access for
	 * @param path
	 *            the folder / file to set access on
	 * @throws IOException
	 * @throws AccessDeniedException
	 *             if the owner does not have write access
	 */
	public void setDownloadRights(Client owner, Client user, Path path) throws AccessDeniedException, IOException;

	/**
	 * Returns the {@link InputStream} for the path if the client has access to
	 * the asset.
	 * 
	 * @param user
	 *            the user that requests the asset
	 * @param path
	 *            the root relative path to the asset
	 * @throws IOException
	 *             if the file could not be read
	 * @throws AccessDeniedException
	 *             if the user does not have read access
	 */
	public InputStream downloadModel(Client user, Path path) throws AccessDeniedException, IOException;

	/**
	 * Copies the asset to the local user directory if possible
	 * 
	 * @param user
	 *            the user that requests the asset
	 * @param path
	 *            the root relative path to the asset
	 * @throws IOException
	 *             if the file could not be read
	 * @throws AccessDeniedException
	 *             if the user does not have read access
	 */
	public String localCopy(Client user, Path path) throws AccessDeniedException, IOException;

	/**
	 * Uploads a new model to the repository.
	 * 
	 * @param user
	 *            the user performing the upload
	 * @param ms
	 *            the source stream for the model
	 * @param ps
	 *            the source stream for the model picture
	 * @param model
	 *            model description
	 * @throws AccessDeniedException
	 *             if the user does not have write access
	 */
	public void uploadModel(Client user, InputStream ms, InputStream ps, Model model) throws IOException, AccessDeniedException;

	/**
	 * Creates a folder on the given parent folder.
	 * 
	 * @throws IOException
	 *             if the folder could not be created
	 * @throws AccessDeniedException
	 *             if the user does not have write access
	 */
	public void createFolder(Client user, Folder parentFolder, InputStream is, String name) throws IOException;

	/**
	 * Gets properties from the meta file and creates a model object to be
	 * returned
	 * 
	 * @param path
	 * @return model object
	 * @throws IOException 
	 */
	public Model getMetaInformation(Client user, Path path) throws IOException;
	
	
	public File getFile(Client user, Path path) throws IOException;

	/**
	 * Checks an email address for validity
	 * 
	 * @param email
	 * @return false if the email address does not meet the RFC822 standard
	 */
	public boolean isValidEmailAddress(String email);

}
