package com.orm.util;

import com.orm.model.TestRecord;

import junit.framework.Assert;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @author jonatan.salas
 */
public final class SugarConfigTest {

    @Test
    public void testSetGetFields() {
        Field[] fields = TestRecord.class.getFields();

        List<Field> fieldList = Arrays.asList(fields);
        SugarConfig.setFields(TestRecord.class, fieldList);

        Assert.assertEquals(fieldList, SugarConfig.getFields(TestRecord.class));
    }

    @Test
    public void testClearCache() {
        SugarConfig.clearCache();
        Assert.assertEquals(true, SugarConfig.fields.isEmpty());
    }
}
