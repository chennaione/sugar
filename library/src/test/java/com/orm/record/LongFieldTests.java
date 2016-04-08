package com.orm.record;

import com.orm.ClientApp;
import com.orm.RobolectricGradleTestRunner;
import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.orm.models.LongFieldAnnotatedModel;
import com.orm.models.LongFieldExtendedModel;

import org.junit.Before;
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

    @Before
    public void setUp() {
        SugarContext.init(RuntimeEnvironment.application);
    }

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
