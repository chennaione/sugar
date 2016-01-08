package com.example.sugartest;

import com.example.models.PrimaryKeyNotationRelationshipExtendedModel;
import com.example.models.SimpleExtendedModel;
import com.orm.SugarRecord;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.util.List;


import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;


@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk=18)
public class PrimaryKeyNotationRelationshipExtendedTests{
    @Test
    public void emptyDatabaseTest() throws Exception {
        assertEquals(0L, SugarRecord.count(PrimaryKeyNotationRelationshipExtendedModel.class));
        assertEquals(0L, SugarRecord.count(SimpleExtendedModel.class));
    }

    @Test
    public void oneSaveTest() throws Exception {
        SimpleExtendedModel simple = new SimpleExtendedModel();
        save(simple);
        save(new PrimaryKeyNotationRelationshipExtendedModel(simple));
        assertEquals(1L, SugarRecord.count(SimpleExtendedModel.class));
        assertEquals(1L, SugarRecord.count(PrimaryKeyNotationRelationshipExtendedModel.class));
    }

    @Test
    public void twoSameSaveTest() throws Exception {
        SimpleExtendedModel simple = new SimpleExtendedModel();
        save(simple);
        save(new PrimaryKeyNotationRelationshipExtendedModel(simple));
        save(new PrimaryKeyNotationRelationshipExtendedModel(simple));
        assertEquals(1L, SugarRecord.count(SimpleExtendedModel.class));
        assertEquals(2L, SugarRecord.count(PrimaryKeyNotationRelationshipExtendedModel.class));
    }

    @Test
    public void twoDifferentSaveTest() throws Exception {
        SimpleExtendedModel simple = new SimpleExtendedModel();
        save(simple);
        SimpleExtendedModel another_simple = new SimpleExtendedModel();
        save(another_simple);
        save(new PrimaryKeyNotationRelationshipExtendedModel(simple));
        save(new PrimaryKeyNotationRelationshipExtendedModel(another_simple));
        assertEquals(2L, SugarRecord.count(SimpleExtendedModel.class));
        assertEquals(2L, SugarRecord.count(PrimaryKeyNotationRelationshipExtendedModel.class));
    }

    @Test
    public void manySameSaveTest() throws Exception {
        SimpleExtendedModel simple = new SimpleExtendedModel();
        save(simple);
        for (int i = 1; i <= 100; i++) {
            save(new PrimaryKeyNotationRelationshipExtendedModel(simple));
        }
        assertEquals(1L, SugarRecord.count(SimpleExtendedModel.class));
        assertEquals(100L, SugarRecord.count(PrimaryKeyNotationRelationshipExtendedModel.class));
    }

    @Test
    public void manyDifferentSaveTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            SimpleExtendedModel simple = new SimpleExtendedModel();
            save(simple);
            save(new PrimaryKeyNotationRelationshipExtendedModel(simple));
        }
        assertEquals(100L, SugarRecord.count(SimpleExtendedModel.class));
        assertEquals(100L, SugarRecord.count(PrimaryKeyNotationRelationshipExtendedModel.class));
    }

    @Test
    public void listAllSameTest() throws Exception {
        SimpleExtendedModel simple = new SimpleExtendedModel();
        save(simple);
        for (int i = 1; i <= 100; i++) {
            save(new PrimaryKeyNotationRelationshipExtendedModel(simple));
        }
        List<PrimaryKeyNotationRelationshipExtendedModel> models =
                SugarRecord.listAll(PrimaryKeyNotationRelationshipExtendedModel.class);
        assertEquals(100, models.size());
        for (PrimaryKeyNotationRelationshipExtendedModel model : models) {
            assertEquals(simple.getId(), model.getSimple().getId());
        }
    }

    @Test
    public void listAllDifferentTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            SimpleExtendedModel simple = new SimpleExtendedModel();
            save(simple);
            save(new PrimaryKeyNotationRelationshipExtendedModel(simple));
        }
        List<PrimaryKeyNotationRelationshipExtendedModel> models =
                SugarRecord.listAll(PrimaryKeyNotationRelationshipExtendedModel.class);
        assertEquals(100, models.size());
        for (PrimaryKeyNotationRelationshipExtendedModel model : models) {
            assertEquals(model.getId(), model.getSimple().getId());
        }
    }
}
