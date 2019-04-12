package com.orm.util;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author jonatan.salas
 */
public final class KeyWordUtilTest {

	@Test(expected = IllegalAccessException.class)
	public void testPrivateConstructor() throws Exception {
		KeyWordUtil keyWordUtil = KeyWordUtil.class.getDeclaredConstructor().newInstance();
		assertNull(keyWordUtil);
	}

	@Test
	public void testKeyWord() {
		assertTrue(KeyWordUtil.isKeyword("SELECT"));
		assertTrue(KeyWordUtil.isKeyword("TRANSACTION"));
		assertTrue(KeyWordUtil.isKeyword("MATCH"));
		assertTrue(KeyWordUtil.isKeyword("AS"));
		assertTrue(KeyWordUtil.isKeyword("NOTNULL"));
		assertTrue(KeyWordUtil.isKeyword("NOT"));
		assertFalse(KeyWordUtil.isKeyword("PERSONS"));
		assertFalse(KeyWordUtil.isKeyword("NAME"));
		assertFalse(KeyWordUtil.isKeyword("LOCATION"));
	}

	@Test
	public void testNullKeyword() {
		assertFalse(KeyWordUtil.isKeyword(null));
	}
}
