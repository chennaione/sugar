package com.orm.record;

import com.orm.ClientApp;
import com.orm.RobolectricGradleTestRunner;
import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.orm.models.LongFieldAnnotatedModel;
import com.orm.models.LongFieldExtendedModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk=18, application = ClientApp.class)
public class LongFieldTests {

    @Test
    public void nullLongExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new LongFieldExtendedModel());
        LongFieldExtendedModel model = SugarRecord.findById(LongFieldExtendedModel.class, 1);
        assertNull(model.getLong());
    }

    @Test
    public void nullRawLongExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new LongFieldExtendedModel());
        LongFieldExtendedModel model = SugarRecord.findById(LongFieldExtendedModel.class, 1);
        assertEquals(0L, model.getRawLong());
    }

    @Test
    public void nullLongAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new LongFieldAnnotatedModel());
        LongFieldAnnotatedModel model = SugarRecord.findById(LongFieldAnnotatedModel.class, 1);
        assertNull(model.getLong());
    }

    @Test
    public void nullRawLongAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new LongFieldAnnotatedModel());
        LongFieldAnnotatedModel model = SugarRecord.findById(LongFieldAnnotatedModel.class, 1);
        assertEquals(0L, model.getRawLong());
    }

    @Test
    public void objectLongExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        Long objectLong = 25L;
        save(new LongFieldExtendedModel(objectLong));
        LongFieldExtendedModel model = SugarRecord.findById(LongFieldExtendedModel.class, 1);
        assertEquals(objectLong, model.getLong());
    }

    @Test
    public void rawLongExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new LongFieldExtendedModel(25L));
        LongFieldExtendedModel model = SugarRecord.findById(LongFieldExtendedModel.class, 1);
        assertEquals(25L, model.getRawLong());
    }

    @Test
    public void objectLongAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        Long objectLong = 25L;
        save(new LongFieldAnnotatedModel(objectLong));
        LongFieldAnnotatedModel model = SugarRecord.findById(LongFieldAnnotatedModel.class, 1);
        assertEquals(objectLong, model.getLong());
    }

    @Test
    public void rawLongAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new LongFieldAnnotatedModel(25L));
        LongFieldAnnotatedModel model = SugarRecord.findById(LongFieldAnnotatedModel.class, 1);
        assertEquals(25L, model.getRawLong());
    }
}
