package com.orm;

import android.database.Cursor;

import com.orm.app.ClientApp;
import com.orm.dsl.BuildConfig;
import com.orm.model.TestRecord;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

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
        final TestRecord record = new TestRecord();
        record.setName("lalala");

        recordSugarDataSource.insert(
                record,
                new SugarDataSource.SuccessCallback<Long>() {
                    @Override
                    public void onSuccess(Long id) {
                        record.setId(id);
                    }
                },
                new SugarDataSource.ErrorCallback() {
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        Assert.assertNotNull(record.getId());

        recordSugarDataSource.delete(
                record,
                new SugarDataSource.SuccessCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        Assert.assertNotNull(result);
                        Assert.assertEquals(true, result.booleanValue());
                    }
                },
                new SugarDataSource.ErrorCallback() {
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    @Test
    @SuppressWarnings("all")
    public void testInsertAndFindById() {
        final TestRecord record = new TestRecord();
        record.setName("lalala");

        recordSugarDataSource.insert(
                record,
                new SugarDataSource.SuccessCallback<Long>() {
                    @Override
                    public void onSuccess(Long id) {
                        record.setId(id);
                    }
                },
                new SugarDataSource.ErrorCallback() {
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        recordSugarDataSource.findById(
                record.getId(),
                new SugarDataSource.SuccessCallback<TestRecord>() {
                    @Override
                    public void onSuccess(TestRecord result) {
                        Assert.assertEquals(record.getId(), result.getId());
                        Assert.assertEquals(record.getName(), result.getName());
                    }
                },
                new SugarDataSource.ErrorCallback() {
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    @Test
    @SuppressWarnings("all")
    public void testInsertUpdateAndFindById() {
        final TestRecord record = new TestRecord();
        record.setName("lalala");

        recordSugarDataSource.insert(
                record,
                new SugarDataSource.SuccessCallback<Long>() {
                    @Override
                    public void onSuccess(Long id) {
                        record.setId(id);
                    }
                },
                new SugarDataSource.ErrorCallback() {
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        record.setName("fulano");
        recordSugarDataSource.update(
                record,
                new SugarDataSource.SuccessCallback<Long>() {
                    @Override
                    public void onSuccess(Long id) {
                        Assert.assertEquals(record.getId(), id);
                    }
                },
                new SugarDataSource.ErrorCallback() {
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        recordSugarDataSource.findById(
                record.getId(),
                new SugarDataSource.SuccessCallback<TestRecord>() {
                    @Override
                    public void onSuccess(TestRecord result) {
                        Assert.assertEquals(record.getId(), result.getId());
                        Assert.assertEquals("fulano", result.getName());
                    }
                },
                new SugarDataSource.ErrorCallback() {
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    @Test
    @SuppressWarnings("all")
    public void testInsertAndListAll() {
        final TestRecord record = new TestRecord();
        record.setName("lalala");

        recordSugarDataSource.insert(
                record,
                new SugarDataSource.SuccessCallback<Long>() {
                    @Override
                    public void onSuccess(Long id) {
                        record.setId(id);
                    }
                },
                new SugarDataSource.ErrorCallback() {
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        final TestRecord record1 = new TestRecord();
        record1.setName("fulano");

        recordSugarDataSource.insert(
                record1,
                new SugarDataSource.SuccessCallback<Long>() {
                    @Override
                    public void onSuccess(Long id) {
                        record1.setId(id);
                    }
                },
                new SugarDataSource.ErrorCallback() {
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        final TestRecord record2 = new TestRecord();
        record2.setName("mengano");

        recordSugarDataSource.insert(
                record2,
                new SugarDataSource.SuccessCallback<Long>() {
                    @Override
                    public void onSuccess(Long id) {
                        record2.setId(id);
                    }
                },
                new SugarDataSource.ErrorCallback() {
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        recordSugarDataSource.listAll(
                null,
                new SugarDataSource.SuccessCallback<List<TestRecord>>() {
                    @Override
                    public void onSuccess(List<TestRecord> list) {
                        Assert.assertEquals(3, list.size());
                    }
                },
                new SugarDataSource.ErrorCallback() {
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        recordSugarDataSource.deleteAll(
                new SugarDataSource.SuccessCallback<Integer>() {
                    @Override
                    public void onSuccess(Integer count) {
                        Assert.assertEquals(3, count.intValue());
                    }
                },
                new SugarDataSource.ErrorCallback() {
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    @Test
    @SuppressWarnings("all")
    public void testInsertAndCount() {
        final TestRecord record = new TestRecord();
        record.setName("lalala");

        recordSugarDataSource.insert(
                record,
                new SugarDataSource.SuccessCallback<Long>() {
                    @Override
                    public void onSuccess(Long id) {
                        record.setId(id);
                    }
                },
                new SugarDataSource.ErrorCallback() {
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        final TestRecord record1 = new TestRecord();
        record1.setName("fulano");

        recordSugarDataSource.insert(
                record1,
                new SugarDataSource.SuccessCallback<Long>() {
                    @Override
                    public void onSuccess(Long id) {
                        record1.setId(id);
                    }
                },
                new SugarDataSource.ErrorCallback() {
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                }
        );


        final TestRecord record2 = new TestRecord();
        record2.setName("mengano");

        recordSugarDataSource.insert(
                record2,
                new SugarDataSource.SuccessCallback<Long>() {
                    @Override
                    public void onSuccess(Long id) {
                        record2.setId(id);
                    }
                },
                new SugarDataSource.ErrorCallback() {
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        recordSugarDataSource.count(
                new SugarDataSource.SuccessCallback<Long>() {
                    @Override
                    public void onSuccess(Long count) {
                        Assert.assertEquals(3, count.longValue());
                    }
                },
                new SugarDataSource.ErrorCallback() {
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    @Test
    @SuppressWarnings("all")
    public void testInsertAndGetCursor() {
        final TestRecord record = new TestRecord();
        record.setName("lalala");

        recordSugarDataSource.insert(
                record,
                new SugarDataSource.SuccessCallback<Long>() {
                    @Override
                    public void onSuccess(Long id) {
                        record.setId(id);
                    }
                },
                new SugarDataSource.ErrorCallback() {
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        final TestRecord record1 = new TestRecord();
        record1.setName("fulano");

        recordSugarDataSource.insert(
                record1,
                new SugarDataSource.SuccessCallback<Long>() {
                    @Override
                    public void onSuccess(Long id) {
                        record1.setId(id);
                    }
                },
                new SugarDataSource.ErrorCallback() {
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                }
        );


        final TestRecord record2 = new TestRecord();
        record2.setName("mengano");

        recordSugarDataSource.insert(
                record2,
                new SugarDataSource.SuccessCallback<Long>() {
                    @Override
                    public void onSuccess(Long id) {
                        record2.setId(id);
                    }
                },
                new SugarDataSource.ErrorCallback() {
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        recordSugarDataSource.listAll(
                null,
                new SugarDataSource.SuccessCallback<List<TestRecord>>() {
                    @Override
                    public void onSuccess(List<TestRecord> list) {
                        Assert.assertEquals(3, list.size());
                    }
                },
                new SugarDataSource.ErrorCallback() {
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        recordSugarDataSource.query(
                null,
                null,
                null,
                null,
                null,
                new SugarDataSource.SuccessCallback<Cursor>() {
                    @Override
                    public void onSuccess(Cursor cursor) {
                        Assert.assertNotNull(cursor);
                    }
                },
                new SugarDataSource.ErrorCallback() {
                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }
}
