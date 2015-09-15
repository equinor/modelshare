package com.statoil.modelshare.security;

import java.io.File;
import java.nio.file.Path;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DotAccessFileTest {
	
	@BeforeClass
	public static void setUp(){
		AccessFileReader.getSharedInstance("test-resources/models/");
	}
	
	@Test
	public void TestReadAccessFile() {
		Path path = AccessFileReader.getSystemRoot();
		File[] listFiles = path.toFile().listFiles();
		Assert.assertEquals(3, listFiles.length);
	}
	
}
