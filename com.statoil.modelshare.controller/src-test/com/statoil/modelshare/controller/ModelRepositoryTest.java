package com.statoil.modelshare.controller;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.emf.common.util.EList;
import org.junit.BeforeClass;
import org.junit.Test;

import com.statoil.modelshare.Asset;
import com.statoil.modelshare.Folder;
import com.statoil.modelshare.Model;
import com.statoil.modelshare.ModelshareFactory;
import com.statoil.modelshare.User;

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
		User user = ModelshareFactory.eINSTANCE.createUser();
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
}
