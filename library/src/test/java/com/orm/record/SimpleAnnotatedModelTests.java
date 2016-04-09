package com.orm.record;

import com.orm.app.ClientApp;
import com.orm.dsl.BuildConfig;
import com.orm.helper.NamingHelper;
import com.orm.model.SimpleAnnotatedModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static com.orm.SugarRecord.save;
import static com.orm.SugarRecord.count;
import static com.orm.SugarRecord.deleteAll;
import static com.orm.SugarRecord.delete;
import static com.orm.SugarRecord.deleteInTx;
import static com.orm.SugarRecord.listAll;
import static com.orm.SugarRecord.findById;
import static com.orm.SugarRecord.saveInTx;
import static com.orm.SugarRecord.find;
import static com.orm.SugarRecord.findAsIterator;
import static com.orm.SugarRecord.findWithQuery;
import static com.orm.SugarRecord.findAll;
import static com.orm.SugarRecord.findWithQueryAsIterator;
import static com.orm.SugarRecord.executeQuery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, constants = BuildConfig.class, application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class SimpleAnnotatedModelTests {

    @Test
    public void emptyDatabaseTest() throws Exception {
        assertEquals(0L, count(SimpleAnnotatedModel.class));
    }

    @Test
    public void oneSaveTest() throws Exception {
        save(new SimpleAnnotatedModel());
        assertEquals(1L, count(SimpleAnnotatedModel.class));
    }

    @Test
    public void twoSaveTest() throws Exception {
        save(new SimpleAnnotatedModel());
        save(new SimpleAnnotatedModel());
        assertEquals(2L, count(SimpleAnnotatedModel.class));
    }

    @Test
    public void manySaveTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            save(new SimpleAnnotatedModel());
        }
        assertEquals(100L, count(SimpleAnnotatedModel.class));
    }

    @Test
    public void defaultIdTest() throws Exception {
        assertEquals(1L, save(new SimpleAnnotatedModel()));
    }

    @Test
    public void whereCountTest() throws Exception {
        save(new SimpleAnnotatedModel());
        save(new SimpleAnnotatedModel());
        assertEquals(1L, count(SimpleAnnotatedModel.class, "id = ?", new String[]{"1"}));
    }

    @Test
    public void whereNoCountTest() throws Exception {
        assertEquals(0L, count(SimpleAnnotatedModel.class, "id = ?", new String[]{"1"}));
        save(new SimpleAnnotatedModel());
        save(new SimpleAnnotatedModel());
        assertEquals(0L, count(SimpleAnnotatedModel.class, "id = ?", new String[]{"3"}));
        assertEquals(0L, count(SimpleAnnotatedModel.class, "id = ?", new String[]{"a"}));
    }

    @Test
    public void whereBrokenCountTest() throws Exception {
        save(new SimpleAnnotatedModel());
        save(new SimpleAnnotatedModel());
        assertEquals(-1L, count(SimpleAnnotatedModel.class, "di = ?", new String[]{"1"}));
    }

    @Test
    public void deleteTest() throws Exception {
        SimpleAnnotatedModel model = new SimpleAnnotatedModel();
        save(model);
        assertEquals(1L, count(SimpleAnnotatedModel.class));
        assertTrue(delete(model));
        assertEquals(0L, count(SimpleAnnotatedModel.class));
    }

    @Test
    public void deleteUnsavedTest() throws Exception {
        SimpleAnnotatedModel model = new SimpleAnnotatedModel();
        assertEquals(0L, count(SimpleAnnotatedModel.class));
        assertFalse(delete(model));
        assertEquals(0L, count(SimpleAnnotatedModel.class));
    }

    @Test
    public void deleteWrongTest() throws Exception {
        SimpleAnnotatedModel model = new SimpleAnnotatedModel();
        save(model);
        assertEquals(1L, count(SimpleAnnotatedModel.class));

        Field idField = model.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(model, Long.MAX_VALUE);

        assertFalse(delete(model));
        assertEquals(1L, count(SimpleAnnotatedModel.class));
    }

    @Test
    public void deleteAllTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            save(new SimpleAnnotatedModel());
        }

        assertEquals(100, deleteAll(SimpleAnnotatedModel.class));
        assertEquals(0L, count(SimpleAnnotatedModel.class));
    }

    @Test
    @SuppressWarnings("all")
    public void deleteAllWhereTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            save(new SimpleAnnotatedModel());
        }

        assertEquals(99, deleteAll(SimpleAnnotatedModel.class, "id > ?", new String[]{"1"}));
        assertEquals(1L, count(SimpleAnnotatedModel.class));
    }

    @Test
    public void deleteInTransactionFewTest() throws Exception {
        SimpleAnnotatedModel first = new SimpleAnnotatedModel();
        SimpleAnnotatedModel second = new SimpleAnnotatedModel();
        SimpleAnnotatedModel third = new SimpleAnnotatedModel();
        save(first);
        save(second);
        // Not saving last model
        assertEquals(2L, count(SimpleAnnotatedModel.class));
        assertEquals(2, deleteInTx(first, second, third));
        assertEquals(0L, count(SimpleAnnotatedModel.class));
    }

    @Test
    public void deleteInTransactionManyTest() throws Exception {
        List<SimpleAnnotatedModel> models = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            SimpleAnnotatedModel model = new SimpleAnnotatedModel();
            models.add(model);
            // Not saving last model
            if (i < 100) {
                save(model);
            }
        }

        assertEquals(99, count(SimpleAnnotatedModel.class));
        assertEquals(99, deleteInTx(models));
        assertEquals(0L, count(SimpleAnnotatedModel.class));
    }

    @Test
    public void saveInTransactionTest() throws Exception {
        saveInTx(new SimpleAnnotatedModel(), new SimpleAnnotatedModel());
        assertEquals(2L, count(SimpleAnnotatedModel.class));
    }

    @Test
    public void listAllTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            save(new SimpleAnnotatedModel());
        }

        List<SimpleAnnotatedModel> models = listAll(SimpleAnnotatedModel.class);
        assertEquals(100, models.size());

        for (long i = 1; i <= 100; i++) {
            assertEquals(Long.valueOf(i), models.get((int) i - 1).getId());
        }
    }

    @Test
    public void findTest() throws Exception {
        save(new SimpleAnnotatedModel());
        save(new SimpleAnnotatedModel());

        List<SimpleAnnotatedModel> models = find(SimpleAnnotatedModel.class, "id = ?", "2");

        assertEquals(1, models.size());
        assertEquals(2L, models.get(0).getId().longValue());
    }

    @Test
    public void findWithQueryTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            save(new SimpleAnnotatedModel());
        }

        List<SimpleAnnotatedModel> models = findWithQuery(SimpleAnnotatedModel.class, "Select * from " +
                        NamingHelper.toTableName(SimpleAnnotatedModel.class) +
                        " where id >= ? ", "50");

        for (SimpleAnnotatedModel model : models) {
            assertEquals(75L, model.getId(), 25L);
        }
    }

    @Test
    @SuppressWarnings("all")
    public void findByIdTest() throws Exception {
        save(new SimpleAnnotatedModel());
        assertEquals(1L, findById(SimpleAnnotatedModel.class, 1L).getId().longValue());
    }

    @Test
    public void findByIdIntegerTest() throws Exception {
        save(new SimpleAnnotatedModel());
        assertEquals(1L, findById(SimpleAnnotatedModel.class, 1).getId().longValue());
    }

    @Test
    public void findByIdStringsNullTest() throws Exception {
        save(new SimpleAnnotatedModel());
        assertEquals(0, findById(SimpleAnnotatedModel.class, new String[]{""}).size());
    }

    @Test
    public void findByIdStringsOneTest() throws Exception {
        save(new SimpleAnnotatedModel());
        List<SimpleAnnotatedModel> models = findById(SimpleAnnotatedModel.class, new String[]{"1"});
        assertEquals(1, models.size());
        assertEquals(1L, models.get(0).getId().longValue());
    }

    @Test
    public void findByIdStringsTwoTest() throws Exception {
        save(new SimpleAnnotatedModel());
        save(new SimpleAnnotatedModel());
        save(new SimpleAnnotatedModel());
        List<SimpleAnnotatedModel> models = findById(SimpleAnnotatedModel.class, new String[]{"1", "3"});
        assertEquals(2, models.size());
        assertEquals(Long.valueOf(1L), models.get(0).getId());
        assertEquals(Long.valueOf(3L), models.get(1).getId());
    }

    @Test
    public void findByIdStringsManyTest() throws Exception {
        for (int i = 1; i <= 10; i++) {
            save(new SimpleAnnotatedModel());
        }
        List<SimpleAnnotatedModel> models = findById(SimpleAnnotatedModel.class, new String[]{"1", "3", "6", "10"});
        assertEquals(4, models.size());
        assertEquals(Long.valueOf(1L), models.get(0).getId());
        assertEquals(Long.valueOf(3L), models.get(1).getId());
        assertEquals(Long.valueOf(6L), models.get(2).getId());
        assertEquals(Long.valueOf(10L), models.get(3).getId());
    }

    @Test
    public void findByIdStringsOrderTest() throws Exception {
        for (int i = 1; i <= 10; i++) {
            save(new SimpleAnnotatedModel());
        }
        List<SimpleAnnotatedModel> models = findById(SimpleAnnotatedModel.class, new String[]{"10", "6", "3", "1"});
        assertEquals(4, models.size());
        // The order of the query doesn't matter
        assertEquals(Long.valueOf(1L), models.get(0).getId());
        assertEquals(Long.valueOf(3L), models.get(1).getId());
        assertEquals(Long.valueOf(6L), models.get(2).getId());
        assertEquals(Long.valueOf(10L), models.get(3).getId());
    }

    @Test
    public void findByIdNullTest() throws Exception {
        save(new SimpleAnnotatedModel());
        assertNull(findById(SimpleAnnotatedModel.class, 2L));
    }

    @Test
    public void findAllTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            save(new SimpleAnnotatedModel());
        }
        Iterator<SimpleAnnotatedModel> cursor = findAll(SimpleAnnotatedModel.class);
        for (int i = 1; i <= 100; i++) {
            assertTrue(cursor.hasNext());
            SimpleAnnotatedModel model = cursor.next();
            assertNotNull(model);
            assertEquals(Long.valueOf(i), model.getId());
        }
    }

    @Test
    public void findAsIteratorTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            save(new SimpleAnnotatedModel());
        }
        Iterator<SimpleAnnotatedModel> cursor = findAsIterator(SimpleAnnotatedModel.class,
                "id >= ?", "50");
        for (int i = 50; i <= 100; i++) {
            assertTrue(cursor.hasNext());
            SimpleAnnotatedModel model = cursor.next();
            assertNotNull(model);
            assertEquals(Long.valueOf(i), model.getId());
        }
    }

    @Test
    public void findWithQueryAsIteratorTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            save(new SimpleAnnotatedModel());
        }
        Iterator<SimpleAnnotatedModel> cursor = findWithQueryAsIterator(SimpleAnnotatedModel.class,
                        "Select * from " +
                                NamingHelper.toTableName(SimpleAnnotatedModel.class) +
                                " where id >= ? ", "50");
        for (int i = 50; i <= 100; i++) {
            assertTrue(cursor.hasNext());
            SimpleAnnotatedModel model = cursor.next();
            assertNotNull(model);
            assertEquals(Long.valueOf(i), model.getId());
        }
    }

    @Test(expected=NoSuchElementException.class)
    public void findAsIteratorOutOfBoundsTest() throws Exception {
        save(new SimpleAnnotatedModel());
        Iterator<SimpleAnnotatedModel> cursor = findAsIterator(SimpleAnnotatedModel.class,
                "id = ?", "1");
        assertTrue(cursor.hasNext());
        SimpleAnnotatedModel model = cursor.next();
        assertNotNull(model);
        assertEquals(Long.valueOf(1), model.getId());
        // This should throw a NoSuchElementException
        cursor.next();
    }

    @Test(expected=UnsupportedOperationException.class)
    public void disallowRemoveCursorTest() throws Exception {
        save(new SimpleAnnotatedModel());
        Iterator<SimpleAnnotatedModel> cursor = findAsIterator(SimpleAnnotatedModel.class, "id = ?", "1");
        assertTrue(cursor.hasNext());
        SimpleAnnotatedModel model = cursor.next();
        assertNotNull(model);
        assertEquals(Long.valueOf(1), model.getId());
        // This should throw a UnsupportedOperationException
        cursor.remove();
    }

    @Test
    public void vacuumTest() throws Exception {
        executeQuery("Vacuum");
    }
}