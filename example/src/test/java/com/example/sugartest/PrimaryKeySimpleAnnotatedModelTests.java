package com.example.sugartest;

import com.example.models.PrimaryKeyNotationSimpleAnnotatedModel;
import com.orm.SugarRecord;
import com.orm.util.NamingHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


@RunWith (RobolectricGradleTestRunner.class)
@Config (sdk = 18)
public class PrimaryKeySimpleAnnotatedModelTests{
    @Test
    public void emptyDatabaseTest() throws Exception{
        assertEquals(0L, SugarRecord.count(PrimaryKeyNotationSimpleAnnotatedModel.class));
    }

    @Test
    public void oneSaveTest() throws Exception{
        save(new PrimaryKeyNotationSimpleAnnotatedModel());
        assertEquals(1L, SugarRecord.count(PrimaryKeyNotationSimpleAnnotatedModel.class));
    }

    @Test
    public void twoSaveTest() throws Exception{
        save(new PrimaryKeyNotationSimpleAnnotatedModel());
        save(new PrimaryKeyNotationSimpleAnnotatedModel());
        assertEquals(2L, SugarRecord.count(PrimaryKeyNotationSimpleAnnotatedModel.class));
    }

    @Test
    public void manySaveTest() throws Exception{
        for(int i = 1; i <= 100; i++){
            save(new PrimaryKeyNotationSimpleAnnotatedModel());
        }
        assertEquals(100L, SugarRecord.count(PrimaryKeyNotationSimpleAnnotatedModel.class));
    }

    @Test
    public void defaultIdTest() throws Exception{
        assertEquals(1L, save(new PrimaryKeyNotationSimpleAnnotatedModel()));
    }

    @Test
    public void whereCountTest() throws Exception{
        save(new PrimaryKeyNotationSimpleAnnotatedModel());
        save(new PrimaryKeyNotationSimpleAnnotatedModel());
        assertEquals(1L, SugarRecord.count(PrimaryKeyNotationSimpleAnnotatedModel.class, "my_id = ?", new String[] {"1"}));
    }

    @Test
    public void whereNoCountTest() throws Exception{
        assertEquals(0L, SugarRecord.count(PrimaryKeyNotationSimpleAnnotatedModel.class, "my_id = ?", new String[] {"1"}));
        save(new PrimaryKeyNotationSimpleAnnotatedModel());
        save(new PrimaryKeyNotationSimpleAnnotatedModel());
        assertEquals(0L, SugarRecord.count(PrimaryKeyNotationSimpleAnnotatedModel.class, "my_id = ?", new String[] {"3"}));
        assertEquals(0L, SugarRecord.count(PrimaryKeyNotationSimpleAnnotatedModel.class, "my_id = ?", new String[] {"a"}));
    }

    @Test
    public void whereBrokenCountTest() throws Exception{
        save(new PrimaryKeyNotationSimpleAnnotatedModel());
        save(new PrimaryKeyNotationSimpleAnnotatedModel());
        assertEquals(-1L, SugarRecord.count(PrimaryKeyNotationSimpleAnnotatedModel.class, "di = ?", new String[] {"1"}));
    }

    @Test
    public void deleteTest() throws Exception{
        PrimaryKeyNotationSimpleAnnotatedModel model = new PrimaryKeyNotationSimpleAnnotatedModel();
        save(model);
        assertEquals(1L, SugarRecord.count(PrimaryKeyNotationSimpleAnnotatedModel.class));
        assertTrue(SugarRecord.delete(model));
        assertEquals(0L, SugarRecord.count(PrimaryKeyNotationSimpleAnnotatedModel.class));
    }

    @Test
    public void deleteUnsavedTest() throws Exception{
        PrimaryKeyNotationSimpleAnnotatedModel model = new PrimaryKeyNotationSimpleAnnotatedModel();
        assertEquals(0L, SugarRecord.count(PrimaryKeyNotationSimpleAnnotatedModel.class));
        assertFalse(SugarRecord.delete(model));
        assertEquals(0L, SugarRecord.count(PrimaryKeyNotationSimpleAnnotatedModel.class));
    }

    @Test
    public void deleteWrongTest() throws Exception{
        PrimaryKeyNotationSimpleAnnotatedModel model = new PrimaryKeyNotationSimpleAnnotatedModel();
        save(model);
        assertEquals(1L, SugarRecord.count(PrimaryKeyNotationSimpleAnnotatedModel.class));
        Field idField = model.getClass().getDeclaredField("myId");
        idField.setAccessible(true);
        idField.set(model, Long.MAX_VALUE);
        assertFalse(SugarRecord.delete(model));
        assertEquals(1L, SugarRecord.count(PrimaryKeyNotationSimpleAnnotatedModel.class));
    }

    @Test
    public void deleteAllTest() throws Exception{
        int elementNumber = 100;
        for(int i = 1; i <= elementNumber; i++){
            save(new PrimaryKeyNotationSimpleAnnotatedModel());
        }
        assertEquals(elementNumber, SugarRecord.deleteAll(PrimaryKeyNotationSimpleAnnotatedModel.class));
        assertEquals(0L, SugarRecord.count(PrimaryKeyNotationSimpleAnnotatedModel.class));
    }

    @Test
    public void deleteAllWhereTest() throws Exception{
        int elementNumber = 100;
        for(int i = 1; i <= elementNumber; i++){
            save(new PrimaryKeyNotationSimpleAnnotatedModel());
        }
        assertEquals(elementNumber - 1, SugarRecord.deleteAll(PrimaryKeyNotationSimpleAnnotatedModel.class, "my_id > ?", new String[] 
                {"1"}));
        assertEquals(1L, SugarRecord.count(PrimaryKeyNotationSimpleAnnotatedModel.class));
    }

    @Test
    public void deleteInTransactionFewTest() throws Exception{
        PrimaryKeyNotationSimpleAnnotatedModel first = new PrimaryKeyNotationSimpleAnnotatedModel();
        PrimaryKeyNotationSimpleAnnotatedModel second = new PrimaryKeyNotationSimpleAnnotatedModel();
        PrimaryKeyNotationSimpleAnnotatedModel third = new PrimaryKeyNotationSimpleAnnotatedModel();
        save(first);
        save(second);
        // Not saving last model
        assertEquals(2L, SugarRecord.count(PrimaryKeyNotationSimpleAnnotatedModel.class));
        assertEquals(2, SugarRecord.deleteInTx(first, second, third));
        assertEquals(0L, SugarRecord.count(PrimaryKeyNotationSimpleAnnotatedModel.class));
    }

    @Test
    public void deleteInTransactionManyTest() throws Exception{
        long elementNumber = 100;
        List<PrimaryKeyNotationSimpleAnnotatedModel> models = new ArrayList<>();
        for(int i = 1; i <= elementNumber; i++){
            PrimaryKeyNotationSimpleAnnotatedModel model = new PrimaryKeyNotationSimpleAnnotatedModel();
            models.add(model);
            // Not saving last model
            if(i < elementNumber){
                save(model);
            }
        }
        assertEquals(elementNumber - 1, SugarRecord.count(PrimaryKeyNotationSimpleAnnotatedModel.class));
        assertEquals(elementNumber - 1, SugarRecord.deleteInTx(models));
        assertEquals(0L, SugarRecord.count(PrimaryKeyNotationSimpleAnnotatedModel.class));
    }

    @Test
    public void saveInTransactionTest() throws Exception{
        SugarRecord.saveInTx(new PrimaryKeyNotationSimpleAnnotatedModel(), new PrimaryKeyNotationSimpleAnnotatedModel());
        assertEquals(2L, SugarRecord.count(PrimaryKeyNotationSimpleAnnotatedModel.class));
    }

    @Test
    public void listAllTest() throws Exception{
        for(int i = 1; i <= 100; i++){
            save(new PrimaryKeyNotationSimpleAnnotatedModel());
        }
        List<PrimaryKeyNotationSimpleAnnotatedModel> models = SugarRecord.listAll(PrimaryKeyNotationSimpleAnnotatedModel.class);
        assertEquals(100, models.size());
        for(long i = 1; i <= 100; i++){
            assertEquals(new Long(i), models.get((int) i - 1).getMyId());
        }
    }

    @Test
    public void findTest() throws Exception{
        save(new PrimaryKeyNotationSimpleAnnotatedModel());
        save(new PrimaryKeyNotationSimpleAnnotatedModel());
        List<PrimaryKeyNotationSimpleAnnotatedModel> models = SugarRecord.find(PrimaryKeyNotationSimpleAnnotatedModel.class, "my_id = ?", "2");
        assertEquals(1, models.size());
        assertEquals(new Long(2L), models.get(0).getMyId());
    }

    @Test
    public void findWithQueryTest() throws Exception{
        for(int i = 1; i <= 100; i++){
            save(new PrimaryKeyNotationSimpleAnnotatedModel());
        }
        List<PrimaryKeyNotationSimpleAnnotatedModel> models =
                SugarRecord.findWithQuery(PrimaryKeyNotationSimpleAnnotatedModel.class, "Select * from " +
                                                                                        NamingHelper.toSQLName(
                                                                                                PrimaryKeyNotationSimpleAnnotatedModel.class) +
                                                                                        " where my_id >= ? ", "50");
        for(PrimaryKeyNotationSimpleAnnotatedModel model : models){
            assertEquals(new Long(75), model.getMyId(), 25L);
        }
    }

    @Test
    public void findByIdTest() throws Exception{
        save(new PrimaryKeyNotationSimpleAnnotatedModel());
        assertEquals(new Long(1L), SugarRecord.findById(PrimaryKeyNotationSimpleAnnotatedModel.class, 1L).getMyId());
    }

    @Test
    public void findByIdIntegerTest() throws Exception{
        save(new PrimaryKeyNotationSimpleAnnotatedModel());
        assertEquals(new Long(1L), SugarRecord.findById(PrimaryKeyNotationSimpleAnnotatedModel.class, 1).getMyId());
    }

    @Test
    public void findByIdStringsNullTest() throws Exception{
        save(new PrimaryKeyNotationSimpleAnnotatedModel());
        assertEquals(0, SugarRecord.findById(PrimaryKeyNotationSimpleAnnotatedModel.class, new String[] {""}).size());
    }

    @Test
    public void findByIdStringsOneTest() throws Exception{
        save(new PrimaryKeyNotationSimpleAnnotatedModel());
        List<PrimaryKeyNotationSimpleAnnotatedModel> models =
                SugarRecord.findById(PrimaryKeyNotationSimpleAnnotatedModel.class, new String[] {"1"});
        assertEquals(1, models.size());
        assertEquals(new Long(1L), models.get(0).getMyId());
    }

    @Test
    public void findByIdStringsTwoTest() throws Exception{
        save(new PrimaryKeyNotationSimpleAnnotatedModel());
        save(new PrimaryKeyNotationSimpleAnnotatedModel());
        save(new PrimaryKeyNotationSimpleAnnotatedModel());
        List<PrimaryKeyNotationSimpleAnnotatedModel> models =
                SugarRecord.findById(PrimaryKeyNotationSimpleAnnotatedModel.class, new String[] {"1", "3"});
        assertEquals(2, models.size());
        assertEquals(new Long(1L), models.get(0).getMyId());
        assertEquals(new Long(3L), models.get(1).getMyId());
    }

    @Test
    public void findByIdStringsManyTest() throws Exception{
        for(int i = 1; i <= 10; i++){
            save(new PrimaryKeyNotationSimpleAnnotatedModel());
        }
        List<PrimaryKeyNotationSimpleAnnotatedModel> models =
                SugarRecord.findById(PrimaryKeyNotationSimpleAnnotatedModel.class, new String[] {"1", "3", "6", "10"});
        assertEquals(4, models.size());
        assertEquals(new Long(1L), models.get(0).getMyId());
        assertEquals(new Long(3L), models.get(1).getMyId());
        assertEquals(new Long(6L), models.get(2).getMyId());
        assertEquals(new Long(10L), models.get(3).getMyId());
    }

    @Test
    public void findByIdStringsOrderTest() throws Exception{
        for(int i = 1; i <= 10; i++){
            save(new PrimaryKeyNotationSimpleAnnotatedModel());
        }
        List<PrimaryKeyNotationSimpleAnnotatedModel> models =
                SugarRecord.findById(PrimaryKeyNotationSimpleAnnotatedModel.class, new String[] {"10", "6", "3", "1"});
        assertEquals(4, models.size());
        // The order of the query doesn't matter
        assertEquals(new Long(1L), models.get(0).getMyId());
        assertEquals(new Long(3L), models.get(1).getMyId());
        assertEquals(new Long(6L), models.get(2).getMyId());
        assertEquals(new Long(10L), models.get(3).getMyId());
    }

    @Test
    public void findByIdNullTest() throws Exception{
        save(new PrimaryKeyNotationSimpleAnnotatedModel());
        assertNull(SugarRecord.findById(PrimaryKeyNotationSimpleAnnotatedModel.class, 2L));
    }

    @Test
    public void findAllTest() throws Exception{
        for(int i = 1; i <= 100; i++){
            save(new PrimaryKeyNotationSimpleAnnotatedModel());
        }
        Iterator<PrimaryKeyNotationSimpleAnnotatedModel> cursor = SugarRecord.findAll(PrimaryKeyNotationSimpleAnnotatedModel.class);
        for(int i = 1; i <= 100; i++){
            assertTrue(cursor.hasNext());
            PrimaryKeyNotationSimpleAnnotatedModel model = cursor.next();
            assertNotNull(model);
            assertEquals(new Long(i), model.getMyId());
        }
    }

    @Test
    public void findAsIteratorTest() throws Exception{
        for(int i = 1; i <= 100; i++){
            save(new PrimaryKeyNotationSimpleAnnotatedModel());
        }
        Iterator<PrimaryKeyNotationSimpleAnnotatedModel> cursor =
                SugarRecord.findAsIterator(PrimaryKeyNotationSimpleAnnotatedModel.class, "my_id >= ?", "50");
        for(int i = 50; i <= 100; i++){
            assertTrue(cursor.hasNext());
            PrimaryKeyNotationSimpleAnnotatedModel model = cursor.next();
            assertNotNull(model);
            assertEquals(new Long(i), model.getMyId());
        }
    }

    @Test
    public void findWithQueryAsIteratorTest() throws Exception{
        for(int i = 1; i <= 100; i++){
            save(new PrimaryKeyNotationSimpleAnnotatedModel());
        }
        Iterator<PrimaryKeyNotationSimpleAnnotatedModel> cursor =
                SugarRecord.findWithQueryAsIterator(PrimaryKeyNotationSimpleAnnotatedModel.class, "Select * from " +
                                                                                                  NamingHelper.toSQLName(
                                                                                                          PrimaryKeyNotationSimpleAnnotatedModel.class) +
                                                                                                  " where my_id >= ? ", "50");
        for(int i = 50; i <= 100; i++){
            assertTrue(cursor.hasNext());
            PrimaryKeyNotationSimpleAnnotatedModel model = cursor.next();
            assertNotNull(model);
            assertEquals(new Long(i), model.getMyId());
        }
    }

    @Test (expected = NoSuchElementException.class)
    public void findAsIteratorOutOfBoundsTest() throws Exception{
        save(new PrimaryKeyNotationSimpleAnnotatedModel());
        Iterator<PrimaryKeyNotationSimpleAnnotatedModel> cursor =
                SugarRecord.findAsIterator(PrimaryKeyNotationSimpleAnnotatedModel.class, "my_id = ?", "1");
        assertTrue(cursor.hasNext());
        PrimaryKeyNotationSimpleAnnotatedModel model = cursor.next();
        assertNotNull(model);
        assertEquals(new Long(1), model.getMyId());
        // This should throw a NoSuchElementException
        cursor.next();
    }

    @Test (expected = UnsupportedOperationException.class)
    public void disallowRemoveCursorTest() throws Exception{
        save(new PrimaryKeyNotationSimpleAnnotatedModel());
        Iterator<PrimaryKeyNotationSimpleAnnotatedModel> cursor =
                SugarRecord.findAsIterator(PrimaryKeyNotationSimpleAnnotatedModel.class, "my_id = ?", "1");
        assertTrue(cursor.hasNext());
        PrimaryKeyNotationSimpleAnnotatedModel model = cursor.next();
        assertNotNull(model);
        assertEquals(new Long(1), model.getMyId());
        // This should throw a UnsupportedOperationException
        cursor.remove();
    }

    @Test
    public void vacuumTest() throws Exception{
        SugarRecord.executeQuery("Vacuum");
    }
}