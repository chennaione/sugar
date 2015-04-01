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
        FloatFieldExtendedModel model = FloatFieldExtendedModel.first(FloatFieldExtendedModel.class);
        assertEquals(firstObjectFloat, model.getFloat() );
    }

    @Test
    public void firstHelperDeletedRecordTest() {
        System.out.println("bla");
        Float firstObjectFloat = new Float(15F);
        Float secondObjectFloat = new Float(25F);
        Float thirdObjectFloat = new Float(35F);
        Float fourthObjectFloat = new Float(45F);
        save(new FloatFieldExtendedModel(firstObjectFloat));
        save(new FloatFieldExtendedModel(secondObjectFloat));
        save(new FloatFieldExtendedModel(thirdObjectFloat));
        save(new FloatFieldExtendedModel(fourthObjectFloat));
        FloatFieldExtendedModel firstRecord = FloatFieldExtendedModel.findById(FloatFieldExtendedModel.class,1);
        firstRecord.delete();
        FloatFieldExtendedModel model = FloatFieldExtendedModel.first(FloatFieldExtendedModel.class);
        assertEquals(secondObjectFloat, model.getFloat() );
    }

    @Test
    public void lastHelperTest() {
        Float firstObjectFloat = new Float(25F);
        Float lastObjectFloat = new Float(50F);
        save(new FloatFieldExtendedModel(firstObjectFloat));
        save(new FloatFieldExtendedModel(lastObjectFloat));
        FloatFieldExtendedModel model = FloatFieldExtendedModel.last(FloatFieldExtendedModel.class);
        assertEquals(lastObjectFloat, model.getFloat() );
    }

    @Test
    public void lastHelperDeletedRecordTest() {
        Float firstObjectFloat = new Float(15F);
        Float secondObjectFloat = new Float(25F);
        Float thirdObjectFloat = new Float(35F);
        Float fourthObjectFloat = new Float(45F);
        save(new FloatFieldExtendedModel(firstObjectFloat));
        save(new FloatFieldExtendedModel(secondObjectFloat));
        save(new FloatFieldExtendedModel(thirdObjectFloat));
        save(new FloatFieldExtendedModel(fourthObjectFloat));
        FloatFieldExtendedModel lastRecord = FloatFieldExtendedModel.findById(FloatFieldExtendedModel.class, 4);
        lastRecord.delete();
        FloatFieldExtendedModel model = FloatFieldExtendedModel.last(FloatFieldExtendedModel.class);
        assertEquals(thirdObjectFloat, model.getFloat());
    }

    @Test
    public void nullFirstHelperTest() {
        FloatFieldExtendedModel model = FloatFieldExtendedModel.first(FloatFieldExtendedModel.class);
        assertNull(model);
    }

    @Test
    public void nullLastHelperTest() {
        FloatFieldExtendedModel model = FloatFieldExtendedModel.last(FloatFieldExtendedModel.class);
        assertNull(model);
    }

    @Test
    public void oneItemHelperTest() {
        save(new FloatFieldExtendedModel(new Float(25F)));
        FloatFieldExtendedModel firstModel = FloatFieldExtendedModel.first(FloatFieldExtendedModel.class);
        FloatFieldExtendedModel lastModel = FloatFieldExtendedModel.last(FloatFieldExtendedModel.class);
        assertEquals(firstModel.getFloat(), lastModel.getFloat());
    }
}
