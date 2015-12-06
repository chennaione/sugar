package com.orm.util;

import com.orm.query.TestRecord;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Nursultan Turdaliev on 12/6/15.
 */
public class SugarConfigTest {

    @Test
    public void testSetFields() throws Exception {

        assertNull("SugarConfig: testing non existent key", SugarConfig.getFields(TestRecord.class));

        ReflectionUtil.getTableFields(TestRecord.class);

        assertTrue("SugarConfig: testing existing key on SugarConfig", SugarConfig.fields.containsKey(TestRecord.class));
        assertFalse("SugarConfig: testing non existent key ", SugarConfig.fields.containsKey(Field.class));

        SugarConfig.clearCache();
        assertNull("SugarConfig: testing non existent key", SugarConfig.getFields(TestRecord.class));

    }

    @Test
    public void testGetFields() throws Exception {

    }
}