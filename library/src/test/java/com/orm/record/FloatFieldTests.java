package com.orm.record;

import com.orm.ClientApp;
import com.orm.RobolectricGradleTestRunner;
import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.orm.models.FloatFieldAnnotatedModel;
import com.orm.models.FloatFieldExtendedModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk=18, application = ClientApp.class)
public class FloatFieldTests {

    @Test
    public void nullFloatExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new FloatFieldExtendedModel());
        FloatFieldExtendedModel model = SugarRecord.findById(FloatFieldExtendedModel.class, 1);
        assertNull(model.getFloat());
    }

    @Test
    public void nullRawFloatExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new FloatFieldExtendedModel());
        FloatFieldExtendedModel model = SugarRecord.findById(FloatFieldExtendedModel.class, 1);
        assertEquals(0F, model.getRawFloat(), 0.0000000001F);
    }

    @Test
    public void nullFloatAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new FloatFieldAnnotatedModel());
        FloatFieldAnnotatedModel model = SugarRecord.findById(FloatFieldAnnotatedModel.class, 1);
        assertNull(model.getFloat());
    }

    @Test
    public void nullRawFloatAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new FloatFieldAnnotatedModel());
        FloatFieldAnnotatedModel model = SugarRecord.findById(FloatFieldAnnotatedModel.class, 1);
        assertEquals(0F, model.getRawFloat(), 0.0000000001F);
    }

    @Test
    public void objectFloatExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        Float objectFloat = 25F;
        save(new FloatFieldExtendedModel(objectFloat));
        FloatFieldExtendedModel model = SugarRecord.findById(FloatFieldExtendedModel.class, 1);
        assertEquals(objectFloat, model.getFloat());
    }

    @Test
    public void rawFloatExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new FloatFieldExtendedModel(25F));
        FloatFieldExtendedModel model = SugarRecord.findById(FloatFieldExtendedModel.class, 1);
        assertEquals(25F, model.getRawFloat(), 0.0000000001F);
    }

    @Test
    public void objectFloatAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        Float objectFloat = 25F;
        save(new FloatFieldAnnotatedModel(objectFloat));
        FloatFieldAnnotatedModel model = SugarRecord.findById(FloatFieldAnnotatedModel.class, 1);
        assertEquals(objectFloat, model.getFloat());
    }

    @Test
    public void rawFloatAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new FloatFieldAnnotatedModel(25F));
        FloatFieldAnnotatedModel model = SugarRecord.findById(FloatFieldAnnotatedModel.class, 1);
        assertEquals(25F, model.getRawFloat(), 0.0000000001F);
    }
}
