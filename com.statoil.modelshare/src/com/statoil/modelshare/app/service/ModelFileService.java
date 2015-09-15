package com.statoil.modelshare.app.service;

public class ModelFileService {

	public static MockModelFile[] getModelFiles() {
		return createFileTree();
	}

	private static MockModelFile[] createFileTree() {
		MockModelFile[] files = new MockModelFile[3];
		files[0] = new MockModelFile(1, "Njord", "This is the description");
		files[1] = new MockModelFile(2, "Åsgard A", "This is the description");
		files[2] = new MockModelFile(3, "Åsgard B", "This is the description");
	return files;
	}
	
}
