/*******************************************************************************
 * Copyright © 2020 Equinor ASA
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package com.equinor.modelshare;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class WriteXMLMetaFileTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void writeAndReadXMLProperties() throws IOException {
		Properties p = new Properties();
		p.setProperty("owner", "lars");
		p.setProperty("organisation", "Equinor");
		

		File metafile = folder.newFile("test.meta");
		FileOutputStream fos = new FileOutputStream(metafile);
		p.storeToXML(fos, "Meta is cool", "UTF-8");
		fos.close();

		assertTrue(metafile.exists());
		assertTrue(metafile.canRead());

		Properties resultProps = new Properties();
		final FileInputStream in = new FileInputStream(metafile);
		resultProps.loadFromXML(in);
		in.close();

		assertEquals("Equinor", resultProps.get("organisation"));
		assertEquals("lars", resultProps.get("owner"));
	}
}
