package com.orm.record;

import com.orm.ClientApp;
import com.orm.RobolectricGradleTestRunner;
import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.orm.models.BooleanFieldAnnotatedModel;
import com.orm.models.BooleanFieldExtendedModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk=18, application = ClientApp.class)
public class BooleanFieldTests {

    @Test
    public void nullBooleanExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new BooleanFieldExtendedModel());
        BooleanFieldExtendedModel model = SugarRecord.findById(BooleanFieldExtendedModel.class, 1);
        assertNull(model.getBoolean());
    }

    @Test
    public void nullRawBooleanExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new BooleanFieldExtendedModel());
        BooleanFieldExtendedModel model = SugarRecord.findById(BooleanFieldExtendedModel.class, 1);
        assertEquals(false, model.getRawBoolean());
    }

    @Test
    public void nullBooleanAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new BooleanFieldAnnotatedModel());
        BooleanFieldAnnotatedModel model = SugarRecord.findById(BooleanFieldAnnotatedModel.class, 1);
        assertNull(model.getBoolean());
    }

    @Test
    public void nullRawBooleanAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new BooleanFieldAnnotatedModel());
        BooleanFieldAnnotatedModel model = SugarRecord.findById(BooleanFieldAnnotatedModel.class, 1);
        assertEquals(false, model.getRawBoolean());
    }

//TODO check this method
//    @Test
//    public void objectBooleanExtendedTest() {
//        SugarContext.init(RuntimeEnvironment.application);
//        save(new BooleanFieldExtendedModel(true));
//        BooleanFieldExtendedModel model = SugarRecord.findById(BooleanFieldExtendedModel.class, 1);
//        assertEquals(true, model.getBoolean());
//    }

    @Test
    public void rawBooleanExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new BooleanFieldExtendedModel(true));
        BooleanFieldExtendedModel model = SugarRecord.findById(BooleanFieldExtendedModel.class, 1);
        assertEquals(true, model.getRawBoolean());
    }

//TODO check this
//    @Test
//    public void objectBooleanAnnotatedTest() {
//        SugarContext.init(RuntimeEnvironment.application);
//        save(new BooleanFieldAnnotatedModel(true));
//        BooleanFieldAnnotatedModel model = SugarRecord.findById(BooleanFieldAnnotatedModel.class, 1);
//        assertEquals(true, model.getBoolean());
//    }

    @Test
    public void rawBooleanAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new BooleanFieldAnnotatedModel(true));
        BooleanFieldAnnotatedModel model = SugarRecord.findById(BooleanFieldAnnotatedModel.class, 1);
        assertEquals(true, model.getRawBoolean());
    }
}
