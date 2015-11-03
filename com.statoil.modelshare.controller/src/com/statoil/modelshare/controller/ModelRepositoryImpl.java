package com.statoil.modelshare.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

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

	private Path rootPath;
	
	private RepositoryAccessControl ra;
	
	static Log log = LogFactory.getLog(ModelRepository.class.getName());

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
	public ModelRepositoryImpl(Path path) {
		rootPath = path.toAbsolutePath();
		ra = new RepositoryAccessControl(rootPath);
		System.out.println("Using root folder at " + rootPath);
	}

	private void fillFolderContents(Folder folder, Client user) throws IOException {
		File file = new File(folder.getPath());
		if (!file.exists()) {
			return;
		}

		// List all files except those that are hidden
		File[] listFiles = file.listFiles((FilenameFilter) (dir, name) -> {
			return (!name.startsWith("."));
		});

		// Recurse into subfolders and add files
		for (File child : listFiles) {
			// Ignore those where the user have no access
			if (hasDisplayAccess(user, child.toPath())) {
				if (child.isDirectory()) {
					Folder newFolder = ModelshareFactory.eINSTANCE.createFolder();
					newFolder.setName(child.getName());
					newFolder.setPath(child.getAbsolutePath());
					folder.getAssets().add(newFolder);
					fillFolderContents(newFolder, user);
				} else {
					Model newFile = ModelshareFactory.eINSTANCE.createModel();
					newFile.setPath(child.getAbsolutePath());
					newFile.setName(child.getName());
					folder.getAssets().add(newFile);
				}
			}
		}
	}

	public boolean hasDisplayAccess(Client user, Path path) throws IOException {
		EnumSet<Access> rights = ra.getRights(path, user);
		return ((rights.contains(Access.READ) || rights.contains(Access.WRITE) || rights.contains(Access.VIEW)));
	}

	public boolean hasReadAccess(Client user, Path path) throws IOException {
		EnumSet<Access> rights = ra.getRights(path, user);
		return ((rights.contains(Access.READ) || rights.contains(Access.WRITE)));
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
	public void createFolder(Folder parentFolder, String name) {
		File parentDir = new File(parentFolder.getPath());
		File childDir = new File(parentDir + File.separator + name);
		childDir.mkdir();
	}

	@Override
	public void deleteFolder(Folder folder) {
		File f = new File(folder.getPath());
		f.delete();
	}

	@Override
	public void uploadFile(File sourceFile, Model model) throws IOException, ParserConfigurationException, SAXException {
		if (!sourceFile.exists()) {
			System.err.println("File not found " + sourceFile.getAbsolutePath());
		}
		File destDir = new File(model.getPath());
		if (!destDir.isDirectory()) {
			destDir = destDir.getParentFile();
		}
		File destFile = new File(destDir, sourceFile.getName());
		if (!destFile.exists()) {
			try {
				destFile.createNewFile();
				System.out.println("Destination file doesn't exist. Creating one!");
			} catch (IOException e) {
				System.err.println("Error creating new file in Modelshare respositury " + destFile.getName());
			}
		}
		
		try (FileInputStream sourceStream = new FileInputStream(sourceFile);
			FileOutputStream destStream = new FileOutputStream(destFile)	
		) {
			FileChannel source = sourceStream.getChannel();
			FileChannel destination = destStream.getChannel();
			if (destination != null && source != null) {
				destination.transferFrom(source, 0, source.size());
			}
		}
		
		Properties p = setupMetaFileProperties(sourceFile, model);

		String metaPath = destDir + File.separator + "." + destFile.getName() + ".meta";
		writeMetaFile(metaPath, p);
	}

	private Properties setupMetaFileProperties(File sourceFile, Model model) throws ParserConfigurationException, SAXException, IOException {
		Properties p = new Properties();
		String owner = "unknown";
		if (model.getOwner() != null) {
			owner = model.getOwner();
		}
		p.setProperty("owner", owner);
		
		String org = "unknown";
		if (model.getOrganisation() != null) {
			org = model.getOrganisation();
		}
		p.setProperty("organisation", org);
		
		String name = "unknown";
		if (model.getName() != null) {
			name = model.getName();
		}
		p.setProperty("name", name);
		
		String usage = "unknown";
		if (model.getUsage() != null) {
			usage = model.getUsage();
		}
		p.setProperty("usage", usage);
		
		String email = "Unknown";
		if (model.getMail() != null) {
			email = model.getMail();
		}
		p.setProperty("mail", email);
		p.setProperty("lastUpdated", LocalDateTime.now().toString());
		
		addTaskInformation(sourceFile, p);
		return p;
	}

	private void addTaskInformation(File sourceFile, Properties p) throws ParserConfigurationException, SAXException, IOException {
		if (sourceFile.getName().endsWith(".stask")) {
			List<TaskInformation> tasks = unzipAndGetStaskInformation(sourceFile.toPath());
			for (TaskInformation taskInfo : tasks) {
				p.setProperty("task."+taskInfo.getName()+".description", taskInfo.getDescription());
			}
		}
	}
	
	private void writeMetaFile(String metaPath, Properties properties) {
		try {
			File metaFile = new File(metaPath);
			metaFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(metaFile);
			properties.storeToXML(fos, "Statoil model meta properties file", "UTF-8");
			fos.close();
		} catch (FileNotFoundException fe) {
			System.err.println("Error writing to " + metaPath);
		} catch (IOException ioe) {
			System.err.println("Error trying to write XML properties to " + metaPath);
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
			log.error("Error reading meta information from "+ metaFileName);
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
	
	/**
	 * Unzip stask and parses the xmi files for task names and descriptions
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	private List<TaskInformation> unzipAndGetStaskInformation(Path path) throws ParserConfigurationException, SAXException, IOException {
		List<TaskInformation> tasks = new ArrayList<>();
		Path tempPath = Files.createTempDirectory("modelshare");
		UnzipUtility unzipper = new UnzipUtility();
		unzipper.unzip(path, tempPath);
		List<File> unzippedFiles = unzipper.getunzippedFiles();
		for (int i = 0; i < unzippedFiles.size(); i++) {
			File unzippedFile = unzippedFiles.get(i);
			TaskInformation taskInfo = ParseUtility.parseStaskXMI(unzippedFile);
			tasks.add(taskInfo);
		}
		return tasks;
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
	public void setPassword(String name, String hash) {
		ra.setPassword(name, hash);
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

	@Override
	public InputStream getFileStream(Client user, Path path) throws IOException {
		File file = rootPath.resolve(path).toFile();
		EnumSet<Access> rights = ra.getRights(path, user);
		
		// TODO: Log Date, time, user and path + rights
		
		if (rights.contains(Access.READ))
			return new FileInputStream(file);
		else
			throw new AccessDeniedException(path.toString());
	}

	@Override
	public void setDownloadRights(Client user, Path path) throws IOException {
		ra.setDownloadRights(path, user);
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

}
