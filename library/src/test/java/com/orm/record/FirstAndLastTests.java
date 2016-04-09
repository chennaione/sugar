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
public class FirstAndLastTests {

    @Test
    @SuppressWarnings("all")
    public void firstExtendedTest() {
        Float firstObjectFloat = 25F;
        Float lastObjectFloat = 50F;
        save(new FloatFieldExtendedModel(firstObjectFloat));
        save(new FloatFieldExtendedModel(lastObjectFloat));
        FloatFieldExtendedModel model = SugarRecord.first(FloatFieldExtendedModel.class);

        if (null != model) {
            assertEquals(firstObjectFloat, model.getFloat());
        }
    }

    @Test
    public void firstDeletedRecordExtendedTest() {
        Float firstObjectFloat = 15F;
        Float secondObjectFloat = 25F;
        Float thirdObjectFloat = 35F;
        Float fourthObjectFloat = 45F;
        save(new FloatFieldExtendedModel(firstObjectFloat));
        save(new FloatFieldExtendedModel(secondObjectFloat));
        save(new FloatFieldExtendedModel(thirdObjectFloat));
        save(new FloatFieldExtendedModel(fourthObjectFloat));
        FloatFieldExtendedModel firstRecord = SugarRecord.findById(FloatFieldExtendedModel.class, 1);
        firstRecord.delete();
        FloatFieldExtendedModel model = SugarRecord.first(FloatFieldExtendedModel.class);

        if (null != model) {
            assertEquals(secondObjectFloat, model.getFloat());
        }
    }

    @Test
    public void lastExtendedTest() {
        Float firstObjectFloat = 25F;
        Float lastObjectFloat = 50F;
        save(new FloatFieldExtendedModel(firstObjectFloat));
        save(new FloatFieldExtendedModel(lastObjectFloat));
        FloatFieldExtendedModel model = SugarRecord.last(FloatFieldExtendedModel.class);

        if (null != model) {
            assertEquals(lastObjectFloat, model.getFloat());
        }
    }

    @Test
    public void lastDeletedRecordExtendedTest() {
        Float firstObjectFloat = 15F;
        Float secondObjectFloat = 25F;
        Float thirdObjectFloat = 35F;
        Float fourthObjectFloat = 45F;
        save(new FloatFieldExtendedModel(firstObjectFloat));
        save(new FloatFieldExtendedModel(secondObjectFloat));
        save(new FloatFieldExtendedModel(thirdObjectFloat));
        save(new FloatFieldExtendedModel(fourthObjectFloat));
        FloatFieldExtendedModel lastRecord = SugarRecord.findById(FloatFieldExtendedModel.class, 4);
        lastRecord.delete();
        FloatFieldExtendedModel model = SugarRecord.last(FloatFieldExtendedModel.class);

        if (null != model) {
            assertEquals(thirdObjectFloat, model.getFloat());
        }
    }

    @Test
    public void nullFirstExtendedTest() {
        assertNull(SugarRecord.first(FloatFieldExtendedModel.class));
    }

    @Test
    public void nullLastExtendedTest() {
        assertNull(SugarRecord.last(FloatFieldExtendedModel.class));
    }

    @Test
    public void oneItemExtendedTest() {
        save(new FloatFieldExtendedModel(25F));
        FloatFieldExtendedModel firstModel = SugarRecord.first(FloatFieldExtendedModel.class);
        FloatFieldExtendedModel lastModel = SugarRecord.last(FloatFieldExtendedModel.class);

        if (null != firstModel && null != lastModel) {
            assertEquals(firstModel.getFloat(), lastModel.getFloat());
        }
    }

    @Test
    public void firstAnnotatedTest() {
        Float firstObjectFloat = 25F;
        Float lastObjectFloat = 50F;
        save(new FloatFieldAnnotatedModel(firstObjectFloat));
        save(new FloatFieldAnnotatedModel(lastObjectFloat));
        FloatFieldAnnotatedModel model = SugarRecord.first(FloatFieldAnnotatedModel.class);

        if (null != model) {
            assertEquals(firstObjectFloat, model.getFloat());
        }
    }

    @Test
    public void firstDeletedRecordAnnotatedTest() {
        Float firstObjectFloat = 15F;
        Float secondObjectFloat = 25F;
        Float thirdObjectFloat = 35F;
        Float fourthObjectFloat = 45F;
        save(new FloatFieldAnnotatedModel(firstObjectFloat));
        save(new FloatFieldAnnotatedModel(secondObjectFloat));
        save(new FloatFieldAnnotatedModel(thirdObjectFloat));
        save(new FloatFieldAnnotatedModel(fourthObjectFloat));
        FloatFieldAnnotatedModel firstRecord = SugarRecord.findById(FloatFieldAnnotatedModel.class, 1);
        SugarRecord.delete(firstRecord);
        FloatFieldAnnotatedModel model = SugarRecord.first(FloatFieldAnnotatedModel.class);

        if (null != model) {
            assertEquals(secondObjectFloat, model.getFloat());
        }
    }

    @Test
    public void lastAnnotatedTest() {
        Float firstObjectFloat = 25F;
        Float lastObjectFloat = 50F;
        save(new FloatFieldAnnotatedModel(firstObjectFloat));
        save(new FloatFieldAnnotatedModel(lastObjectFloat));
        FloatFieldAnnotatedModel model = SugarRecord.last(FloatFieldAnnotatedModel.class);

        if (null != model) {
            assertEquals(lastObjectFloat, model.getFloat());
        }
    }

    @Test
    public void lastDeletedRecordAnnotatedTest() {
        Float firstObjectFloat = 15F;
        Float secondObjectFloat = 25F;
        Float thirdObjectFloat = 35F;
        Float fourthObjectFloat = 45F;
        save(new FloatFieldAnnotatedModel(firstObjectFloat));
        save(new FloatFieldAnnotatedModel(secondObjectFloat));
        save(new FloatFieldAnnotatedModel(thirdObjectFloat));
        save(new FloatFieldAnnotatedModel(fourthObjectFloat));
        FloatFieldAnnotatedModel lastRecord = SugarRecord.findById(FloatFieldAnnotatedModel.class, 4);
        SugarRecord.delete(lastRecord);
        FloatFieldAnnotatedModel model = SugarRecord.last(FloatFieldAnnotatedModel.class);

        if (null != model) {
            assertEquals(thirdObjectFloat, model.getFloat());
        }
    }

    @Test
    public void nullFirstAnnotatedTest() {
        assertNull(SugarRecord.first(FloatFieldAnnotatedModel.class));
    }

    @Test
    public void nullLastAnnotatedTest() {
        assertNull(SugarRecord.last(FloatFieldAnnotatedModel.class));
    }

    @Test
    public void oneItemAnnotatedTest() {
        save(new FloatFieldAnnotatedModel(25F));
        FloatFieldAnnotatedModel firstModel = SugarRecord.first(FloatFieldAnnotatedModel.class);
        FloatFieldAnnotatedModel lastModel = SugarRecord.last(FloatFieldAnnotatedModel.class);

        if (null != firstModel && null != lastModel) {
            assertEquals(firstModel.getFloat(), lastModel.getFloat());
        }
    }
}
