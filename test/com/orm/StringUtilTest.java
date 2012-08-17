package com.orm;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class StringUtilTest {
    @Test
    public void testToSQLNameCaseConversion() throws Exception {
        assertEquals("TESTLOWERCASE", StringUtil.toSQLName("testlowercase"));
        assertEquals("TESTUPPERCASE", StringUtil.toSQLName("TESTUPPERCASE"));
    }

    @Test
    public void testToSQLNameUnderscore(){
        assertEquals("TEST_UNDERSCORE", StringUtil.toSQLName("testUnderscore"));
        assertEquals("AB_CD", StringUtil.toSQLName("AbCd"));
        assertEquals("AB_CD", StringUtil.toSQLName("ABCd"));
        assertEquals("AB_CD", StringUtil.toSQLName("AbCD"));
        assertEquals("SOME_DETAILS_OBJECT", StringUtil.toSQLName("SomeDetailsObject"));
    }


}
