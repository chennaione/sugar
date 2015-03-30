package com.example.sugartest;

import com.example.models.NestedMixedABModel;
import com.example.models.RelationshipMixedBModel;
import com.example.models.SimpleExtendedModel;
import com.orm.SugarRecord;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.util.List;

import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;


@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk=18)
public class NestedMixedABTests {
    @Test
    public void emptyDatabaseTest() throws Exception {
        assertEquals(0L, SugarRecord.count(NestedMixedABModel.class));
        assertEquals(0L, SugarRecord.count(RelationshipMixedBModel.class));
        assertEquals(0L, SugarRecord.count(SimpleExtendedModel.class));
    }

    @Test
    public void oneSaveTest() throws Exception {
        SimpleExtendedModel simple = new SimpleExtendedModel();
        save(simple);
        RelationshipMixedBModel nested = new RelationshipMixedBModel(simple);
        save(nested);
        save(new NestedMixedABModel(nested));
        assertEquals(1L, SugarRecord.count(SimpleExtendedModel.class));
        assertEquals(1L, SugarRecord.count(RelationshipMixedBModel.class));
        assertEquals(1L, SugarRecord.count(NestedMixedABModel.class));
    }

    @Test
    public void twoSameSaveTest() throws Exception {
        SimpleExtendedModel simple = new SimpleExtendedModel();
        save(simple);
        RelationshipMixedBModel nested = new RelationshipMixedBModel(simple);
        save(nested);
        save(new NestedMixedABModel(nested));
        save(new NestedMixedABModel(nested));
        assertEquals(1L, SugarRecord.count(SimpleExtendedModel.class));
        assertEquals(1L, SugarRecord.count(RelationshipMixedBModel.class));
        assertEquals(2L, SugarRecord.count(NestedMixedABModel.class));
    }

    @Test
    public void twoDifferentSaveTest() throws Exception {
        SimpleExtendedModel simple = new SimpleExtendedModel();
        save(simple);
        SimpleExtendedModel another_simple = new SimpleExtendedModel();
        save(another_simple);
        RelationshipMixedBModel nested = new RelationshipMixedBModel(simple);
        save(nested);
        RelationshipMixedBModel another_nested = new RelationshipMixedBModel(another_simple);
        save(another_nested);
        save(new NestedMixedABModel(nested));
        save(new NestedMixedABModel(another_nested));
        assertEquals(2L, SugarRecord.count(SimpleExtendedModel.class));
        assertEquals(2L, SugarRecord.count(RelationshipMixedBModel.class));
        assertEquals(2L, SugarRecord.count(NestedMixedABModel.class));
    }

    @Test
    public void manySameSaveTest() throws Exception {
        SimpleExtendedModel simple = new SimpleExtendedModel();
        save(simple);
        RelationshipMixedBModel nested = new RelationshipMixedBModel(simple);
        save(nested);
        for (int i = 1; i <= 100; i++) {
            save(new NestedMixedABModel(nested));
        }
        assertEquals(1L, SugarRecord.count(SimpleExtendedModel.class));
        assertEquals(1L, SugarRecord.count(RelationshipMixedBModel.class));
        assertEquals(100L, SugarRecord.count(NestedMixedABModel.class));
    }

    @Test
    public void manyDifferentSaveTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            SimpleExtendedModel simple = new SimpleExtendedModel();
            save(simple);
            RelationshipMixedBModel nested = new RelationshipMixedBModel(simple);
            save(nested);
            save(new NestedMixedABModel(nested));
        }
        assertEquals(100L, SugarRecord.count(SimpleExtendedModel.class));
        assertEquals(100L, SugarRecord.count(RelationshipMixedBModel.class));
        assertEquals(100L, SugarRecord.count(NestedMixedABModel.class));
    }

    @Test
    public void listAllSameTest() throws Exception {
        SimpleExtendedModel simple = new SimpleExtendedModel();
        save(simple);
        RelationshipMixedBModel nested = new RelationshipMixedBModel(simple);
        save(nested);
        for (int i = 1; i <= 100; i++) {
            save(new NestedMixedABModel(nested));
        }
        List<NestedMixedABModel> models = SugarRecord.listAll(NestedMixedABModel.class);
        assertEquals(100, models.size());
        for (NestedMixedABModel model : models) {
            assertEquals(nested.getId(), model.getNested().getId());
            assertEquals(simple.getId(), model.getNested().getSimple().getId());
        }
    }

    @Test
    public void listAllDifferentTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            SimpleExtendedModel simple = new SimpleExtendedModel();
            save(simple);
            RelationshipMixedBModel nested = new RelationshipMixedBModel(simple);
            save(nested);
            save(new NestedMixedABModel(nested));
        }
        List<NestedMixedABModel> models = SugarRecord.listAll(NestedMixedABModel.class);
        assertEquals(100, models.size());
        for (NestedMixedABModel model : models) {
            assertEquals(model.getId(), model.getNested().getId());
            assertEquals(model.getId(), model.getNested().getSimple().getId());
        }
    }
}
