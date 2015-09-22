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
		String home = System.getProperty("user.home");
		
		assertNotNull(home);
		File homeDir = new File(home);
		assertTrue(homeDir.exists());
		String root = homeDir + File.separator + "modelshare" + File.separator + "repository";
		File rootFile = new File(root);
		Path rootPath = rootFile.toPath();
		
		ModelRepository repo = new ModelRepositoryImpl(rootPath);
		
		Client client = repo.getUser("test");
		
		repo.createFolder(repo.getRoot(client), "TestUpload");
		
		String owner = "lars";
		String org = "Statoil";
		String usage = "Bla bla bla...";
		
		File file = Paths.get("test-resources/itema.stask").toAbsolutePath().toFile();
		
		String testUpload = rootPath + File.separator + "TestUpload";
		File testDir = new File(testUpload);
		repo.uploadFile(testDir.toPath(), file, owner, org, usage);
		
		File staskFile = new File(testDir, "itema.stask");
		assertNotNull(staskFile);
	}
}
