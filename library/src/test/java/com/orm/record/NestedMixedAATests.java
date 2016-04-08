package com.orm.record;

import com.orm.ClientApp;
import com.orm.RobolectricGradleTestRunner;
import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.orm.models.NestedMixedAAModel;
import com.orm.models.RelationshipMixedAModel;
import com.orm.models.SimpleAnnotatedModel;

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
public class NestedMixedAATests {

    @Before
    public void setUp() {
        SugarContext.init(RuntimeEnvironment.application);
    }

    @Test
    public void emptyDatabaseTest() throws Exception {
        assertEquals(0L, SugarRecord.count(NestedMixedAAModel.class));
        assertEquals(0L, SugarRecord.count(RelationshipMixedAModel.class));
        assertEquals(0L, SugarRecord.count(SimpleAnnotatedModel.class));
    }

    @Test
    public void oneSaveTest() throws Exception {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel();
        save(simple);
        RelationshipMixedAModel nested = new RelationshipMixedAModel(simple);
        save(nested);
        save(new NestedMixedAAModel(nested));
        assertEquals(1L, SugarRecord.count(SimpleAnnotatedModel.class));
        assertEquals(1L, SugarRecord.count(RelationshipMixedAModel.class));
        assertEquals(1L, SugarRecord.count(NestedMixedAAModel.class));
    }

    @Test
    public void twoSameSaveTest() throws Exception {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel();
        save(simple);
        RelationshipMixedAModel nested = new RelationshipMixedAModel(simple);
        save(nested);
        save(new NestedMixedAAModel(nested));
        save(new NestedMixedAAModel(nested));
        assertEquals(1L, SugarRecord.count(SimpleAnnotatedModel.class));
        assertEquals(1L, SugarRecord.count(RelationshipMixedAModel.class));
        assertEquals(2L, SugarRecord.count(NestedMixedAAModel.class));
    }

    @Test
    public void twoDifferentSaveTest() throws Exception {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel();
        save(simple);
        SimpleAnnotatedModel another_simple = new SimpleAnnotatedModel();
        save(another_simple);
        RelationshipMixedAModel nested = new RelationshipMixedAModel(simple);
        save(nested);
        RelationshipMixedAModel another_nested = new RelationshipMixedAModel(another_simple);
        save(another_nested);
        save(new NestedMixedAAModel(nested));
        save(new NestedMixedAAModel(another_nested));
        assertEquals(2L, SugarRecord.count(SimpleAnnotatedModel.class));
        assertEquals(2L, SugarRecord.count(RelationshipMixedAModel.class));
        assertEquals(2L, SugarRecord.count(NestedMixedAAModel.class));
    }

    @Test
    public void manySameSaveTest() throws Exception {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel();
        save(simple);
        RelationshipMixedAModel nested = new RelationshipMixedAModel(simple);
        save(nested);
        for (int i = 1; i <= 100; i++) {
            save(new NestedMixedAAModel(nested));
        }
        assertEquals(1L, SugarRecord.count(SimpleAnnotatedModel.class));
        assertEquals(1L, SugarRecord.count(RelationshipMixedAModel.class));
        assertEquals(100L, SugarRecord.count(NestedMixedAAModel.class));
    }

    @Test
    public void manyDifferentSaveTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            SimpleAnnotatedModel simple = new SimpleAnnotatedModel();
            save(simple);
            RelationshipMixedAModel nested = new RelationshipMixedAModel(simple);
            save(nested);
            save(new NestedMixedAAModel(nested));
        }
        assertEquals(100L, SugarRecord.count(SimpleAnnotatedModel.class));
        assertEquals(100L, SugarRecord.count(RelationshipMixedAModel.class));
        assertEquals(100L, SugarRecord.count(NestedMixedAAModel.class));
    }

    @Test
    public void listAllSameTest() throws Exception {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel();
        save(simple);
        RelationshipMixedAModel nested = new RelationshipMixedAModel(simple);
        save(nested);
        for (int i = 1; i <= 100; i++) {
            save(new NestedMixedAAModel(nested));
        }
        List<NestedMixedAAModel> models = SugarRecord.listAll(NestedMixedAAModel.class);
        assertEquals(100, models.size());
        for (NestedMixedAAModel model : models) {
            assertEquals(nested.getId(), model.getNested().getId());
            assertEquals(simple.getId(), model.getNested().getSimple().getId());
        }
    }

    @Test
    public void listAllDifferentTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            SimpleAnnotatedModel simple = new SimpleAnnotatedModel();
            save(simple);
            RelationshipMixedAModel nested = new RelationshipMixedAModel(simple);
            save(nested);
            save(new NestedMixedAAModel(nested));
        }
        List<NestedMixedAAModel> models = SugarRecord.listAll(NestedMixedAAModel.class);
        assertEquals(100, models.size());
        for (NestedMixedAAModel model : models) {
            assertEquals(model.getId(), model.getNested().getId());
            assertEquals(model.getId(), model.getNested().getSimple().getId());
        }
    }
}
