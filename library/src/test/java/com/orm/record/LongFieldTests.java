package com.orm.record;

import com.orm.app.ClientApp;
import com.orm.SugarRecord;
import com.orm.dsl.BuildConfig;
import com.orm.model.LongFieldAnnotatedModel;
import com.orm.model.LongFieldExtendedModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, constants = BuildConfig.class, application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public class LongFieldTests {

    @Test
    public void nullLongExtendedTest() {
        save(new LongFieldExtendedModel());
        LongFieldExtendedModel model = SugarRecord.findById(LongFieldExtendedModel.class, 1);
        assertNull(model.getLong());
    }

    @Test
    public void nullRawLongExtendedTest() {
        save(new LongFieldExtendedModel());
        LongFieldExtendedModel model = SugarRecord.findById(LongFieldExtendedModel.class, 1);
        assertEquals(0L, model.getRawLong());
    }

    @Test
    public void nullLongAnnotatedTest() {
        save(new LongFieldAnnotatedModel());
        LongFieldAnnotatedModel model = SugarRecord.findById(LongFieldAnnotatedModel.class, 1);
        assertNull(model.getLong());
    }

    @Test
    public void nullRawLongAnnotatedTest() {
        save(new LongFieldAnnotatedModel());
        LongFieldAnnotatedModel model = SugarRecord.findById(LongFieldAnnotatedModel.class, 1);
        assertEquals(0L, model.getRawLong());
    }

    @Test
    public void objectLongExtendedTest() {
        Long objectLong = 25L;
        save(new LongFieldExtendedModel(objectLong));
        LongFieldExtendedModel model = SugarRecord.findById(LongFieldExtendedModel.class, 1);
        assertEquals(objectLong, model.getLong());
    }

    @Test
    public void rawLongExtendedTest() {
        save(new LongFieldExtendedModel(25L));
        LongFieldExtendedModel model = SugarRecord.findById(LongFieldExtendedModel.class, 1);
        assertEquals(25L, model.getRawLong());
    }

    @Test
    public void objectLongAnnotatedTest() {
        Long objectLong = 25L;
        save(new LongFieldAnnotatedModel(objectLong));
        LongFieldAnnotatedModel model = SugarRecord.findById(LongFieldAnnotatedModel.class, 1);
        assertEquals(objectLong, model.getLong());
    }

    @Test
    public void rawLongAnnotatedTest() {
        save(new LongFieldAnnotatedModel(25L));
        LongFieldAnnotatedModel model = SugarRecord.findById(LongFieldAnnotatedModel.class, 1);
        assertEquals(25L, model.getRawLong());
    }
}
