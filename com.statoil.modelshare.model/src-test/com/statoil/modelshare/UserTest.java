package com.statoil.modelshare;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {

	@Test
	public void testUser() {
		Client per = ModelshareFactory.eINSTANCE.createClient();
		assertNotNull(per);
	}
}
