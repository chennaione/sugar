package com.orm.record;

import com.orm.app.ClientApp;
import com.orm.dsl.BuildConfig;
import com.orm.model.RelationshipAnnotatedModel;
import com.orm.model.SimpleAnnotatedModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static com.orm.SugarRecord.save;
import static com.orm.SugarRecord.count;
import static com.orm.SugarRecord.listAll;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, constants = BuildConfig.class, application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class RelationshipAnnotatedTests {

    @Test
    public void emptyDatabaseTest() throws Exception {
        assertEquals(0L, count(RelationshipAnnotatedModel.class));
        assertEquals(0L, count(SimpleAnnotatedModel.class));
    }

    @Test
    public void oneSaveTest() throws Exception {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel();

        save(simple);
        save(new RelationshipAnnotatedModel(simple));

        assertEquals(1L, count(SimpleAnnotatedModel.class));
        assertEquals(1L, count(RelationshipAnnotatedModel.class));
    }

    @Test
    public void twoSameSaveTest() throws Exception {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel();

        save(simple);
        save(new RelationshipAnnotatedModel(simple));
        save(new RelationshipAnnotatedModel(simple));

        assertEquals(1L, count(SimpleAnnotatedModel.class));
        assertEquals(2L, count(RelationshipAnnotatedModel.class));
    }

    @Test
    public void twoDifferentSaveTest() throws Exception {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel();
        save(simple);
        SimpleAnnotatedModel anotherSimple = new SimpleAnnotatedModel();

        save(anotherSimple);
        save(new RelationshipAnnotatedModel(simple));
        save(new RelationshipAnnotatedModel(anotherSimple));

        assertEquals(2L, count(SimpleAnnotatedModel.class));
        assertEquals(2L, count(RelationshipAnnotatedModel.class));
    }

    @Test
    public void manySameSaveTest() throws Exception {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel();
        save(simple);

        for (int i = 1; i <= 100; i++) {
            save(new RelationshipAnnotatedModel(simple));
        }

        assertEquals(1L, count(SimpleAnnotatedModel.class));
        assertEquals(100L, count(RelationshipAnnotatedModel.class));
    }

    @Test
    public void manyDifferentSaveTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            SimpleAnnotatedModel simple = new SimpleAnnotatedModel();
            save(simple);
            save(new RelationshipAnnotatedModel(simple));
        }

        assertEquals(100L, count(SimpleAnnotatedModel.class));
        assertEquals(100L, count(RelationshipAnnotatedModel.class));
    }

    @Test
    public void listAllSameTest() throws Exception {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel();
        save(simple);

        for (int i = 1; i <= 100; i++) {
            save(new RelationshipAnnotatedModel(simple));
        }

        List<RelationshipAnnotatedModel> models = listAll(RelationshipAnnotatedModel.class);
        assertEquals(100, models.size());

        for (RelationshipAnnotatedModel model : models) {
            assertEquals(simple.getId(), model.getSimple().getId());
        }
    }

    @Test
    public void listAllDifferentTest() throws Exception {
        for (int i = 1; i <= 100; i++) {
            SimpleAnnotatedModel simple = new SimpleAnnotatedModel();
            save(simple);
            save(new RelationshipAnnotatedModel(simple));
        }

        List<RelationshipAnnotatedModel> models = listAll(RelationshipAnnotatedModel.class);
        assertEquals(100, models.size());

        for (RelationshipAnnotatedModel model : models) {
            assertEquals(model.getId(), model.getSimple().getId());
        }
    }
}
