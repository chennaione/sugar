package com.orm;

import com.orm.util.NamingHelper;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class NamingHelperTest {
    @Test
    public void testToSQLNameCaseConversion() throws Exception {
        assertEquals("TESTLOWERCASE", NamingHelper.toSQLNameDefault("testlowercase"));
        assertEquals("TESTUPPERCASE", NamingHelper.toSQLNameDefault("TESTUPPERCASE"));
    }

    @Test
    public void testToSQLNameUnderscore() {
        assertEquals("TEST_UNDERSCORE", NamingHelper.toSQLNameDefault("testUnderscore"));
        assertEquals("AB_CD", NamingHelper.toSQLNameDefault("AbCd"));
        assertEquals("AB_CD", NamingHelper.toSQLNameDefault("ABCd"));
        assertEquals("AB_CD", NamingHelper.toSQLNameDefault("AbCD"));
        assertEquals("SOME_DETAILS_OBJECT", NamingHelper.toSQLNameDefault("SomeDetailsObject"));
    }


}
