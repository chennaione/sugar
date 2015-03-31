package com.example.sugartest;


import com.example.models.FloatFieldExtendedModel;
import com.orm.SugarRecord;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk=18)
public class HelperRecordTests {
    @Test
    public void firstHelperTest() {
        save(new FloatFieldExtendedModel(0.25F));
        save(new FloatFieldExtendedModel(0.5F));
        FloatFieldExtendedModel model = SugarRecord.first(FloatFieldExtendedModel.class);
        assertEquals(model.getFloat(), 0.25F);
    }

    @Test
    public void lastHelperTest() {
        save(new FloatFieldExtendedModel(0.25F));
        save(new FloatFieldExtendedModel(0.5F));
        FloatFieldExtendedModel model = SugarRecord.findById(FloatFieldExtendedModel.class);
        assertEquals(model.getFloat(), 0.5F);
    }

    @Test
    public void nullFirstHelperTest() {
        FloatFieldExtendedModel model = SugarRecord.first(FloatFieldExtendedModel.class);
        assertNull(model);
    }

    @Test
    public void nullLastHelperTest() {
        FloatFieldExtendedModel model = SugarRecord.last(FloatFieldExtendedModel.class);
        assertNull(model);
    }

    @Test
    public void oneItemHelperTest() {
        save(new FloatFieldExtendedModel(0.25F));
        FloatFieldExtendedModel firstModel = SugarRecord.first(FloatFieldExtendedModel.class);
        FloatFieldExtendedModel lastModel = SugarRecord.last(FloatFieldExtendedModel.class);
        assertEquals(firstModel.getFloat(), lastModel.getFloat() );
    }
}
