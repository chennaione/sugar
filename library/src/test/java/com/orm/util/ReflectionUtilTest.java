package com.orm.util;

import com.orm.app.ClientApp;
import com.orm.dsl.BuildConfig;
import com.orm.model.TestRecord;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author jonatan.salas
 */
public final class ReflectionUtilTest {

    @Test
    public void testGetTableFields() {
        List<Field> fieldList = ReflectionUtil.getTableFields(TestRecord.class);
        List<String> strings = new ArrayList<>();

        for (Field field: fieldList) {
            strings.add(field.getName());
        }

        assertEquals(true, strings.contains("id"));
        assertEquals(true, strings.contains("name"));
    }
}
