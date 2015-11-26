package com.statoil.modelshare.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * This utility extracts files and directories of a standard zip file to a
 * destination directory.
 * 
 * @author www.codejava.net
 *
 */
public class UnzipUtility {

	private List<File> unzippedFiles;

	public void unzip(Path zipFilePath, Path destDir) {
		List<File> files = new ArrayList<>();
		File dir = destDir.toFile();
		// create output directory if it doesn't exist
		if (!dir.exists())
			dir.mkdirs();
		FileInputStream fis;
		// buffer for read and write data to file
		byte[] buffer = new byte[1024];
		try {
			fis = new FileInputStream(zipFilePath.toFile());
			ZipInputStream zis = new ZipInputStream(fis);
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				String fileName = ze.getName();
				if (ze.getName().endsWith(".xmi") || ze.getName().equals("meta.properties")) {
					File newFile = new File(destDir + File.separator + fileName);
					// create directories for sub directories in zip
					new File(newFile.getParent()).mkdirs();
					FileOutputStream fos = new FileOutputStream(newFile);
					int len;
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					fos.close();
					// close this ZipEntry
					files.add(newFile);
				}
				zis.closeEntry();
				ze = zis.getNextEntry();
			}
			// close last ZipEntry
			zis.closeEntry();
			zis.close();
			fis.close();
			
			unzippedFiles = files;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<File> getunzippedFiles() {
		return unzippedFiles;
	}

}