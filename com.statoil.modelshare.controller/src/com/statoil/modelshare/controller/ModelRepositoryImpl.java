package com.statoil.modelshare.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.statoil.modelshare.Access;
import com.statoil.modelshare.Account;
import com.statoil.modelshare.Asset;
import com.statoil.modelshare.Client;
import com.statoil.modelshare.Folder;
import com.statoil.modelshare.Model;
import com.statoil.modelshare.ModelshareFactory;
import com.statoil.modelshare.ModelsharePackage;
import com.statoil.modelshare.security.RepositoryAccessControl;
import com.statoil.modelshare.util.ParseUtility;

/**
 * @author Torkild U. Resheim, Itema AS
 */
public class ModelRepositoryImpl implements ModelRepository {

	static Log log = LogFactory.getLog(ModelRepository.class.getName());
	
	static Log downloadLog = LogFactory.getLog("downloadLogger");

	/** Root for storing repository files */
	private Path rootPath;

	/** Root for user home folders */
	private Path userRoot;
	
	/** Cached root folders */
	private Map<Client,CachedFolder> rootCache;
	
	private RepositoryAccessControl ra;
	
	/**
	 * A simple folder cache.
	 */
	private class CachedFolder {
		private Folder folder;
		private LocalDateTime timestamp;
	}

	@SuppressWarnings("unused")
	private ModelRepositoryImpl() {
		// NOOP - use constructor specifying repository root instead
	}

	/**
	 * Creates a new model repository. Files are retrieved and stored at the
	 * given location.
	 * 
	 * @param path
	 *            path to the repository root.
	 */
	public ModelRepositoryImpl(Path path, Path userRoot) {
		rootPath = path.toAbsolutePath();
		ra = new RepositoryAccessControl(rootPath);
		this.userRoot = userRoot;		
		// register the extension
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
	    Map<String, Object> m = reg.getExtensionToFactoryMap();
	    m.put("modeldata", new XMIResourceFactoryImpl());
	    rootCache = Collections.synchronizedMap(new HashMap<>());
	    
	    FileAlterationObserver fao = new FileAlterationObserver(rootPath.toFile());
	    fao.addListener(new FileAlterationListenerAdaptor(){

			@Override
			public void onDirectoryCreate(File directory) {
				rootCache.clear();
			}

			@Override
			public void onDirectoryChange(File directory) {
				rootCache.clear();
			}

			@Override
			public void onDirectoryDelete(File directory) {
				rootCache.clear();
			}

			@Override
			public void onFileCreate(File file) {
				rootCache.clear();
			}

			@Override
			public void onFileChange(File file) {
				rootCache.clear();
			}

			@Override
			public void onFileDelete(File file) {
				rootCache.clear();
			}
	    	
	    });
	    FileAlterationMonitor fam = new FileAlterationMonitor();
	    fam.addObserver(fao);
	    try {
			fam.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createFolder(Client user, Folder parentFolder, InputStream is, String name) throws IOException {
		EnumSet<Access> rights = ra.getRights(Paths.get(parentFolder.getPath()), user);
		if (!rights.contains(Access.WRITE)) {
			throw new AccessDeniedException(parentFolder.toString());
		}
		File dir = new File(parentFolder.getPath(), name);
		dir.mkdir();
		if (is!=null) {
			Path path = dir.getParentFile().toPath().resolve(name+".jpg");
			Files.copy(is, path, StandardCopyOption.REPLACE_EXISTING);
		}

		// clear the cache for all users
		rootCache.clear();
	}

	private void fillFolderContents(Folder rootFolder, Client user) throws IOException {
		File file = new File(rootFolder.getPath());
		if (!file.exists()) {
			return;
		}

		// list all files except those that are hidden
		File[] listFiles = file.listFiles((FilenameFilter) (dir, name) -> {
			return (!name.startsWith(".") 
					&& !name.endsWith(".jpg") 
					&& !name.endsWith(".modeldata")
					&& !name.equals("pages"));
		});

		// recurse into subfolders and add files
		for (File child : listFiles) {
			// ignore those where the user have no access
			if (hasViewAccess(user, child.toPath())) {
				String relativePath = rootPath.relativize(child.toPath()).toString().replace('\\', '/');
				if (child.isDirectory()) {
					Folder folder = ModelshareFactory.eINSTANCE.createFolder();
					folder.setName(child.getName());
					folder.setPath(child.getAbsolutePath());
					folder.setRelativePath(relativePath);
					setPicturePath(folder, child);
					rootFolder.getAssets().add(folder);
					fillFolderContents(folder, user);
				} else {
					Model model = getModel(user, child.toPath());
					model.setPath(child.getAbsolutePath());
					model.setRelativePath(relativePath);
					setPicturePath(model, child);
					rootFolder.getAssets().add(model);
				}
			}
		}
	}

	private void setPicturePath(Asset asset, File child) {
		File picture = new File(child.getAbsolutePath()+".jpg");
		if (picture.exists()){
			String picturePath = rootPath.relativize(picture.toPath()).toString().replace('\\', '/');
			asset.setPicturePath(picturePath);
		} else {
			asset.setPicturePath(null);
		}
	}

	@Override
	public List<Client> getClients() {
		List<Client> users = new ArrayList<>();
		List<Account> accounts = ra.getAccounts();
		for (Account account : accounts) {
			if (account instanceof Client) {
				users.add((Client) account);
			}
		}
		return users;
	}

	@Override
	public InputStream downloadModel(Client user, Path path) throws IOException {
		File file = rootPath.resolve(path).toFile();
		EnumSet<Access> rights = ra.getRights(path, user);
		if (rights.contains(Access.READ)) {
			return new FileInputStream(file);
		}
		else {
			throw new AccessDeniedException(path.toString());
		}
	}

	@Override
	public Model getModel(Client owner, Path path) throws IOException {
		Properties resultProps = new Properties();
		Path resolvedPath = rootPath.resolve(path);
		String fileName = resolvedPath.getFileName().toString();
		
		File metaFile = new File(resolvedPath.toFile().getParent() + File.separator + "." + fileName + ".meta");
		File dataFile = new File(resolvedPath.toFile()+".modeldata");
		
		if (metaFile.exists()){
			try {
				// read the old properties if present
				final FileInputStream in = new FileInputStream(metaFile);
				resultProps.loadFromXML(in);
				in.close();
				// create a new model instance
				Model model = ModelshareFactory.eINSTANCE.createModel();
				String relativePath = rootPath.relativize(path).toString().replace('\\', '/');
				model.setOwner(resultProps.getProperty("owner"));
				model.setLastUpdated(resultProps.getProperty("lastUpdated"));
				model.setName(resultProps.getProperty("name"));
				model.setOrganisation(resultProps.getProperty("organisation"));
				model.setPath(path.toString());
				model.setRelativePath(relativePath);
				model.setUsage(resultProps.getProperty("usage"));
				model.setMail(resultProps.getProperty("mail"));
				// parse the SIMA-model file
				if (fileName.endsWith(".stask")){
					model.getTaskDetails().clear();
					model.getTaskFolders().clear();
					ParseUtility.parseSimaModel(resolvedPath, model);
				}
				saveModelData(model, resolvedPath);
				metaFile.delete();
				return model;
			} catch (IOException ioe) {
				log.error("Error reading meta information from " + metaFile.getAbsolutePath());
			}
		}
		else if (dataFile.exists()){
			// required to initialize the package URI
			@SuppressWarnings("unused")
			ModelsharePackage mf = ModelsharePackage.eINSTANCE;
			ResourceSet rs = new ResourceSetImpl();
			Resource resource = rs.getResource(URI.createFileURI(dataFile.getAbsolutePath()), true);
			resource.load(null);
		    return (Model)resource.getContents().get(0);
			
		}
		// the file may not exist due to parsing errors when uploading the file
		// dummy model
		Model model = ModelshareFactory.eINSTANCE.createModel();
		model.setName(path.getFileName().toString());
		model.setOwner("Unkown");
		model.setMail("Unknown");
		model.setOrganisation("");
		model.setLastUpdated(LocalDateTime.now().toString());
		model.setPath(path.toString());
		model.setUsage("Metadata is not on record for this model. Content has been generated on the fly and is not persisted.");
		if (fileName.endsWith(".stask")){
			ParseUtility.parseSimaModel(resolvedPath, model);
		}				
		return model;
	}
	
	public Folder getRoot(Client user) {
		
		// use the cache if it exists
		CachedFolder c = rootCache.get(user);
		if (c!=null && LocalDateTime.now().isBefore(c.timestamp.plusMinutes(5))){
			return c.folder;
		}
		
		Folder root = ModelshareFactory.eINSTANCE.createFolder();
		root.setPath(rootPath.toString());
		root.setName("");
		root.setRelativePath("/");
		try {
			fillFolderContents(root, user);
		} catch (IOException e) {
			rootCache.remove(user);
			throw new RuntimeException(e);
		}
		
		// cache the folder
		c = new CachedFolder();
		c.folder = root;
		c.timestamp = LocalDateTime.now();
		rootCache.put(user, c);
		
		return root;
	}

	@Override
	public Client getUser(String id) {
		for (Client user : getClients()) {
			if (user.getIdentifier().equals(id)) {
				return user;
			}
		}
		return null;
	}

	public boolean hasDownloadAccess(Client user, Path path) throws IOException {
		EnumSet<Access> rights = ra.getRights(path, user);
		return rights.contains(Access.READ);
	}

	public boolean hasViewAccess(Client user, Path path) throws IOException {
		EnumSet<Access> rights = ra.getRights(path, user);
		return rights.contains(Access.VIEW);
	}

	// XXX: This does not belong here!
	@Override
	public boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	@Override
	public String localCopy(Client user, Path path) throws AccessDeniedException, IOException {
		String localUser = user.getLocalUser();
		// a local user must be specified
		if (localUser == null || localUser.isEmpty()) {
			throw new AccessDeniedException(null, null, "Cannot copy file. User does not have a local account");
		}
		EnumSet<Access> rights = ra.getRights(path, user);
		if (rights.contains(Access.READ)) {
			Path target = userRoot.resolve(Paths.get(localUser, "Documents", "Model-export",path.getFileName().toString()));
			String message = "Model copied to "+target.toString();
			File file = target.toFile();
			if (file.exists()){
				message = "Model copied to "+target.toString()+". Existing file was overwritten.";
			}
			file.mkdirs();
			Files.copy(rootPath.resolve(path), target, StandardCopyOption.REPLACE_EXISTING);
			Object[] messageArgs = { path, user.getIdentifier() };
			downloadLog.info(MessageFormat.format("Model \"{0}\" was downloaded by {1}", messageArgs));
			return message;
		} else {
			throw new AccessDeniedException(path.toString());
		}
	}

	@Override
	public void setDownloadRights(Client owner, Client user, Path path) throws AccessDeniedException, IOException {
		EnumSet<Access> rights = ra.getRights(path, owner);
		if (rights.contains(Access.WRITE)) {
			ra.setDownloadRights(path, user);
		}
		else {
			throw new AccessDeniedException(path.toString());
		}
	}

	@Override
	public void setPassword(String name, String hash) {
		ra.setPassword(name, hash);
	}

	@Override
	public void uploadModel(Client user, InputStream modelStream, InputStream pictureStream, Model model)
			throws IOException, AccessDeniedException {
		// assert that the user has write access
		Path path = rootPath.resolve(model.getRelativePath());
		EnumSet<Access> rights = ra.getRights(path, user);
		if (!rights.contains(Access.WRITE)) {
			throw new AccessDeniedException(path.toString());
		}

		// do the actual write in one operation
		Files.copy(modelStream, path, StandardCopyOption.REPLACE_EXISTING);

		// assign the picture if we have one
		if (pictureStream != null) {
			Path p = path.getParent().resolve(path.getFileName() + ".jpg");
			Files.copy(pictureStream, p, StandardCopyOption.REPLACE_EXISTING);
		}

		// obtain SIMA information if any
		if (path.getFileName().toString().endsWith(".stask")) {
			ParseUtility.parseSimaModel(path, model);
		}

		saveModelData(model, path);
		
		// clear the cache for all users
		rootCache.clear();
	}

	/**
	 * 
	 * @param model
	 *            the model instance
	 * @param path
	 *            path to the model file
	 * @throws IOException
	 */
	private void saveModelData(Model model, Path path) throws IOException {
			    
		// register the XMI resource factory for the .modeldata extension
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
			.put("modeldata", new XMIResourceFactoryImpl());

		// register XMI resource factory for all other extensions
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
			.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());

		 // obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();

		// create a resource
		URI uri = URI.createFileURI(path + ".modeldata");
		Resource resource = resSet.createResource(uri);
		if (resource == null) {
			throw new RuntimeException("Could not create ECore resource for "+uri);
		}
		resource.getContents().add(model);
		
		model.setLastUpdated(LocalDateTime.now().toString());

		// now save the content.
		resource.save(Collections.EMPTY_MAP);
	}

	@Override
	public File getFile(Client user, Path path) throws IOException {
		if (hasViewAccess(user, path)){
			return rootPath.resolve(path).toFile();
		} else {
			return null;
		}
	}

}
