package com.statoil.modelshare.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.emf.common.util.EList;
import org.junit.BeforeClass;
import org.junit.Test;

import com.statoil.modelshare.Asset;
import com.statoil.modelshare.Client;
import com.statoil.modelshare.Folder;
import com.statoil.modelshare.Model;
import com.statoil.modelshare.ModelshareFactory;

/**
 * @author Torkild U. Resheim, Itema AS
 */
public class ModelRepositoryTest {	

	private static final Path rootPath = Paths.get("test-resources/models/").toAbsolutePath();
	private static ModelRepository repo;
	
	@BeforeClass
	public static void setUp(){
		repo = new ModelRepositoryImpl(rootPath);		
	}
	
	@Test
	public void testGetRoot(){
		Client user = ModelshareFactory.eINSTANCE.createClient();
		user.setIdentifier("administrators");
		Folder root = repo.getRoot(user);
		EList<Asset> eContents = root.getAssets();
		for (Asset eObject : eContents) {
			if (eObject instanceof Folder){
				System.out.println("<dir> "+((Folder) eObject).getName());
			} else if (eObject instanceof Model){
				System.out.println("      "+((Model) eObject).getName());			
			}			
		}
	}
	
	@Test
	public void testUploadingFile() {
		// Set up a model repository
		String home = System.getProperty("user.home");
		assertNotNull(home);
		File homeDir = new File(home);
		assertTrue(homeDir.exists());
		String root = homeDir + File.separator + "modelshare" + File.separator + "repository";
		Path rootPath = new File(root).toPath();
		ModelRepository repo = new ModelRepositoryImpl(rootPath);
		
		// Create a folder as user "test" 
		Client client = repo.getUser("test");
		repo.createFolder(repo.getRoot(client), "TestUpload");
		
		String testUpload = rootPath + File.separator + "TestUpload";
		File testDir = new File(testUpload);
		
		// Create a model representation of the file
		File file = Paths.get("test-resources/itema.stask").toAbsolutePath().toFile();
		Model model = ModelshareFactory.eINSTANCE.createModel();
		model.setOwner("lars");
		model.setOrganisation("Statoil");
		model.setUsage("Bla bla bla...");
		model.setName(file.getName());
		model.setPath(testDir.getAbsolutePath() + File.separator + "itema.stask");
		
		// Upload the file
		repo.uploadFile(file, model);
		
		// Make sure it actually exists
		File staskFile = new File(testDir, "itema.stask");
		assertNotNull(staskFile);
	}
}
