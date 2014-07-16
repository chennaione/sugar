package com.orm;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class StringUtilTest {
    @Test
    public void testToSQLNameCaseConversion() throws Exception {
        assertEquals("TESTLOWERCASE", StringUtil.toSQLNameDefault("testlowercase"));
        assertEquals("TESTUPPERCASE", StringUtil.toSQLNameDefault("TESTUPPERCASE"));
    }

    @Test
    public void testToSQLNameUnderscore(){
        assertEquals("TEST_UNDERSCORE", StringUtil.toSQLNameDefault("testUnderscore"));
        assertEquals("AB_CD", StringUtil.toSQLNameDefault("AbCd"));
        assertEquals("AB_CD", StringUtil.toSQLNameDefault("ABCd"));
        assertEquals("AB_CD", StringUtil.toSQLNameDefault("AbCD"));
        assertEquals("SOME_DETAILS_OBJECT", StringUtil.toSQLNameDefault("SomeDetailsObject"));
    }


}
