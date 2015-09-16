package com.statoil.modelshare.app.service;

import com.statoil.modelshare.app.config.RepositoryConfig;

public class ModelFileService {

	private static RepositoryConfig config;

	public static MockModelFile[] getModelFiles() {
		if (config == null) {
			config = new RepositoryConfig();
		}
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
