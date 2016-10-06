package com.orm;

import android.database.Cursor;

import com.orm.app.ClientApp;
import com.orm.dsl.BuildConfig;
import com.orm.model.TestRecord;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;

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

        assertNotNull(record.getId());

        recordSugarDataSource.delete(
                record,
                new SugarDataSource.SuccessCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        assertNotNull(result);
                        assertEquals(true, result.booleanValue());
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
                        assertEquals(record.getId(), result.getId());
                        assertEquals(record.getName(), result.getName());
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
                        assertEquals(record.getId(), id);
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
                        assertEquals(record.getId(), result.getId());
                        assertEquals("fulano", result.getName());
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
                        assertEquals(3, list.size());
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
                        assertEquals(3, count.intValue());
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
                        assertEquals(3, count.longValue());
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
                        assertEquals(3, list.size());
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
                        assertNotNull(cursor);
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
    public void bulkInsertAndListAllTest() {
        final TestRecord record = new TestRecord();
        record.setName("lalala");

        final TestRecord record1 = new TestRecord();
        record1.setName("fulano");

        final TestRecord record2 = new TestRecord();
        record2.setName("mengano");

        final List<TestRecord> list = new ArrayList<>();
        list.add(record);
        list.add(record1);
        list.add(record2);

        recordSugarDataSource.bulkInsert(
                list,
                new SugarDataSource.SuccessCallback<List<Long>>() {
                    @Override
                    public void onSuccess(List<Long> ids) {
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setId(ids.get(i));
                        }
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
                    public void onSuccess(List<TestRecord> testRecords) {
                        for (int i = 0; i < list.size(); i++) {
                            TestRecord record1 = list.get(i);
                            TestRecord record2 = testRecords.get(i);

                            assertEquals(record1.getId(), record2.getId());
                            assertEquals(record1.getName(), record2.getName());
                        }
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
    public void nullFindById() {
        TestRecord record = new TestRecord();
        record.setId(0L);

        recordSugarDataSource.findById(
                record.getId(),
                new SugarDataSource.SuccessCallback<TestRecord>() {
                    @Override
                    public void onSuccess(TestRecord object) {
                        assertNull(object);
                    }
                },
                new SugarDataSource.ErrorCallback() {
                    @Override
                    public void onError(Exception e) {
                        assertNotNull(e.getMessage());
                    }
                }
        );
    }

    @Test
    public void testNullListAll() {
        recordSugarDataSource.listAll(
                null,
                new SugarDataSource.SuccessCallback<List<TestRecord>>() {
                    @Override
                    public void onSuccess(List<TestRecord> object) {
                        assertNull(object);
                    }
                },
                new SugarDataSource.ErrorCallback() {
                    @Override
                    public void onError(Exception e) {
                        assertNotNull(e.getMessage());
                    }
                }
        );
    }

    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("all")
    public void testNullConstructor() {
        SugarDataSource<TestRecord> dataSource = SugarDataSource.getInstance(null);
    }

    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("all")
    public void testCheckNotNull() {
        TestRecord record = null;
        recordSugarDataSource.checkNotNull(record);
    }
}
