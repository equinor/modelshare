package com.statoil.modelshare;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {

	@Test
	public void testUser() {
		User per = ModelshareFactory.eINSTANCE.createUser();
		assertNotNull(per);
	}
}
