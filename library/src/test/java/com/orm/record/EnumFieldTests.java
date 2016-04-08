package com.orm.record;

import com.orm.ClientApp;
import com.orm.RobolectricGradleTestRunner;
import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.orm.models.EnumFieldAnnotatedModel;
import com.orm.models.EnumFieldExtendedModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, application = ClientApp.class)
public class EnumFieldTests {

    @Test
    public void nullDefaultEnumExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new EnumFieldExtendedModel());
        EnumFieldExtendedModel model = SugarRecord.findById(EnumFieldExtendedModel.class, 1);
        assertNull(model.getDefaultEnum());
    }

    @Test
    public void nullOverriddenEnumExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new EnumFieldExtendedModel());
        EnumFieldExtendedModel model = SugarRecord.findById(EnumFieldExtendedModel.class, 1);
        assertNull(model.getOverrideEnum());
    }
    @Test
    public void nullDefaultEnumAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new EnumFieldAnnotatedModel());
        EnumFieldAnnotatedModel model = SugarRecord.findById(EnumFieldAnnotatedModel.class, 1);
        assertNull(model.getDefaultEnum());
    }

    @Test
    public void nullOverriddenEnumAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new EnumFieldAnnotatedModel());
        EnumFieldAnnotatedModel model = SugarRecord.findById(EnumFieldAnnotatedModel.class, 1);
        assertNull(model.getOverrideEnum());
    }

    @Test
    public void defaultEnumExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new EnumFieldExtendedModel(EnumFieldExtendedModel.OverrideEnum.ONE,
                EnumFieldExtendedModel.DefaultEnum.TWO));
        EnumFieldExtendedModel model = SugarRecord.findById(EnumFieldExtendedModel.class, 1);
        assertNotNull(model);
        assertEquals(model.getDefaultEnum(), EnumFieldExtendedModel.DefaultEnum.TWO);
    }

    @Test
    public void overriddenEnumExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new EnumFieldExtendedModel(EnumFieldExtendedModel.OverrideEnum.ONE,
                EnumFieldExtendedModel.DefaultEnum.TWO));
        EnumFieldExtendedModel model = SugarRecord.findById(EnumFieldExtendedModel.class, 1);
        assertNotNull(model);
        assertEquals(model.getOverrideEnum(), EnumFieldExtendedModel.OverrideEnum.ONE);
    }

    @Test
    public void defaultEnumAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new EnumFieldAnnotatedModel(EnumFieldAnnotatedModel.OverrideEnum.ONE,
                EnumFieldAnnotatedModel.DefaultEnum.TWO));
        EnumFieldAnnotatedModel model = SugarRecord.findById(EnumFieldAnnotatedModel.class, 1);
        assertNotNull(model);
        assertEquals(model.getDefaultEnum(), EnumFieldAnnotatedModel.DefaultEnum.TWO);
    }

    @Test
    public void overriddenEnumAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new EnumFieldAnnotatedModel(EnumFieldAnnotatedModel.OverrideEnum.ONE,
                EnumFieldAnnotatedModel.DefaultEnum.TWO));
        EnumFieldAnnotatedModel model = SugarRecord.findById(EnumFieldAnnotatedModel.class, 1);
        assertNotNull(model);
        assertEquals(model.getOverrideEnum(), EnumFieldAnnotatedModel.OverrideEnum.ONE);
    }
}
