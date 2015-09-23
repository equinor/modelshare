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
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

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
	public void uploadFile(File sourceFile, Model model) throws FileNotFoundException, IOException {
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

	private Properties setupMetaFileProperties(File sourceFile, Model model) {
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
		p.setProperty("lastUpdated", LocalDateTime.now().toString());
		
		addTaskInformation(sourceFile, p);
		return p;
	}

	private void addTaskInformation(File sourceFile, Properties p) {
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
		String fileName = path.getFileName().toString();
		String metaFileName = path.toFile().getParent() + File.separator + "." + fileName + ".meta";
		try {
			final FileInputStream in = new FileInputStream(metaFileName);
			resultProps.loadFromXML(in);
			in.close();
		} catch (IOException ioe) {
			System.err.println("Error reading meta information from "+ metaFileName);
		}
		
		Model model = ModelshareFactory.eINSTANCE.createModel();
		model.setOwner(resultProps.getProperty("owner"));
		model.setLastUpdated(resultProps.getProperty("lastUpdated"));
		model.setName(resultProps.getProperty("name"));
		model.setOrganisation(resultProps.getProperty("organisation"));
		model.setPath(path.toString());
		model.setUsage(resultProps.getProperty("usage"));

		for (Enumeration<?> e = resultProps.propertyNames(); e.hasMoreElements();) {
			String element = (String) e.nextElement();
			if (element.startsWith("task")) {
				TaskInformation taskInfo = ModelshareFactory.eINSTANCE.createTaskInformation();
				int start = element.indexOf(".");
				int end = element.lastIndexOf(".");
				String taskName = element.substring(start, end-1);
				taskInfo.setName(taskName);
				taskInfo.setDescription(resultProps.getProperty(element));
				model.getTaskInformation().add(taskInfo);
			}
		}
		return model;
	}
	
	/**
	 * Unzip stask and parses the xmi files for task names and descriptions
	 */
	private List<TaskInformation> unzipAndGetStaskInformation(Path path) {
		List<TaskInformation> tasks = new ArrayList<>();
		String tempDir = System.getProperty("java.io.tmpdir");
		UnzipUtility.unzip(path.toString(), tempDir);
		List<File> unzippedFiles = UnzipUtility.getunzippedFiles();
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
		if (rights.contains(Access.READ))
			return new FileInputStream(file);
		else
			throw new AccessDeniedException(path.toString());
	}

}
