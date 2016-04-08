package com.orm.record;

import com.orm.ClientApp;
import com.orm.RobolectricGradleTestRunner;
import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.orm.models.DoubleFieldAnnotatedModel;
import com.orm.models.DoubleFieldExtendedModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk=18, application = ClientApp.class, manifest = Config.NONE)
public class DoubleFieldTests {

    @Before
    public void setUp() {
        SugarContext.init(RuntimeEnvironment.application);
    }

    @Test
    public void nullDoubleExtendedTest() {
        save(new DoubleFieldExtendedModel());
        DoubleFieldExtendedModel model = SugarRecord.findById(DoubleFieldExtendedModel.class, 1);
        assertNull(model.getDouble());
    }

    @Test
    public void nullRawDoubleExtendedTest() {
        save(new DoubleFieldExtendedModel());
        DoubleFieldExtendedModel model = SugarRecord.findById(DoubleFieldExtendedModel.class, 1);
        assertEquals(0.0, model.getRawDouble(), 0.0000000001);
    }

    @Test
    public void nullDoubleAnnotatedTest() {
        save(new DoubleFieldAnnotatedModel());
        DoubleFieldAnnotatedModel model = SugarRecord.findById(DoubleFieldAnnotatedModel.class, 1);
        assertNull(model.getDouble());
    }

    @Test
    public void nullRawDoubleAnnotatedTest() {
        save(new DoubleFieldAnnotatedModel());
        DoubleFieldAnnotatedModel model = SugarRecord.findById(DoubleFieldAnnotatedModel.class, 1);
        assertEquals(0.0, model.getRawDouble(), 0.0000000001);
    }

    @Test
    @SuppressWarnings("all")
    public void objectDoubleExtendedTest() {
        Double objectDouble = Double.valueOf(25.0);
        save(new DoubleFieldExtendedModel(objectDouble));
        DoubleFieldExtendedModel model = SugarRecord.findById(DoubleFieldExtendedModel.class, 1);
        assertEquals(objectDouble, model.getDouble());
    }

    @Test
    public void rawDoubleExtendedTest() {
        save(new DoubleFieldExtendedModel(25.0));
        DoubleFieldExtendedModel model = SugarRecord.findById(DoubleFieldExtendedModel.class, 1);
        assertEquals(25.0, model.getRawDouble(), 0.0000000001);
    }

    @Test
    @SuppressWarnings("all")
    public void objectDoubleAnnotatedTest() {
        Double objectDouble = Double.valueOf(25.0);
        save(new DoubleFieldAnnotatedModel(objectDouble));
        DoubleFieldAnnotatedModel model = SugarRecord.findById(DoubleFieldAnnotatedModel.class, 1);
        assertEquals(objectDouble, model.getDouble());
    }

    @Test
    public void rawDoubleAnnotatedTest() {
        save(new DoubleFieldAnnotatedModel(25.0));
        DoubleFieldAnnotatedModel model = SugarRecord.findById(DoubleFieldAnnotatedModel.class, 1);
        assertEquals(25.0, model.getRawDouble(), 0.0000000001);
    }
}
