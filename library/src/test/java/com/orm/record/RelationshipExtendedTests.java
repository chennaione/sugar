package com.orm.record;

import com.orm.ClientApp;
import com.orm.RobolectricGradleTestRunner;
import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.orm.models.RelationshipExtendedModel;
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
public class RelationshipExtendedTests {

    @Before
    public void setUp() {
        SugarContext.init(RuntimeEnvironment.application);
    }

    @Test
    public void emptyDatabaseTest() throws Exception {
        assertEquals(0L, SugarRecord.count(RelationshipExtendedModel.class));
        assertEquals(0L, SugarRecord.count(SimpleExtendedModel.class));
    }

    @Test
    public void oneSaveTest() throws Exception {
        SimpleExtendedModel simple = new SimpleExtendedModel();
        save(simple);
        save(new RelationshipExtendedModel(simple));
        assertEquals(1L, SugarRecord.count(SimpleExtendedModel.class));
        assertEquals(1L, SugarRecord.count(RelationshipExtendedModel.class));
    }

    @Test
    public void twoSameSaveTest() throws Exception {
        SimpleExtendedModel simple = new SimpleExtendedModel();
        save(simple);
        save(new RelationshipExtendedModel(simple));
        save(new RelationshipExtendedModel(simple));
        assertEquals(1L, SugarRecord.count(SimpleExtendedModel.class));
        assertEquals(2L, SugarRecord.count(RelationshipExtendedModel.class));
    }

    @Test
    public void twoDifferentSaveTest() throws Exception {
        SimpleExtendedModel simple = new SimpleExtendedModel();
        save(simple);
        SimpleExtendedModel another_simple = new SimpleExtendedModel();
        save(another_simple);
        save(new RelationshipExtendedModel(simple));
        save(new RelationshipExtendedModel(another_simple));
        assertEquals(2L, SugarRecord.count(SimpleExtendedModel.class));
        assertEquals(2L, SugarRecord.count(RelationshipExtendedModel.class));
    }

    @Test
    public void manySameSaveTest() throws Exception {
        SimpleExtendedModel simple = new SimpleExtendedModel();
        save(simple);
        for (int i = 1; i <= 100; i++) {
            save(new RelationshipExtendedModel(simple));
        }
        assertEquals(1L, SugarRecord.count(SimpleExtendedModel.class));
        assertEquals(100L, SugarRecord.count(RelationshipExtendedModel.class));
    }

    @Test
    public void manyDifferentSaveTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            SimpleExtendedModel simple = new SimpleExtendedModel();
            save(simple);
            save(new RelationshipExtendedModel(simple));
        }
        assertEquals(100L, SugarRecord.count(SimpleExtendedModel.class));
        assertEquals(100L, SugarRecord.count(RelationshipExtendedModel.class));
    }

    @Test
    public void listAllSameTest() throws Exception {
        SimpleExtendedModel simple = new SimpleExtendedModel();
        save(simple);
        for (int i = 1; i <= 100; i++) {
            save(new RelationshipExtendedModel(simple));
        }
        List<RelationshipExtendedModel> models =
                SugarRecord.listAll(RelationshipExtendedModel.class);
        assertEquals(100, models.size());
        for (RelationshipExtendedModel model : models) {
            assertEquals(simple.getId(), model.getSimple().getId());
        }
    }

    @Test
    public void listAllDifferentTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            SimpleExtendedModel simple = new SimpleExtendedModel();
            save(simple);
            save(new RelationshipExtendedModel(simple));
        }
        List<RelationshipExtendedModel> models =
                SugarRecord.listAll(RelationshipExtendedModel.class);
        assertEquals(100, models.size());
        for (RelationshipExtendedModel model : models) {
            assertEquals(model.getId(), model.getSimple().getId());
        }
    }
}
