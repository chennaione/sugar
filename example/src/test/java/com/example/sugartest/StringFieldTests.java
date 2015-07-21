package com.example.sugartest;


import com.example.models.StringFieldAnnotatedModel;
import com.example.models.StringFieldExtendedModel;
import com.orm.SugarRecord;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk=18)
public class StringFieldTests {
    @Test
    public void nullStringExtendedTest() {
        save(new StringFieldExtendedModel());
        StringFieldExtendedModel model = SugarRecord.findById(StringFieldExtendedModel.class, 1);
        assertNull(model.getString());
    }

    @Test
    public void nullStringAnnotatedTest() {
        save(new StringFieldAnnotatedModel());
        StringFieldAnnotatedModel model = SugarRecord.findById(StringFieldAnnotatedModel.class, 1);
        assertNull(model.getString());
    }

    @Test
    public void stringExtendedTest() {
        String string = "Test String";
        save(new StringFieldExtendedModel(string));
        StringFieldExtendedModel model = SugarRecord.findById(StringFieldExtendedModel.class, 1);
        assertEquals(string, model.getString());
    }

    @Test
    public void stringAnnotatedTest() {
        String string = "Test String";
        save(new StringFieldAnnotatedModel(string));
        StringFieldAnnotatedModel model = SugarRecord.findById(StringFieldAnnotatedModel.class, 1);
        assertEquals(string, model.getString());
    }
}
