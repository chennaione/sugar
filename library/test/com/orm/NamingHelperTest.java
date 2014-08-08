package com.orm;

import com.orm.util.NamingHelper;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class NamingHelperTest {
    @Test
    public void testToSQLNameCaseConversion() throws Exception {
        assertToSqlNameEquals("TESTLOWERCASE", "testlowercase");
        assertToSqlNameEquals("TESTUPPERCASE", "TESTUPPERCASE");
    }

    @Test
    public void testToSQLNameUnderscore() {
        assertToSqlNameEquals("TEST_UNDERSCORE", "testUnderscore");
        assertToSqlNameEquals("AB_CD", "AbCd");
        assertToSqlNameEquals("AB_CD", "ABCd");
        assertToSqlNameEquals("AB_CD", "AbCD");
        assertToSqlNameEquals("SOME_DETAILS_OBJECT", "SomeDetailsObject");
        assertToSqlNameEquals("H_OL_A","hOlA");
        assertToSqlNameEquals("A","a");
    }

    /**
     * helper method that asserts the a CamelCase string is converted to UPPER_CASE_UNDER_SCORE correctly
     * @param expected a camel cased string
     * @param actual the expected UPPER_CASE_UNDER_SCORE string
     */
    private static void assertToSqlNameEquals(String expected, String actual){
        assertEquals(expected, NamingHelper.toSQLNameDefault(actual));
    }

}
