package com.orm.record;

import com.orm.app.ClientApp;
import com.orm.dsl.BuildConfig;
import com.orm.model.onetomany.OneToManyRelationModel;
import com.orm.model.onetomany.OneToManyModel;
import com.orm.model.onetomany.WithoutOneToManyAnnotationModel;
import com.orm.model.onetomany.WithoutOneToManyAnnotationRelationModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import static com.orm.SugarRecord.findById;
import static com.orm.SugarRecord.save;

/**
 * Created by Łukasz Wesołowski on 28.07.2016.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, constants = BuildConfig.class, application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public class OneToManyTest {
    @Test
    public void shouldSaveWithOneToManyRelation() {
        List<Long> relationIds = Arrays.asList(1l, 2l, 3l, 4l);

        OneToManyModel model = new OneToManyModel(1l);
        save(model);

        for (long i : relationIds) {
            save(new OneToManyRelationModel(i, model));
        }

        OneToManyModel result = findById(OneToManyModel.class, 1l);

        Assert.assertEquals(4, result.getModels().size());

        Assert.assertTrue(relationIds.contains(result.getModels().get(0).getId()));
        Assert.assertTrue(relationIds.contains(result.getModels().get(1).getId()));
        Assert.assertTrue(relationIds.contains(result.getModels().get(2).getId()));
        Assert.assertTrue(relationIds.contains(result.getModels().get(3).getId()));

        Assert.assertEquals(result, result.getModels().get(0).getModel());
        Assert.assertEquals(result, result.getModels().get(1).getModel());
        Assert.assertEquals(result, result.getModels().get(2).getModel());
        Assert.assertEquals(result, result.getModels().get(3).getModel());
    }

    @Test
    public void shouldRemoveOneOfManyToOneRelation() {
        OneToManyModel model = new OneToManyModel(1l);
        save(model);

        for (long i : Arrays.asList(1l, 2l, 3l, 4l)) {
            save(new OneToManyRelationModel(i, model));
        }

        OneToManyModel result = findById(OneToManyModel.class, 1l);

        Assert.assertEquals(4, result.getModels().size());

        OneToManyRelationModel.deleteAll(OneToManyRelationModel.class, "id = ?", String.valueOf(3l));

        result = findById(OneToManyModel.class, 1l);

        Assert.assertEquals(3, result.getModels().size());

        Assert.assertTrue(result.getModels().get(0).getId() != 3l);
        Assert.assertTrue(result.getModels().get(1).getId() != 3l);
        Assert.assertTrue(result.getModels().get(2).getId() != 3l);
    }

    @Test
    public void shouldNotRemoveRelation() {
        OneToManyModel model = new OneToManyModel(1l);
        save(model);

        for (long i : Arrays.asList(1l, 2l, 3l, 4l)) {
            save(new OneToManyRelationModel(i, model));
        }

        OneToManyModel result = findById(OneToManyModel.class, 1l);

        result.getModels().clear();

        save(model);

        result = findById(OneToManyModel.class, 1l);

        Assert.assertEquals(4, result.getModels().size());
    }

    @Test
    public void shouldNotAddRelation() {
        List<Long> relationIds = Arrays.asList(1l, 2l, 3l, 4l);
        OneToManyModel model = new OneToManyModel(1l);
        save(model);

        for (long i : relationIds) {
            save(new OneToManyRelationModel(i, model));
        }

        save(new OneToManyRelationModel(5l, null));

        OneToManyModel result = findById(OneToManyModel.class, 1l);

        Assert.assertEquals(4, result.getModels().size());

        Assert.assertTrue(relationIds.contains(result.getModels().get(0).getId()));
        Assert.assertTrue(relationIds.contains(result.getModels().get(1).getId()));
        Assert.assertTrue(relationIds.contains(result.getModels().get(2).getId()));
        Assert.assertTrue(relationIds.contains(result.getModels().get(3).getId()));

        Assert.assertEquals(result, result.getModels().get(0).getModel());
        Assert.assertEquals(result, result.getModels().get(1).getModel());
        Assert.assertEquals(result, result.getModels().get(2).getModel());
        Assert.assertEquals(result, result.getModels().get(3).getModel());
    }

    @Test
    public void shouldNotInflateList() {
        List<Long> relationIds = Arrays.asList(1l, 2l, 3l, 4l);
        WithoutOneToManyAnnotationModel model = new WithoutOneToManyAnnotationModel(1l);
        save(model);

        for (long i : relationIds) {
            save(new WithoutOneToManyAnnotationRelationModel(i, model));
        }

        WithoutOneToManyAnnotationModel result = findById(WithoutOneToManyAnnotationModel.class, 1l);

        Assert.assertEquals(null, result.getModels());
    }
}
