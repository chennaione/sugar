package com.orm.util;

import com.orm.model.TestRecord;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author jonatan.salas
 */
public final class ReflectionUtilTest {

//    @Test
//    public void testGetTableFields() {
//        List<Field> fieldList = ReflectionUtil.getTableFields(TestRecord.class);
//        List<String> strings = new ArrayList<>();
//
//        for (Field field: fieldList) {
//            strings.add(field.getName());
//        }
//
//        assertEquals(true, strings.contains("id"));
//        assertEquals(true, strings.contains("name"));
//    }

    @Test
    public void testSetFieldValueForId() {
        TestRecord record = new TestRecord();
        record.setName("Bla bla");

        ReflectionUtil.setFieldValueForId(record, 1L);
        Assert.assertEquals(1L, record.getId().longValue());
    }

}
