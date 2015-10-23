package com.statoil.modelshare.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.emf.common.util.EList;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

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
	public void testLastUpdatedFeature() {
		// Representing date in .meta file
		LocalDateTime now = LocalDateTime.now();
		String storedDate = now.toString();
		
		// Setting date string in model to be delivered to the view
		Model model = ModelshareFactory.eINSTANCE.createModel();
		model.setLastUpdated(storedDate);

		// Converting to date
		String lastUpdated = model.getLastUpdated();
		LocalDateTime ldt = LocalDateTime.parse(lastUpdated);
		
		assertEquals(now, ldt);
	}
	
}
