package com.orm.record;

import com.orm.app.ClientApp;
import com.orm.SugarRecord;
import com.orm.dsl.BuildConfig;
import com.orm.model.FloatFieldAnnotatedModel;
import com.orm.model.FloatFieldExtendedModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, constants = BuildConfig.class, application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class FloatFieldTests {

    @Test
    public void nullFloatExtendedTest() {
        save(new FloatFieldExtendedModel());
        FloatFieldExtendedModel model = SugarRecord.findById(FloatFieldExtendedModel.class, 1);
        assertNull(model.getFloat());
    }

    @Test
    public void nullRawFloatExtendedTest() {
        save(new FloatFieldExtendedModel());
        FloatFieldExtendedModel model = SugarRecord.findById(FloatFieldExtendedModel.class, 1);
        assertEquals(0F, model.getRawFloat(), 0.0000000001F);
    }

    @Test
    public void nullFloatAnnotatedTest() {
        save(new FloatFieldAnnotatedModel());
        FloatFieldAnnotatedModel model = SugarRecord.findById(FloatFieldAnnotatedModel.class, 1);
        assertNull(model.getFloat());
    }

    @Test
    public void nullRawFloatAnnotatedTest() {
        save(new FloatFieldAnnotatedModel());
        FloatFieldAnnotatedModel model = SugarRecord.findById(FloatFieldAnnotatedModel.class, 1);
        assertEquals(0F, model.getRawFloat(), 0.0000000001F);
    }

    @Test
    public void objectFloatExtendedTest() {
        Float objectFloat = 25F;
        save(new FloatFieldExtendedModel(objectFloat));
        FloatFieldExtendedModel model = SugarRecord.findById(FloatFieldExtendedModel.class, 1);
        assertEquals(objectFloat, model.getFloat());
    }

    @Test
    public void rawFloatExtendedTest() {
        save(new FloatFieldExtendedModel(25F));
        FloatFieldExtendedModel model = SugarRecord.findById(FloatFieldExtendedModel.class, 1);
        assertEquals(25F, model.getRawFloat(), 0.0000000001F);
    }

    @Test
    public void objectFloatAnnotatedTest() {
        Float objectFloat = 25F;
        save(new FloatFieldAnnotatedModel(objectFloat));
        FloatFieldAnnotatedModel model = SugarRecord.findById(FloatFieldAnnotatedModel.class, 1);
        assertEquals(objectFloat, model.getFloat());
    }

    @Test
    public void rawFloatAnnotatedTest() {
        save(new FloatFieldAnnotatedModel(25F));
        FloatFieldAnnotatedModel model = SugarRecord.findById(FloatFieldAnnotatedModel.class, 1);
        assertEquals(25F, model.getRawFloat(), 0.0000000001F);
    }
}
