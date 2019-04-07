package com.orm.record;

import com.orm.app.ClientApp;
import com.orm.dsl.BuildConfig;
import com.orm.model.BooleanFieldAnnotatedModel;
import com.orm.model.BooleanFieldExtendedModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.orm.SugarRecord.save;
import static com.orm.SugarRecord.findById;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, constants = BuildConfig.class, application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class BooleanFieldTests {

    @Test
    public void nullBooleanExtendedTest() {
        save(new BooleanFieldExtendedModel());
        BooleanFieldExtendedModel model = findById(BooleanFieldExtendedModel.class, 1);
        assertNull(model.getBoolean());
    }

    @Test
    public void nullRawBooleanExtendedTest() {
        save(new BooleanFieldExtendedModel());
        BooleanFieldExtendedModel model = findById(BooleanFieldExtendedModel.class, 1);
	    assertFalse(model.getRawBoolean());
    }

    @Test
    public void nullBooleanAnnotatedTest() {
        save(new BooleanFieldAnnotatedModel());
        BooleanFieldAnnotatedModel model = findById(BooleanFieldAnnotatedModel.class, 1);
        assertNull(model.getBoolean());
    }

    @Test
    public void nullRawBooleanAnnotatedTest() {
        save(new BooleanFieldAnnotatedModel());
        BooleanFieldAnnotatedModel model = findById(BooleanFieldAnnotatedModel.class, 1);
	    assertFalse(model.getRawBoolean());
    }

////TODO check this method
//    @Test
//    public void objectBooleanExtendedTest() {
//        save(new BooleanFieldExtendedModel(true));
//        BooleanFieldExtendedModel model = SugarRecord.findById(BooleanFieldExtendedModel.class, 1);
//        assertEquals(true, model.getBoolean());
//    }

    @Test
    public void rawBooleanExtendedTest() {
        save(new BooleanFieldExtendedModel(true));
        BooleanFieldExtendedModel model = findById(BooleanFieldExtendedModel.class, 1);
	    assertTrue(model.getRawBoolean());
    }

//    //TODO check this
//    @Test
//    public void objectBooleanAnnotatedTest() {
//        save(new BooleanFieldAnnotatedModel(true));
//        BooleanFieldAnnotatedModel model = SugarRecord.findById(BooleanFieldAnnotatedModel.class, 1);
//
//        if (null != model) {
//            assertEquals(true, model.getBoolean());
//        }
//    }

    @Test
    public void rawBooleanAnnotatedTest() {
        save(new BooleanFieldAnnotatedModel(true));
        BooleanFieldAnnotatedModel model = findById(BooleanFieldAnnotatedModel.class, 1);
	    assertTrue(model.getRawBoolean());
    }
}
