package com.orm.record;

import com.orm.ClientApp;
import com.orm.RobolectricGradleTestRunner;
import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.orm.models.NestedMixedBBModel;
import com.orm.models.RelationshipMixedBModel;
import com.orm.models.SimpleExtendedModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk=18, application = ClientApp.class)
public class NestedMixedBBTests {

    @Before
    public void setUp() {
        SugarContext.init(RuntimeEnvironment.application);
    }

    @Test
    public void emptyDatabaseTest() throws Exception {
        assertEquals(0L, SugarRecord.count(NestedMixedBBModel.class));
        assertEquals(0L, SugarRecord.count(RelationshipMixedBModel.class));
        assertEquals(0L, SugarRecord.count(SimpleExtendedModel.class));
    }

    @Test
    public void oneSaveTest() throws Exception {
         SimpleExtendedModel simple = new SimpleExtendedModel();
        save(simple);
        RelationshipMixedBModel nested = new RelationshipMixedBModel(simple);
        save(nested);
        save(new NestedMixedBBModel(nested));
        assertEquals(1L, SugarRecord.count(SimpleExtendedModel.class));
        assertEquals(1L, SugarRecord.count(RelationshipMixedBModel.class));
        assertEquals(1L, SugarRecord.count(NestedMixedBBModel.class));
    }

    @Test
    public void twoSameSaveTest() throws Exception {
        SimpleExtendedModel simple = new SimpleExtendedModel();
        save(simple);
        RelationshipMixedBModel nested = new RelationshipMixedBModel(simple);
        save(nested);
        save(new NestedMixedBBModel(nested));
        save(new NestedMixedBBModel(nested));
        assertEquals(1L, SugarRecord.count(SimpleExtendedModel.class));
        assertEquals(1L, SugarRecord.count(RelationshipMixedBModel.class));
        assertEquals(2L, SugarRecord.count(NestedMixedBBModel.class));
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
        save(new NestedMixedBBModel(nested));
        save(new NestedMixedBBModel(another_nested));
        assertEquals(2L, SugarRecord.count(SimpleExtendedModel.class));
        assertEquals(2L, SugarRecord.count(RelationshipMixedBModel.class));
        assertEquals(2L, SugarRecord.count(NestedMixedBBModel.class));
    }

    @Test
    public void manySameSaveTest() throws Exception {
        SimpleExtendedModel simple = new SimpleExtendedModel();
        save(simple);
        RelationshipMixedBModel nested = new RelationshipMixedBModel(simple);
        save(nested);
        for (int i = 1; i <= 100; i++) {
            save(new NestedMixedBBModel(nested));
        }
        assertEquals(1L, SugarRecord.count(SimpleExtendedModel.class));
        assertEquals(1L, SugarRecord.count(RelationshipMixedBModel.class));
        assertEquals(100L, SugarRecord.count(NestedMixedBBModel.class));
    }

    @Test
    public void manyDifferentSaveTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            SimpleExtendedModel simple = new SimpleExtendedModel();
            save(simple);
            RelationshipMixedBModel nested = new RelationshipMixedBModel(simple);
            save(nested);
            save(new NestedMixedBBModel(nested));
        }
        assertEquals(100L, SugarRecord.count(SimpleExtendedModel.class));
        assertEquals(100L, SugarRecord.count(RelationshipMixedBModel.class));
        assertEquals(100L, SugarRecord.count(NestedMixedBBModel.class));
    }

    @Test
    public void listAllSameTest() throws Exception {
        SimpleExtendedModel simple = new SimpleExtendedModel();
        save(simple);
        RelationshipMixedBModel nested = new RelationshipMixedBModel(simple);
        save(nested);
        for (int i = 1; i <= 100; i++) {
            save(new NestedMixedBBModel(nested));
        }
        List<NestedMixedBBModel> models = SugarRecord.listAll(NestedMixedBBModel.class);
        assertEquals(100, models.size());
        for (NestedMixedBBModel model : models) {
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
            save(new NestedMixedBBModel(nested));
        }
        List<NestedMixedBBModel> models = SugarRecord.listAll(NestedMixedBBModel.class);
        assertEquals(100, models.size());
        for (NestedMixedBBModel model : models) {
            assertEquals(model.getId(), model.getNested().getId());
            assertEquals(model.getId(), model.getNested().getSimple().getId());
        }
    }
}
