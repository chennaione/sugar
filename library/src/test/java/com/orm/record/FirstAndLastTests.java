package com.orm.record;

import com.orm.app.ClientApp;
import com.orm.dsl.BuildConfig;
import com.orm.model.FloatFieldAnnotatedModel;
import com.orm.model.FloatFieldExtendedModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.orm.SugarRecord.save;
import static com.orm.SugarRecord.first;
import static com.orm.SugarRecord.delete;
import static com.orm.SugarRecord.findById;
import static com.orm.SugarRecord.last;

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
        FloatFieldExtendedModel model = first(FloatFieldExtendedModel.class);

        if (null != model) {
            assertEquals(firstObjectFloat, model.getFloat());
        }
    }

    @Test
    public void firstDeletedRecordExtendedTest() {
        Float second = 25F;

        save(new FloatFieldExtendedModel(15F));
        save(new FloatFieldExtendedModel(second));
        save(new FloatFieldExtendedModel(35F));
        save(new FloatFieldExtendedModel(45F));

        FloatFieldExtendedModel firstRecord = findById(FloatFieldExtendedModel.class, 1);
        delete(firstRecord);
        FloatFieldExtendedModel model = first(FloatFieldExtendedModel.class);

        if (null != model) {
            assertEquals(second, model.getFloat());
        }
    }

    @Test
    public void lastExtendedTest() {
        Float last = 50F;

        save(new FloatFieldExtendedModel(25F));
        save(new FloatFieldExtendedModel(last));

        FloatFieldExtendedModel model = last(FloatFieldExtendedModel.class);

        if (null != model) {
            assertEquals(last, model.getFloat());
        }
    }

    @Test
    public void lastDeletedRecordExtendedTest() {
        Float third = 35F;

        save(new FloatFieldExtendedModel(15F));
        save(new FloatFieldExtendedModel(25F));
        save(new FloatFieldExtendedModel(third));
        save(new FloatFieldExtendedModel(45F));

        FloatFieldExtendedModel lastRecord = findById(FloatFieldExtendedModel.class, 4);
        delete(lastRecord);
        FloatFieldExtendedModel model = last(FloatFieldExtendedModel.class);

        if (null != model) {
            assertEquals(third, model.getFloat());
        }
    }

    @Test
    public void nullFirstExtendedTest() {
        assertNull(first(FloatFieldExtendedModel.class));
    }

    @Test
    public void nullLastExtendedTest() {
        assertNull(last(FloatFieldExtendedModel.class));
    }

    @Test
    public void oneItemExtendedTest() {
        save(new FloatFieldExtendedModel(25F));

        FloatFieldExtendedModel firstModel = first(FloatFieldExtendedModel.class);
        FloatFieldExtendedModel lastModel = last(FloatFieldExtendedModel.class);

        if (null != firstModel && null != lastModel) {
            assertEquals(firstModel.getFloat(), lastModel.getFloat());
        }
    }

    @Test
    public void firstAnnotatedTest() {
        Float first = 25F;

        save(new FloatFieldAnnotatedModel(first));
        save(new FloatFieldAnnotatedModel(50F));

        FloatFieldAnnotatedModel model = first(FloatFieldAnnotatedModel.class);

        if (null != model) {
            assertEquals(first, model.getFloat());
        }
    }

    @Test
    public void firstDeletedRecordAnnotatedTest() {
        Float second = 25F;

        save(new FloatFieldAnnotatedModel(15F));
        save(new FloatFieldAnnotatedModel(second));
        save(new FloatFieldAnnotatedModel(35F));
        save(new FloatFieldAnnotatedModel(45F));

        FloatFieldAnnotatedModel firstRecord = findById(FloatFieldAnnotatedModel.class, 1);

        delete(firstRecord);

        FloatFieldAnnotatedModel model = first(FloatFieldAnnotatedModel.class);

        if (null != model) {
            assertEquals(second, model.getFloat());
        }
    }

    @Test
    public void lastAnnotatedTest() {
        Float last = 50F;

        save(new FloatFieldAnnotatedModel(25F));
        save(new FloatFieldAnnotatedModel(last));

        FloatFieldAnnotatedModel model = last(FloatFieldAnnotatedModel.class);

        if (null != model) {
            assertEquals(last, model.getFloat());
        }
    }

    @Test
    public void lastDeletedRecordAnnotatedTest() {
        Float third = 35F;

        save(new FloatFieldAnnotatedModel(15F));
        save(new FloatFieldAnnotatedModel(25F));
        save(new FloatFieldAnnotatedModel(third));
        save(new FloatFieldAnnotatedModel(45F));

        FloatFieldAnnotatedModel lastRecord = findById(FloatFieldAnnotatedModel.class, 4);
        delete(lastRecord);
        FloatFieldAnnotatedModel model = last(FloatFieldAnnotatedModel.class);

        if (null != model) {
            assertEquals(third, model.getFloat());
        }
    }

    @Test
    public void nullFirstAnnotatedTest() {
        assertNull(first(FloatFieldAnnotatedModel.class));
    }

    @Test
    public void nullLastAnnotatedTest() {
        assertNull(last(FloatFieldAnnotatedModel.class));
    }

    @Test
    public void oneItemAnnotatedTest() {
        save(new FloatFieldAnnotatedModel(25F));

        FloatFieldAnnotatedModel first = first(FloatFieldAnnotatedModel.class);
        FloatFieldAnnotatedModel last = last(FloatFieldAnnotatedModel.class);

        if (null != first && null != last) {
            assertEquals(first.getFloat(), last.getFloat());
        }
    }
}
