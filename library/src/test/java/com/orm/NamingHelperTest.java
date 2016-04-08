package com.orm;

import com.orm.helper.NamingHelper;
import com.orm.models.TestRecord;
import com.orm.util.ReflectionUtil;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class NamingHelperTest {

    @Test
    public void testToSQLNameFromField() {
        List<Field> fieldList = ReflectionUtil.getTableFields(TestRecord.class);

        if (null != fieldList && !fieldList.isEmpty()) {
            List<String> columnList = new ArrayList<>();

            for(Field field: fieldList) {
                columnList.add(NamingHelper.toSQLName(field));
            }

            boolean isIdInList = inList(columnList, "ID");
            boolean isNameInList = inList(columnList, "NAME");

            assertTrue(isIdInList);
            assertTrue(isNameInList);
        }
    }

    private boolean inList(List<String> list, String searchValue) {
        for (String val: list) {
            if (val.equals(searchValue)) {
                return true;
            }
        }

        return false;
    }

    @Test
    public void testToSQLNameFromClass() {
        assertEquals("TEST_RECORD", NamingHelper.toSQLName(TestRecord.class));
    }

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
     * Helper method that asserts a CamelCaseString is converted to UPPER_CASE_UNDER_SCORE.
     *
     * @param expected  a CamelCaseString
     * @param actual    the expected UPPER_CASE_UNDER_SCORE string
     */
    private static void assertToSqlNameEquals(String expected, String actual) {
        assertEquals(expected, NamingHelper.toSQLNameDefault(actual));
    }

}
