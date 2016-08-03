package com.orm.record;

import com.orm.app.ClientApp;
import com.orm.dsl.BuildConfig;
import com.orm.model.ManyToOneModel;
import com.orm.model.OneToManyModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by Łukasz Wesołowski on 28.07.2016.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, constants = BuildConfig.class, application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public class OneToManyTest {
    @Test
    public void saveOneToManyRelationTest() {
        OneToManyModel oneToManyModel = new OneToManyModel();
        oneToManyModel.setId(1l);
        oneToManyModel.setName("oneToMany");
        oneToManyModel.save();

        ManyToOneModel manyToOneModel1 = new ManyToOneModel();
        manyToOneModel1.setId(1l);
        manyToOneModel1.setName("manyToOne 1");
        manyToOneModel1.setModel(oneToManyModel);
        manyToOneModel1.save();

        ManyToOneModel manyToOneModel2 = new ManyToOneModel();
        manyToOneModel2.setId(2l);
        manyToOneModel2.setName("manyToOne 2");
        manyToOneModel2.setModel(oneToManyModel);
        manyToOneModel2.save();

        ManyToOneModel manyToOneModel3 = new ManyToOneModel();
        manyToOneModel3.setId(3l);
        manyToOneModel3.setName("manyToOne 3");
        manyToOneModel3.setModel(oneToManyModel);
        manyToOneModel3.save();

        OneToManyModel result = OneToManyModel.findById(OneToManyModel.class, 1l);

        Assert.assertEquals(3, result.getModels().size());

        Assert.assertTrue(result.getModels().contains(manyToOneModel1));
        Assert.assertTrue(result.getModels().contains(manyToOneModel2));
        Assert.assertTrue(result.getModels().contains(manyToOneModel3));

        Assert.assertEquals(result, result.getModels().get(0).getModel());
        Assert.assertEquals(result, result.getModels().get(1).getModel());
        Assert.assertEquals(result, result.getModels().get(2).getModel());
    }

    @Test
    public void removeOneToManyRelationTest() {
        OneToManyModel oneToManyModel = new OneToManyModel();
        oneToManyModel.setId(1l);
        oneToManyModel.setName("oneToMany");
        oneToManyModel.save();

        ManyToOneModel manyToOneModel1 = new ManyToOneModel();
        manyToOneModel1.setId(1l);
        manyToOneModel1.setName("manyToOne 1");
        manyToOneModel1.setModel(oneToManyModel);
        manyToOneModel1.save();

        ManyToOneModel manyToOneModel2 = new ManyToOneModel();
        manyToOneModel2.setId(2l);
        manyToOneModel2.setName("manyToOne 2");
        manyToOneModel2.setModel(oneToManyModel);
        manyToOneModel2.save();

        ManyToOneModel manyToOneModel3 = new ManyToOneModel();
        manyToOneModel3.setId(3l);
        manyToOneModel3.setName("manyToOne 3");
        manyToOneModel3.setModel(oneToManyModel);
        manyToOneModel3.save();

        OneToManyModel result = OneToManyModel.findById(OneToManyModel.class, 1l);

        Assert.assertEquals(3, result.getModels().size());

        ManyToOneModel.delete(manyToOneModel2);

        result = OneToManyModel.findById(OneToManyModel.class, 1l);

        Assert.assertEquals(2, result.getModels().size());

        Assert.assertTrue(result.getModels().contains(manyToOneModel1));
        Assert.assertTrue(result.getModels().contains(manyToOneModel3));
    }
}
