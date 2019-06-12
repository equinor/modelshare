package com.equinor.modelshare.repository;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.emf.common.util.EList;
import org.junit.BeforeClass;
import org.junit.Test;

import com.equinor.modelshare.Asset;
import com.equinor.modelshare.Folder;
import com.equinor.modelshare.Model;
import com.equinor.modelshare.ModelshareFactory;
import com.equinor.modelshare.User;
import com.equinor.modelshare.security.LocalRepositoryAccessControl;

/**
 * @author Torkild U. Resheim, Itema AS
 */
public class ModelRepositoryTest {	

	private static ModelRepository repo;
	
	@BeforeClass
	public static void setUp(){
		Path rootPath = Paths.get("src/test/resources/repository/").toAbsolutePath();
		repo = new ModelRepositoryImpl(rootPath, null);
		((ModelRepositoryImpl)repo).setRepositoryAccessControl(new LocalRepositoryAccessControl(rootPath));
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
