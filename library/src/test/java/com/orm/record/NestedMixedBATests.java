package com.orm.record;

import com.orm.app.ClientApp;
import com.orm.SugarRecord;
import com.orm.dsl.BuildConfig;
import com.orm.model.NestedMixedBAModel;
import com.orm.model.RelationshipMixedAModel;
import com.orm.model.SimpleAnnotatedModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, constants = BuildConfig.class, application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class NestedMixedBATests {

    @Test
    public void emptyDatabaseTest() throws Exception {
        assertEquals(0L, SugarRecord.count(NestedMixedBAModel.class));
        assertEquals(0L, SugarRecord.count(RelationshipMixedAModel.class));
        assertEquals(0L, SugarRecord.count(SimpleAnnotatedModel.class));
    }

    @Test
    public void oneSaveTest() throws Exception {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel();
        save(simple);
        RelationshipMixedAModel nested = new RelationshipMixedAModel(simple);
        save(nested);
        save(new NestedMixedBAModel(nested));
        assertEquals(1L, SugarRecord.count(SimpleAnnotatedModel.class));
        assertEquals(1L, SugarRecord.count(RelationshipMixedAModel.class));
        assertEquals(1L, SugarRecord.count(NestedMixedBAModel.class));
    }

    @Test
    public void twoSameSaveTest() throws Exception {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel();
        save(simple);
        RelationshipMixedAModel nested = new RelationshipMixedAModel(simple);
        save(nested);
        save(new NestedMixedBAModel(nested));
        save(new NestedMixedBAModel(nested));
        assertEquals(1L, SugarRecord.count(SimpleAnnotatedModel.class));
        assertEquals(1L, SugarRecord.count(RelationshipMixedAModel.class));
        assertEquals(2L, SugarRecord.count(NestedMixedBAModel.class));
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
        save(new NestedMixedBAModel(nested));
        save(new NestedMixedBAModel(another_nested));
        assertEquals(2L, SugarRecord.count(SimpleAnnotatedModel.class));
        assertEquals(2L, SugarRecord.count(RelationshipMixedAModel.class));
        assertEquals(2L, SugarRecord.count(NestedMixedBAModel.class));
    }

    @Test
    public void manySameSaveTest() throws Exception {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel();
        save(simple);
        RelationshipMixedAModel nested = new RelationshipMixedAModel(simple);
        save(nested);
        for (int i = 1; i <= 100; i++) {
            save(new NestedMixedBAModel(nested));
        }
        assertEquals(1L, SugarRecord.count(SimpleAnnotatedModel.class));
        assertEquals(1L, SugarRecord.count(RelationshipMixedAModel.class));
        assertEquals(100L, SugarRecord.count(NestedMixedBAModel.class));
    }

    @Test
    public void manyDifferentSaveTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            SimpleAnnotatedModel simple = new SimpleAnnotatedModel();
            save(simple);
            RelationshipMixedAModel nested = new RelationshipMixedAModel(simple);
            save(nested);
            save(new NestedMixedBAModel(nested));
        }
        assertEquals(100L, SugarRecord.count(SimpleAnnotatedModel.class));
        assertEquals(100L, SugarRecord.count(RelationshipMixedAModel.class));
        assertEquals(100L, SugarRecord.count(NestedMixedBAModel.class));
    }

    @Test
    public void listAllSameTest() throws Exception {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel();
        save(simple);
        RelationshipMixedAModel nested = new RelationshipMixedAModel(simple);
        save(nested);
        for (int i = 1; i <= 100; i++) {
            save(new NestedMixedBAModel(nested));
        }
        List<NestedMixedBAModel> models = SugarRecord.listAll(NestedMixedBAModel.class);
        assertEquals(100, models.size());
        for (NestedMixedBAModel model : models) {
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
            save(new NestedMixedBAModel(nested));
        }
        List<NestedMixedBAModel> models = SugarRecord.listAll(NestedMixedBAModel.class);
        assertEquals(100, models.size());
        for (NestedMixedBAModel model : models) {
            assertEquals(model.getId(), model.getNested().getId());
            assertEquals(model.getId(), model.getNested().getSimple().getId());
        }
    }
}
