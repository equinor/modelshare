package com.statoil.modelshare.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import com.statoil.modelshare.Access;
import com.statoil.modelshare.Account;
import com.statoil.modelshare.User;
import com.statoil.modelshare.Folder;
import com.statoil.modelshare.Group;
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
	 * for the particular account. If the user don't have view access to an item,
	 * it will not be part of the collection.
	 * 
	 * @param user
	 *            the logged in user
	 * @return the root folder
	 */
	public Folder getRoot(User user);

	/**
	 * Returns a list of all users that have access to the repository.
	 * 
	 * @return a list of users
	 */
	public List<User> getUsers();

	/**
	 * Returns a list of all groups that have access to the repository.
	 * 
	 * @return a list of groups
	 */
	public List<Group> getGroups();
	
	/**
	 * Returns a map of all accounts that have access to the specific resource.
	 * 
	 * @param requestor
	 *            the user requesting the data
	 * @param asset
	 *            the asset to get the access for
	 * @return a map of accounts with access to the resource
	 * @throws IOException
	 * @throws AccessDeniedException
	 *             if the <i>owner</i> don't have the required credentials
	 */
	public Map<Account, EnumSet<Access>> getRights(User requestor, Path asset) throws AccessDeniedException, IOException;

	/**
	 * Sets the access rights for the specified user on a particular resource.
	 * 
	 * @param requestor
	 *            the user requesting the data
	 * @param user
	 *            the user for which to modify the rights
	 * @param asset
	 *            the asset to set the rights for
	 * @param rights
	 *            the set of access rights
	 * @throws IOException
	 * @throws AccessDeniedException
	 *             if the <i>owner</i> don't have the required credentials
	 */
	public void modifyRights(User requestor, User user, Path asset, EnumSet<Access> rights) throws IOException;
	
	/**
	 * Returns the user with the given identifier if found, otherwise
	 * <code>null</code> is returned.
	 * 
	 * @param id
	 *            the identifier
	 * @return the user or <code>null</code>
	 */
	public User getUser(String id);

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
	public boolean hasViewAccess(User user, Path path) throws IOException;

	/**
	 * Convenience method for determining whether the account has download access
	 * to the given path.
	 * 
	 * @param user
	 *            the user to test for
	 * @param path
	 *            the path to the resource
	 * @return
	 */
	public boolean hasDownloadAccess(User user, Path path) throws IOException;

	/**
	 * Convenience method to set download rights on a file / folder for a user
	 * 
	 * @param requestor
	 *            the account requesting the data
	 * @param user
	 *            the user to set access for
	 * @param path
	 *            the folder / file to set access on
	 * @throws IOException
	 * @throws AccessDeniedException
	 *             if the owner does not have write access
	 */
	public void setDownloadRights(User requestor, User user, Path path) throws AccessDeniedException, IOException;

	/**
	 * Returns the {@link InputStream} for the path if the client has access to
	 * the asset.
	 * 
	 * @param user
	 *            the account requesting the data
	 * @param path
	 *            the root relative path to the asset
	 * @throws IOException
	 *             if the file could not be read
	 * @throws AccessDeniedException
	 *             if the user does not have read access
	 */
	public InputStream downloadModel(User user, Path path) throws AccessDeniedException, IOException;

	/**
	 * Copies the asset to the local user directory if possible
	 * 
	 * @param user
	 *            the user requesting a local copy
	 * @param path
	 *            the root relative path to the asset
	 * @return a description of what actually happened
	 * @throws IOException
	 *             if the file could not be read
	 * @throws AccessDeniedException
	 *             if the user does not have read access
	 */
	public String localCopy(User user, Path path) throws AccessDeniedException, IOException;

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
	public void uploadModel(User user, InputStream ms, InputStream ps, Model model) throws IOException, AccessDeniedException;

	/**
	 * Creates a folder on the given parent folder.
	 * 
	 * @param user
	 *            the user creating the folder
	 * @throws IOException
	 *             if the folder could not be created
	 * @throws AccessDeniedException
	 *             if the user does not have write access
	 */
	public void createFolder(User user, Folder parentFolder, InputStream is, String name) throws IOException;

	/**
	 * Gets properties from the meta file and creates a model object to be
	 * returned
	 * 
	 * @param user
	 *            the user requesting the model
	 * @param path
	 *            path to the asset
	 * @return model object
	 * @throws IOException
	 */
	public Model getModel(User user, Path path) throws IOException;

	/**
	 * Returns a {@link File} instance for the asset in the specified path. This
	 * would typically be a picture of a model residing in the same folder.
	 * 
	 * @param user
	 *            the user requesting the asset
	 * @param path
	 *            path to the asset
	 * @return
	 * @throws IOException
	 */
	public File getFile(User user, Path path) throws IOException;

	/**
	 * Checks an email address for validity
	 * 
	 * @param email
	 * @return false if the email address does not meet the RFC822 standard
	 */
	public boolean isValidEmailAddress(String email);

}
