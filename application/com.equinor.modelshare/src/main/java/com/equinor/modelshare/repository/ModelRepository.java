/*******************************************************************************
 * Copyright © 2020 Equinor ASA
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package com.equinor.modelshare.repository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import com.equinor.modelshare.Access;
import com.equinor.modelshare.Account;
import com.equinor.modelshare.Asset;
import com.equinor.modelshare.User;
import com.equinor.modelshare.security.RepositoryAccessControl;
import com.equinor.modelshare.Folder;
import com.equinor.modelshare.Group;
import com.equinor.modelshare.Model;

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
	 * Creates a folder on the given parent folder.
	 * 
	 * @param user
	 *            the user creating the folder
	 * @throws IOException
	 *             if the folder could not be created
	 * @throws AccessDeniedException
	 *             if the <i>user</i> does not have write access
	 */
	public void createFolder(User user, Folder parentFolder, InputStream is, String name) throws IOException;
	
	/**
	 * Creates a new user and adds it to the list of available users.
	 * 
	 * @param user         the account that is creating the user, must be a
	 *                     supervisor
	 * @param identifier   the user identifier and e-mail address
	 * @param name         the new user's full name
	 * @param organization the organization of the user
	 * @param group        the group to place the user in
	 * @param password     the password of the user
	 * @return the new user
	 * 
	 * @throws AccessDeniedException if the <i>user</i> does not have write access
	 */
	// TODO: Move to {@link RepositoryAccessControl}
	public User createUser(User user, String identifier, String name, String organisation, String group, String password) throws AccessDeniedException;
	
	// TODO: Move to {@link RepositoryAccessControl}
	public void deleteUser(User user, String identifier) throws AccessDeniedException;
	
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
	 *             if the <i>user</i> does not have write access
	 */
	public InputStream downloadModel(User user, Path path) throws AccessDeniedException, IOException;

	/**
	 * Returns a list of all accounts that have been authorized to use the 
	 * system. This list includes both users and groups.
	 * 
	 * @return a list of all accounts
	 */
	public List<Account> getAuthorizedAccounts();
	
	/**
	 * Returns a list of the users that are authorized to use this system.
	 * 
	 * @return a list of users
	 */
	// TODO: Move to {@link RepositoryAccessControl}
	public List<User> getAuthorizedUsers();

	/**
	 * Returns the group with the given identifier if found, otherwise
	 * <code>null</code> is returned.
	 * 
	 * @param id
	 *            the identifier
	 * @return the group or <code>null</code>
	 */
	// TODO: Move to {@link RepositoryAccessControl}
	public Group getGroup(String id);
	
	/**
	 * Returns a list of all groups.
	 * 
	 * @return a list of groups
	 */
	// TODO: Move to {@link RepositoryAccessControl}
	public List<Group> getGroups();
	
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
	 * @param path
	 *            path to the asset
	 * @return
	 * @throws IOException
	 */
	public File getPictureFile(Path path) throws IOException;
	
	/**
	 * Returns a map of all accounts that have explicit access to the specific
	 * resource. This does not include accounts that have inherited access to
	 * the resource, but inherited access will be included in the data.
	 * 
	 * @param requestor
	 *            the user requesting the data
	 * @param asset
	 *            the asset to get the access for
	 * @return a map of accounts with access to the resource
	 * @see RepositoryAccessControl#getRights(Path) RepositoryAccessControl#getRights(Path) for details
	 * @throws IOException
	 * @throws AccessDeniedException
	 *             if the <i>requestor</i> don't have the required credentials
	 */
	public Map<Account, EnumSet<Access>> getRights(User requestor, Path asset) throws AccessDeniedException, IOException;

	/**
	 * Returns the root folder with all it's sub-folders and contents filtered
	 * for the particular user. If the user don't have view access to an item,
	 * it will not be part of the collection.
	 * 
	 * @param user
	 *            the logged in user
	 * @return the root folder
	 */
	public Folder getRoot(User user);

	/**
	 * Returns the path to the root
	 * 
	 * @return
	 */
	public Path getRootPath();

	/**
	 * Returns the user with the given identifier if found, otherwise
	 * <code>null</code> is returned.
	 * 
	 * @param id
	 *            the identifier
	 * @return the user or <code>null</code>
	 */
	// TODO: Move to {@link RepositoryAccessControl}
	public User getUser(String id);

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
	 * Convenience method for determining whether the account has write access
	 * to the given path.
	 * 
	 * @param user
	 *            the user to test for
	 * @param path
	 *            the path to the resource
	 * @return
	 */
	public boolean hasWriteAccess(User user, Path path) throws IOException;

	/**
	 * Checks an email address for validity
	 * 
	 * @param email
	 * @return false if the email address does not meet the RFC822 standard
	 */
	public boolean isValidEmailAddress(String email);
	
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
	 *             if the <i>user</i> does not have write access
	 */
	public String localCopy(User user, Path path) throws AccessDeniedException, IOException;

	/**
	 * Sets the access rights for the specified user on a particular resource.
	 * 
	 * @param requestor
	 *            the user requesting the data
	 * @param user
	 *            the account for which to modify the rights
	 * @param asset
	 *            the asset to set the rights for
	 * @param rights
	 *            the set of access rights
	 * @throws IOException
	 * @throws AccessDeniedException
	 *             if the <i>requestor</i> don't have the required credentials
	 */
	public void modifyRights(User requestor, Account account, Path asset, EnumSet<Access> rights) throws IOException;

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
	 *             if the <i>requestor</i> does not have write access
	 */
	public void setDownloadRights(User requestor, User user, Path path) throws AccessDeniedException, IOException;

	/**
	 * Sets the password has for the specified user.
	 * 
	 * @param id
	 *            user identifier
	 * @param hash
	 *            password hash
	 */
	// TODO: Move to {@link RepositoryAccessControl}
	public void setPassword(String id, String hash);

	// TODO: Move to {@link RepositoryAccessControl}
	public void updateAccountsOnFile();

	/**
	 * The asset instance passed has been changed and must be persisted.
	 * 
	 * @param asset the asset instance to store changes for
	 * @throws IOException
	 * @throws AccessDeniedException if the user does not have access to perform
	 *                               this operation
	 */
	public void updateAsset(Asset asset) throws IOException, AccessDeniedException;
	
	/**
	 * The asset instance must be deleted.
	 * 
	 * @param asset the asset to delete
	 * 
	 * @throws IOException
	 * @throws AccessDeniedException
	 */
	public void deleteAsset(Asset asset) throws IOException, AccessDeniedException;

	/**
	 * Uploads a new model to the repository. If a model is being replaced it
	 * will be made a copy of and the file name suffixed with today's date on
	 * the form YYYY-MM-DD and prefixed with a "." to be hidden from the
	 * indexer.
	 * 
	 * @param user
	 *            the user performing the upload
	 * @param ms
	 *            the source stream for the model
	 * @param ps
	 *            the source stream for the model picture
	 * @param model
	 *            model description
	 * @param replacedModel
	 *            the replaced model or <code>null</code>
	 * @throws AccessDeniedException
	 *             if the <i>user</i> does not have write access
	 */
	public void uploadModel(User user, InputStream ms, InputStream ps, Model model, Model replacedModel) throws IOException, AccessDeniedException;

}
