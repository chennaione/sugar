package com.example.sugartest;

import com.example.models.EnumModel;
import com.orm.SugarRecord;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk = 18)
public class EnumFieldTests {

    @Test
    public void defaultEnums() {
        save(new EnumModel(EnumModel.OverrideEnum.ONE, EnumModel.DefaultEnum.TWO));
        EnumModel model = SugarRecord.findById(EnumModel.class, 1);
        assertNotNull(model);
        assertEquals(model.getDefaultEnum(), EnumModel.DefaultEnum.TWO);
    }

    @Test
    public void overridenEnums() {
        save(new EnumModel(EnumModel.OverrideEnum.ONE, EnumModel.DefaultEnum.TWO));
        EnumModel model = SugarRecord.findById(EnumModel.class, 1);
        assertNotNull(model);
        assertEquals(model.getOverrideEnum(), EnumModel.OverrideEnum.ONE);
    }

}
