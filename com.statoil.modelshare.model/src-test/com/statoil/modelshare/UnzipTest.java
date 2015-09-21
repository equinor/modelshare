package com.statoil.modelshare;

import java.io.File;
import java.nio.file.Paths;

import org.junit.Test;

import com.statoil.modelshare.util.UnzipUtility;

public class UnzipTest {

	@Test
	public void testUnzip() {
		File zipFile = Paths.get("test-resources/itema.stask").toAbsolutePath().toFile();
        String destDirectory = "/Users/robert/Temp";
        UnzipUtility unzipper = new UnzipUtility();
        try {
            unzipper.unzip(zipFile.getAbsolutePath(), destDirectory);
        } catch (Exception ex) {
            // some errors occurred
            ex.printStackTrace();
        }
	}
}
