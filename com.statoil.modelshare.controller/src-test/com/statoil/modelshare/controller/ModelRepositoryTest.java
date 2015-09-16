package com.statoil.modelshare.controller;

import org.eclipse.emf.common.util.EList;
import org.junit.Test;

import com.statoil.modelshare.Asset;
import com.statoil.modelshare.Folder;
import com.statoil.modelshare.Model;

public class ModelRepositoryTest {	
	
	@Test
	public void testGetRoot(){
		Folder root = ModelRepository.getRoot();
		EList<Asset> eContents = root.getAssets();
		for (Asset eObject : eContents) {
			if (eObject instanceof Folder){
//				System.out.println("<dir> "+((Folder) eObject).getName());
			} else if (eObject instanceof Model){
//				System.out.println("      "+((Model) eObject).getName());			
			}			
		}
	}
}
