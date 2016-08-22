package com.orm.helper;

import com.orm.app.ClientApp;
import com.orm.dsl.BuildConfig;
import com.orm.model.TestRecord;
import com.orm.util.ReflectionUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.orm.helper.NamingHelper.*;
import static junit.framework.Assert.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, constants = BuildConfig.class, application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class NamingHelperTest {

    @Test(expected = IllegalAccessException.class)
    public void testPrivateConstructor() throws Exception {
        NamingHelper helper = NamingHelper.class.getDeclaredConstructor().newInstance();
        assertNull(helper);
    }

    @Test
    public void testToSQLNameFromField() {
        List<Field> fieldList = ReflectionUtil.getTableFields(TestRecord.class);

        if (null != fieldList && !fieldList.isEmpty()) {
            List<String> columnList = new ArrayList<>();

            for(Field field: fieldList) {
                columnList.add(toColumnName(field));
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
        assertEquals("TEST_RECORD", toTableName(TestRecord.class));
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
        assertEquals(expected, toSQLNameDefault(actual));
    }

}
