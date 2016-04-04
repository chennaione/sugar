package com.orm.util;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author jonatan.salas
 */
public class KeyWordUtilTest {

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
}
