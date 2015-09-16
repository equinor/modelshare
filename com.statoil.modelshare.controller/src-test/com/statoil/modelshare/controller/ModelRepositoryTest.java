package com.statoil.modelshare.controller;

import org.eclipse.emf.common.util.EList;
import org.junit.Test;

import com.statoil.modelshare.Asset;
import com.statoil.modelshare.Folder;
import com.statoil.modelshare.Model;
import com.statoil.modelshare.ModelshareFactory;
import com.statoil.modelshare.User;

public class ModelRepositoryTest {	
	
	@Test
	public void testGetRoot(){
		ModelRepository repo = new ModelRepositoryImpl();
		User user = ModelshareFactory.eINSTANCE.createUser();
		user.setIdentifier("testa");
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
