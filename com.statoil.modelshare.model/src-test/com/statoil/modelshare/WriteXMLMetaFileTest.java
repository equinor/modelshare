package com.statoil.modelshare;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

public class WriteXMLMetaFileTest {

	@Test
	public void writeAndReadXMLProperties() throws IOException {
		Properties p = new Properties();
		p.setProperty("owner", "lars");
		p.setProperty("organisation", "Statoil");
		
		String tempDir = System.getProperty("java.io.tmpdir");
		
		String metaPathAndName = tempDir+"test.meta";
		FileOutputStream fos = new FileOutputStream(metaPathAndName);
        p.storeToXML(fos, "Meta is cool", "UTF-8");
        fos.close();
        
        File metaFile = new File(metaPathAndName);
        assertTrue(metaFile.exists());
        assertTrue(metaFile.canRead());
        
        Properties resultProps = new Properties();
        final FileInputStream in = new FileInputStream(metaPathAndName);
        resultProps.loadFromXML(in);
        in.close();
        
        assertEquals("Statoil", resultProps.get("organisation"));
        assertEquals("lars", resultProps.get("owner"));
	}
}
