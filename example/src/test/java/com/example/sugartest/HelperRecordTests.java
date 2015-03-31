package com.example.sugartest;


import com.example.models.FloatFieldExtendedModel;
import com.orm.SugarRecord;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertFalse;

@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk=18)
public class HelperRecordTests {
    @Test
    public void firstHelperTest() {

        Float firstObjectFloat = new Float(25F);
        Float lastObjectFloat = new Float(50F);
        save(new FloatFieldExtendedModel(firstObjectFloat));
        save(new FloatFieldExtendedModel(lastObjectFloat));
        FloatFieldExtendedModel model = SugarRecord.first(FloatFieldExtendedModel.class);
        assertEquals(firstObjectFloat, model.getFloat() );
    }

    @Test
    public void lastHelperTest() {
        Float firstObjectFloat = new Float(25F);
        Float lastObjectFloat = new Float(50F);
        save(new FloatFieldExtendedModel(firstObjectFloat));
        save(new FloatFieldExtendedModel(lastObjectFloat));
        FloatFieldExtendedModel model = SugarRecord.last(FloatFieldExtendedModel.class);
        assertEquals(lastObjectFloat, model.getFloat() );
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
        save(new FloatFieldExtendedModel(new Float(25F)));
        FloatFieldExtendedModel firstModel = SugarRecord.first(FloatFieldExtendedModel.class);
        FloatFieldExtendedModel lastModel = SugarRecord.last(FloatFieldExtendedModel.class);
        assertEquals(firstModel.getFloat(), lastModel.getFloat());
    }
}
