package com.orm.util;

import com.orm.app.ClientApp;
import com.orm.dsl.BuildConfig;
import com.orm.model.TestRecord;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jonatan.salas
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, constants = BuildConfig.class, application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class ReflectionUtilTest {

    @Test
    public void testGetTableFields() {
        List<Field> fieldList = ReflectionUtil.getTableFields(TestRecord.class);
        List<String> strings = new ArrayList<>();

        for (Field field: fieldList) {
            strings.add(field.getName());
        }

        Assert.assertEquals(true, strings.contains("id"));
        Assert.assertEquals(true, strings.contains("name"));
    }

    @Test
    public void testSetFieldValueForId() {
        TestRecord record = new TestRecord();
        record.setName("Bla bla");

        ReflectionUtil.setFieldValueForId(record, 1L);
        Assert.assertEquals(1L, record.getId().longValue());
    }

    @Test
    public void testGetAllClasses() {
        List<Class> classes = ReflectionUtil.getDomainClasses();
        Assert.assertEquals(40, classes.size());
    }
}
