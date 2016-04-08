package com.orm.record;

import com.orm.ClientApp;
import com.orm.RobolectricGradleTestRunner;
import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.orm.models.StringFieldAnnotatedModel;
import com.orm.models.StringFieldExtendedModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk=18, application = ClientApp.class)
public class StringFieldTests {

    @Before
    public void setUp() {
        SugarContext.init(RuntimeEnvironment.application);
    }


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
