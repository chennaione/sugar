package com.orm.util;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

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
        assertEquals(true, KeyWordUtil.isKeyword("SELECT"));
        assertEquals(true, KeyWordUtil.isKeyword("TRANSACTION"));
        assertEquals(true, KeyWordUtil.isKeyword("MATCH"));
        assertEquals(true, KeyWordUtil.isKeyword("AS"));
        assertEquals(true, KeyWordUtil.isKeyword("NOTNULL"));
        assertEquals(true, KeyWordUtil.isKeyword("NOT"));
        assertEquals(false, KeyWordUtil.isKeyword("PERSONS"));
        assertEquals(false, KeyWordUtil.isKeyword("NAME"));
        assertEquals(false, KeyWordUtil.isKeyword("LOCATION"));
    }

    @Test
    public void testNullKeyword() {
        assertEquals(false, KeyWordUtil.isKeyword(null));
    }
}
