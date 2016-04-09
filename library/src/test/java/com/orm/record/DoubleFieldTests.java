package com.orm.record;

import com.orm.app.ClientApp;
import com.orm.SugarRecord;
import com.orm.dsl.BuildConfig;
import com.orm.model.DoubleFieldAnnotatedModel;
import com.orm.model.DoubleFieldExtendedModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.orm.SugarRecord.save;
import static com.orm.SugarRecord.findById;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, constants = BuildConfig.class, application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class DoubleFieldTests {

    @Test
    public void nullDoubleExtendedTest() {
        save(new DoubleFieldExtendedModel());
        DoubleFieldExtendedModel model = findById(DoubleFieldExtendedModel.class, 1);
        assertNull(model.getDouble());
    }

    @Test
    public void nullRawDoubleExtendedTest() {
        save(new DoubleFieldExtendedModel());
        DoubleFieldExtendedModel model = findById(DoubleFieldExtendedModel.class, 1);
        assertEquals(0.0, model.getRawDouble(), 0.0000000001);
    }

    @Test
    public void nullDoubleAnnotatedTest() {
        save(new DoubleFieldAnnotatedModel());
        DoubleFieldAnnotatedModel model = findById(DoubleFieldAnnotatedModel.class, 1);
        assertNull(model.getDouble());
    }

    @Test
    public void nullRawDoubleAnnotatedTest() {
        save(new DoubleFieldAnnotatedModel());
        DoubleFieldAnnotatedModel model = findById(DoubleFieldAnnotatedModel.class, 1);
        assertEquals(0.0, model.getRawDouble(), 0.0000000001);
    }

    @Test
    @SuppressWarnings("all")
    public void objectDoubleExtendedTest() {
        Double objectDouble = Double.valueOf(25.0);
        save(new DoubleFieldExtendedModel(objectDouble));
        DoubleFieldExtendedModel model = findById(DoubleFieldExtendedModel.class, 1);
        assertEquals(objectDouble, model.getDouble());
    }

    @Test
    public void rawDoubleExtendedTest() {
        save(new DoubleFieldExtendedModel(25.0));
        DoubleFieldExtendedModel model = findById(DoubleFieldExtendedModel.class, 1);
        assertEquals(25.0, model.getRawDouble(), 0.0000000001);
    }

    @Test
    @SuppressWarnings("all")
    public void objectDoubleAnnotatedTest() {
        Double objectDouble = Double.valueOf(25.0);
        save(new DoubleFieldAnnotatedModel(objectDouble));
        DoubleFieldAnnotatedModel model = findById(DoubleFieldAnnotatedModel.class, 1);
        assertEquals(objectDouble, model.getDouble());
    }

    @Test
    public void rawDoubleAnnotatedTest() {
        save(new DoubleFieldAnnotatedModel(25.0));
        DoubleFieldAnnotatedModel model = findById(DoubleFieldAnnotatedModel.class, 1);
        assertEquals(25.0, model.getRawDouble(), 0.0000000001);
    }
}
