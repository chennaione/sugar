package com.example.sugartest;

import com.example.models.SimpleExtendedModel;
import com.orm.SugarRecord;
import com.orm.util.NamingHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk=18)
public class SimpleExtendedModelTests {
    @Test
    public void emptyDatabaseTest() throws Exception {
        assertEquals(0L, SugarRecord.count(SimpleExtendedModel.class));
    }

    @Test
    public void oneSaveTest() throws Exception {
        save(new SimpleExtendedModel());
        assertEquals(1L, SugarRecord.count(SimpleExtendedModel.class));
    }

    @Test
    public void twoSaveTest() throws Exception {
        save(new SimpleExtendedModel());
        save(new SimpleExtendedModel());
        assertEquals(2L, SugarRecord.count(SimpleExtendedModel.class));
    }

    @Test
    public void manySaveTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            save(new SimpleExtendedModel());
        }
        assertEquals(100L, SugarRecord.count(SimpleExtendedModel.class));
    }

    @Test
    public void defaultIdTest() throws Exception {
        assertEquals(1L, save(new SimpleExtendedModel()));
    }

    @Test
    public void whereCountTest() throws Exception {
        save(new SimpleExtendedModel());
        save(new SimpleExtendedModel());
        assertEquals(1L, SugarRecord.count(SimpleExtendedModel.class, "id = ?", new String[]{"1"}));
    }

    @Test
    public void whereNoCountTest() throws Exception {
        assertEquals(0L, SugarRecord.count(SimpleExtendedModel.class, "id = ?", new String[]{"1"}));
        save(new SimpleExtendedModel());
        save(new SimpleExtendedModel());
        assertEquals(0L, SugarRecord.count(SimpleExtendedModel.class, "id = ?", new String[]{"3"}));
        assertEquals(0L, SugarRecord.count(SimpleExtendedModel.class, "id = ?", new String[]{"a"}));
    }

    @Test
    public void whereBrokenCountTest() throws Exception {
        save(new SimpleExtendedModel());
        save(new SimpleExtendedModel());
        assertEquals(-1L, SugarRecord.count(SimpleExtendedModel.class, "di = ?", new String[]{"1"}));
    }

    @Test
    public void saveMethodTest() throws Exception {
        SimpleExtendedModel model = new SimpleExtendedModel();
        model.save();
        assertEquals(-1L, SugarRecord.count(SimpleExtendedModel.class, "di = ?", new String[]{"1"}));
    }

    @Test
    public void deleteTest() throws Exception {
        SimpleExtendedModel model = new SimpleExtendedModel();
        save(model);
        assertEquals(1L, SugarRecord.count(SimpleExtendedModel.class));
        model.delete();
        assertEquals(0L, SugarRecord.count(SimpleExtendedModel.class));
    }

    @Test
    public void deleteAllTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            save(new SimpleExtendedModel());
        }
        SugarRecord.deleteAll(SimpleExtendedModel.class);
        assertEquals(0L, SugarRecord.count(SimpleExtendedModel.class));
    }

    @Test
    public void deleteAllWhereTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            save(new SimpleExtendedModel());
        }
        SugarRecord.deleteAll(SimpleExtendedModel.class, "id > ?", new String[]{"1"});
        assertEquals(1L, SugarRecord.count(SimpleExtendedModel.class));
    }

    @Test
    public void saveInTransactionTest() throws Exception {
        SugarRecord.saveInTx(new SimpleExtendedModel(), new SimpleExtendedModel());
        assertEquals(2L, SugarRecord.count(SimpleExtendedModel.class));
    }

    @Test
    public void listAllTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            save(new SimpleExtendedModel());
        }
        List<SimpleExtendedModel> models = SugarRecord.listAll(SimpleExtendedModel.class);
        assertEquals(100, models.size());
        for (long i = 1; i <= 100; i++) {
            assertEquals(new Long(i), models.get((int) i - 1).getId());
        }
    }

    @Test
    public void findTest() throws Exception {
        save(new SimpleExtendedModel());
        save(new SimpleExtendedModel());
        List<SimpleExtendedModel> models =
                SugarRecord.find(SimpleExtendedModel.class, "id = ?", "2");
        assertEquals(1, models.size());
        assertEquals(new Long(2L), models.get(0).getId());
    }

    @Test
    public void findWithQueryTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            save(new SimpleExtendedModel());
        }
        List<SimpleExtendedModel> models =
                SugarRecord.findWithQuery(SimpleExtendedModel.class, "Select * from " +
                                          NamingHelper.toSQLName(SimpleExtendedModel.class) +
                                          " where id >= ? ", "50");
        for (SimpleExtendedModel model : models) {
            assertEquals(new Long(75), model.getId(), 25L);
        }
    }

    @Test
    public void findByIdTest() throws Exception {
        save(new SimpleExtendedModel());
        assertEquals(new Long(1L), SugarRecord.findById(SimpleExtendedModel.class, 1L).getId());
    }

    @Test
    public void findByIdIntegerTest() throws Exception {
        save(new SimpleExtendedModel());
        assertEquals(new Long(1L), SugarRecord.findById(SimpleExtendedModel.class, 1).getId());
    }

    @Test
    public void findByIdNullTest() throws Exception {
        save(new SimpleExtendedModel());
        assertNull(SugarRecord.findById(SimpleExtendedModel.class, 2L));
    }

    @Test
    public void findAllTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            save(new SimpleExtendedModel());
        }
        Iterator<SimpleExtendedModel> cursor = SugarRecord.findAll(SimpleExtendedModel.class);
        for (int i = 1; i <= 100; i++) {
            assertTrue(cursor.hasNext());
            SimpleExtendedModel model = cursor.next();
            assertNotNull(model);
            assertEquals(new Long(i), model.getId());
        }
    }

    @Test
    public void findAsIteratorTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            save(new SimpleExtendedModel());
        }
        Iterator<SimpleExtendedModel> cursor = SugarRecord.findAsIterator(SimpleExtendedModel.class,
                "id >= ?", "50");
        for (int i = 50; i <= 100; i++) {
            assertTrue(cursor.hasNext());
            SimpleExtendedModel model = cursor.next();
            assertNotNull(model);
            assertEquals(new Long(i), model.getId());
        }
    }

    @Test
    public void findWithQueryAsIteratorTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            save(new SimpleExtendedModel());
        }
        Iterator<SimpleExtendedModel> cursor =
                SugarRecord.findWithQueryAsIterator(SimpleExtendedModel.class,
                                                    "Select * from " +
                                                    NamingHelper.toSQLName(SimpleExtendedModel.class) +
                                                    " where id >= ? ", "50");
        for (int i = 50; i <= 100; i++) {
            assertTrue(cursor.hasNext());
            SimpleExtendedModel model = cursor.next();
            assertNotNull(model);
            assertEquals(new Long(i), model.getId());
        }
    }

    @Test(expected=NoSuchElementException.class)
    public void findAsIteratorOutOfBoundsTest() throws Exception {
        save(new SimpleExtendedModel());
        Iterator<SimpleExtendedModel> cursor = SugarRecord.findAsIterator(SimpleExtendedModel.class,
                "id = ?", "1");
        assertTrue(cursor.hasNext());
        SimpleExtendedModel model = cursor.next();
        assertNotNull(model);
        assertEquals(new Long(1), model.getId());
        // This should throw a NoSuchElementException
        cursor.next();
    }

    @Test(expected=UnsupportedOperationException.class)
    public void disallowRemoveCursorTest() throws Exception {
        save(new SimpleExtendedModel());
        Iterator<SimpleExtendedModel> cursor = SugarRecord.findAsIterator(SimpleExtendedModel.class,
                "id = ?", "1");
        assertTrue(cursor.hasNext());
        SimpleExtendedModel model = cursor.next();
        assertNotNull(model);
        assertEquals(new Long(1), model.getId());
        // This should throw a UnsupportedOperationException
        cursor.remove();
    }

    @Test
    public void vacuumTest() throws Exception {
        SugarRecord.executeQuery("Vacuum");
    }
}