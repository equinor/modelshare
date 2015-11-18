package com.statoil.modelshare.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.statoil.modelshare.Access;
import com.statoil.modelshare.Account;
import com.statoil.modelshare.Client;
import com.statoil.modelshare.Folder;
import com.statoil.modelshare.Model;
import com.statoil.modelshare.ModelshareFactory;
import com.statoil.modelshare.TaskInformation;
import com.statoil.modelshare.security.RepositoryAccessControl;
import com.statoil.modelshare.util.ParseUtility;
import com.statoil.modelshare.util.UnzipUtility;

/**
 * @author Torkild U. Resheim, Itema AS
 */
public class ModelRepositoryImpl implements ModelRepository {

	static Log log = LogFactory.getLog(ModelRepository.class.getName());
	
	static Log downloadLog = LogFactory.getLog("downloadLogger");

	private Path rootPath;

	private Path userRoot;
	private RepositoryAccessControl ra;

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
	}

	@Override
	public void createFolder(Client user, Folder parentFolder, String name) throws IOException {
		EnumSet<Access> rights = ra.getRights(Paths.get(parentFolder.getPath()), user);
		if (!rights.contains(Access.WRITE)) {
			throw new AccessDeniedException(parentFolder.toString());
		}
		File dir = new File(parentFolder.getPath(), name);
		dir.mkdir();
	}

	private void fillFolderContents(Folder folder, Client user) throws IOException {
		File file = new File(folder.getPath());
		if (!file.exists()) {
			return;
		}

		// list all files except those that are hidden
		File[] listFiles = file.listFiles((FilenameFilter) (dir, name) -> {
			return (!name.startsWith("."));
		});

		// recurse into subfolders and add files
		for (File child : listFiles) {
			// ignore those where the user have no access
			if (hasViewAccess(user, child.toPath())) {
				if (child.isDirectory()) {
					Folder newFolder = ModelshareFactory.eINSTANCE.createFolder();
					newFolder.setName(child.getName());
					newFolder.setPath(child.getAbsolutePath());
					folder.getAssets().add(newFolder);
					fillFolderContents(newFolder, user);
				} else {
					Model newFile = getMetaInformation(child.toPath());
					folder.getAssets().add(newFile);
				}
			}
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
	public Model getMetaInformation(Path path) {
		Properties resultProps = new Properties();
		Path resolvedPath = rootPath.resolve(path);
		String fileName = resolvedPath.getFileName().toString();
		String metaFileName = resolvedPath.toFile().getParent() + File.separator + "." + fileName + ".meta";

		try {
			final FileInputStream in = new FileInputStream(metaFileName);
			resultProps.loadFromXML(in);
			in.close();
		} catch (IOException ioe) {
			log.error("Error reading meta information from " + metaFileName);
		}

		Model model = ModelshareFactory.eINSTANCE.createModel();
		model.setOwner(resultProps.getProperty("owner"));
		model.setLastUpdated(resultProps.getProperty("lastUpdated"));
		model.setName(resultProps.getProperty("name"));
		model.setOrganisation(resultProps.getProperty("organisation"));
		model.setPath(path.toString());
		model.setUsage(resultProps.getProperty("usage"));
		model.setMail(resultProps.getProperty("mail"));

		for (Enumeration<?> e = resultProps.propertyNames(); e.hasMoreElements();) {
			String element = (String) e.nextElement();
			if (element.startsWith("task")) {
				TaskInformation taskInfo = ModelshareFactory.eINSTANCE.createTaskInformation();
				int start = element.indexOf(".");
				int end = element.lastIndexOf(".");
				String taskName = element.substring(start + 1, end);
				taskInfo.setName(taskName);
				taskInfo.setDescription(resultProps.getProperty(element));
				model.getTaskInformation().add(taskInfo);
			}
		}
		return model;
	}

	public Folder getRoot(Client user) {
		Folder root = ModelshareFactory.eINSTANCE.createFolder();
		root.setPath(rootPath.toString());
		root.setName("");
		try {
			fillFolderContents(root, user);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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
		if (localUser == null || localUser.isEmpty()) {
			throw new AccessDeniedException(null, null, "Cannot copy file. User does not have a local account");
		}
		EnumSet<Access> rights = ra.getRights(path, user);
		if (rights.contains(Access.READ)) {
			Path target = userRoot.resolve(Paths.get(localUser, "Documents", "Model-export",path.getFileName().toString()));
			target.toFile().mkdirs();
			Files.copy(rootPath.resolve(path), target, StandardCopyOption.REPLACE_EXISTING);
			Object[] messageArgs = { path, user.getIdentifier() };
			downloadLog.info(MessageFormat.format("Model \"{0}\" was downloaded by {1}", messageArgs));
			return "Model copied to "+target.toString();
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
	/**
	 * Unzip stask and parses the xmi files for task names and descriptions
	 * 
	 * @throws IOException if the SIMA workspace file could not be read
	 */
	private List<TaskInformation> unzipAndGetStaskInformation(Path path) throws IOException {
		try {
			List<TaskInformation> tasks = new ArrayList<>();
			Path tempPath = Files.createTempDirectory("modelshare");
			UnzipUtility unzipper = new UnzipUtility();
			unzipper.unzip(path, tempPath);
			List<File> unzippedFiles = unzipper.getunzippedFiles();
			for (int i = 0; i < unzippedFiles.size(); i++) {
				File f = unzippedFiles.get(i);
				if (!f.getName().equals("folders.xmi")){
					try {
						TaskInformation taskInfo = ParseUtility.parseStaskXMI(f);
						tasks.add(taskInfo);
					} catch (Exception e) {
						log.fatal("Could not parse SIMA task information in file \""+f.getName()+"\"", e);
					}
				}
			}
			return tasks;
		} catch (Exception e) {
			throw new IOException("Could not parse SIMA workspace file", e);
		}
	}

	@Override
	public void uploadModel(Client user, InputStream modelStream, Model model)
			throws IOException, AccessDeniedException {
		// assert that the user has write access
		Path path = Paths.get(model.getPath());
		EnumSet<Access> rights = ra.getRights(path, user);
		if (!rights.contains(Access.WRITE)) {
			throw new AccessDeniedException(path.toString());
		}
				
		// do the actual write in one operation
		Files.copy(modelStream, path, StandardCopyOption.REPLACE_EXISTING);

		// obtain required metadata (TODO: Use EMF serialization) 
		Properties p = new Properties();
		p.setProperty("owner", model.getOwner());
		p.setProperty("organisation", model.getOrganisation());
		p.setProperty("name", model.getName());
		p.setProperty("usage", model.getUsage());
		p.setProperty("mail", model.getMail());
		p.setProperty("lastUpdated", LocalDateTime.now().toString());
		
		// obtain SIMA information if any
		if (path.getFileName().toString().endsWith(".stask")){
			List<TaskInformation> tasks = unzipAndGetStaskInformation(path);
			for (TaskInformation taskInfo : tasks) {
				p.setProperty("task." + taskInfo.getName() + ".description", taskInfo.getDescription());
			}
		}
		
		// write the metadata
		Path metaData = Paths.get(path.getParent().toString(),"."+path.getFileName()+".meta");
		try (FileOutputStream fos = new FileOutputStream(metaData.toFile())) {
			p.storeToXML(fos, "Statoil model meta properties file", "UTF-8");
			fos.close();
		} catch (Exception e) {
			log.error("Could not write metadata file", e);
		}
		
	}

}
