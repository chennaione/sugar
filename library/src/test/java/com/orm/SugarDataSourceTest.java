package com.orm;

import com.orm.app.ClientApp;
import com.orm.dsl.BuildConfig;
import com.orm.model.TestRecord;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * @author jonatan.salas
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, constants = BuildConfig.class, application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class SugarDataSourceTest {
    private SugarDataSource<TestRecord> recordSugarDataSource;

    @Before
    public void setUp() {
        recordSugarDataSource = SugarDataSource.getInstance(TestRecord.class);
    }

    @Test
    @SuppressWarnings("all")
    public void testInsertAndDelete() {
        TestRecord record = new TestRecord();
        record.setName("lalala");

        recordSugarDataSource.insert(
                record,
                id -> {
                    record.setId(id);
                },
                e -> {
                    e.printStackTrace();
                }
        );

        Assert.assertNotNull(record.getId());

        recordSugarDataSource.delete(
                record,
                result -> {
                    Assert.assertNotNull(result);
                    Assert.assertEquals(true, result.booleanValue());
                },
                e -> {
                    e.printStackTrace();
                }
        );
    }

    @Test
    @SuppressWarnings("all")
    public void testInsertAndFindById() {
        TestRecord record = new TestRecord();
        record.setName("lalala");

        recordSugarDataSource.insert(
                record,
                id -> {
                    record.setId(id);
                },
                e -> {
                    e.printStackTrace();
                }
        );

        recordSugarDataSource.findById(
                record.getId(),
                result -> {
                    Assert.assertEquals(record.getId(), result.getId());
                    Assert.assertEquals(record.getName(), result.getName());
                },
                e -> {
                    e.printStackTrace();
                }
        );
    }

    @Test
    @SuppressWarnings("all")
    public void testInsertUpdateAndFindById() {
        TestRecord record = new TestRecord();
        record.setName("lalala");

        recordSugarDataSource.insert(
                record,
                id -> {
                    record.setId(id);
                },
                e -> {
                    e.printStackTrace();
                }
        );

        record.setName("fulano");
        recordSugarDataSource.insert(
                record,
                id -> {
                    Assert.assertEquals(record.getId(), id);
                },
                e -> {
                    e.printStackTrace();
                }
        );

        recordSugarDataSource.findById(
                record.getId(),
                result -> {
                    Assert.assertEquals(record.getId(), result.getId());
                    Assert.assertEquals("fulano", result.getName());
                },
                e -> {
                    e.printStackTrace();
                }
        );
    }
}
