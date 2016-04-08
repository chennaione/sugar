package com.orm.record;

import com.orm.ClientApp;
import com.orm.RobolectricGradleTestRunner;
import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.orm.models.IntegerFieldAnnotatedModel;
import com.orm.models.IntegerFieldExtendedModel;

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
public class IntegerFieldTests {

    @Before
    public void setUp() {
        SugarContext.init(RuntimeEnvironment.application);
    }

    @Test
    public void nullIntegerExtendedTest() {
        save(new IntegerFieldExtendedModel());
        IntegerFieldExtendedModel model = SugarRecord.findById(IntegerFieldExtendedModel.class, 1);
        assertNull(model.getInteger());
    }

    @Test
    public void nullIntExtendedTest() {
        save(new IntegerFieldExtendedModel());
        IntegerFieldExtendedModel model = SugarRecord.findById(IntegerFieldExtendedModel.class, 1);
        assertEquals(0, model.getInt());
    }

    @Test
    public void nullIntegerAnnotatedTest() {
        save(new IntegerFieldAnnotatedModel());
        IntegerFieldAnnotatedModel model = SugarRecord.findById(IntegerFieldAnnotatedModel.class, 1);
        assertNull(model.getInteger());
    }

    @Test
    public void nullIntAnnotatedTest() {
        save(new IntegerFieldAnnotatedModel());
        IntegerFieldAnnotatedModel model = SugarRecord.findById(IntegerFieldAnnotatedModel.class, 1);
        assertEquals(0, model.getInt());
    }

    @Test
    public void integerExtendedTest() {
        Integer integer = 25;
        save(new IntegerFieldExtendedModel(integer));
        IntegerFieldExtendedModel model = SugarRecord.findById(IntegerFieldExtendedModel.class, 1);
        assertEquals(integer, model.getInteger());
    }

    @Test
    public void intExtendedTest() {
        save(new IntegerFieldExtendedModel(25));
        IntegerFieldExtendedModel model = SugarRecord.findById(IntegerFieldExtendedModel.class, 1);
        assertEquals(25, model.getInt());
    }

    @Test
    public void integerAnnotatedTest() {
        Integer integer = 25;
        save(new IntegerFieldAnnotatedModel(integer));
        IntegerFieldAnnotatedModel model = SugarRecord.findById(IntegerFieldAnnotatedModel.class, 1);
        assertEquals(integer, model.getInteger());
    }

    @Test
    public void intAnnotatedTest() {
        save(new IntegerFieldAnnotatedModel(25));
        IntegerFieldAnnotatedModel model = SugarRecord.findById(IntegerFieldAnnotatedModel.class, 1);
        assertEquals(25, model.getInt());
    }
}
