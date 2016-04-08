package com.orm.record;

import com.orm.ClientApp;
import com.orm.RobolectricGradleTestRunner;
import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.orm.models.ShortFieldAnnotatedModel;
import com.orm.models.ShortFieldExtendedModel;

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
public class ShortFieldTests {

    @Before
    public void setUp() {
        SugarContext.init(RuntimeEnvironment.application);
    }

    @Test
    public void nullShortExtendedTest() {
        save(new ShortFieldExtendedModel());
        ShortFieldExtendedModel model = SugarRecord.findById(ShortFieldExtendedModel.class, 1);
        assertNull(model.getShort());
    }

    @Test
    public void nullRawShortExtendedTest() {
        save(new ShortFieldExtendedModel());
        ShortFieldExtendedModel model = SugarRecord.findById(ShortFieldExtendedModel.class, 1);
        assertEquals((short) 0, model.getRawShort());
    }

    @Test
    public void nullShortAnnotatedTest() {
        save(new ShortFieldAnnotatedModel());
        ShortFieldAnnotatedModel model = SugarRecord.findById(ShortFieldAnnotatedModel.class, 1);
        assertNull(model.getShort());
    }

    @Test
    public void nullRawShortAnnotatedTest() {
        save(new ShortFieldAnnotatedModel());
        ShortFieldAnnotatedModel model = SugarRecord.findById(ShortFieldAnnotatedModel.class, 1);
        assertEquals((short) 0, model.getRawShort());
    }

    @Test
    public void objectShortExtendedTest() {
        Short objectShort = 25;
        save(new ShortFieldExtendedModel(objectShort));
        ShortFieldExtendedModel model = SugarRecord.findById(ShortFieldExtendedModel.class, 1);
        assertEquals(objectShort, model.getShort());
    }

    @Test
    public void rawShortExtendedTest() {
        save(new ShortFieldExtendedModel((short) 25));
        ShortFieldExtendedModel model = SugarRecord.findById(ShortFieldExtendedModel.class, 1);
        assertEquals((short) 25, model.getRawShort());
    }

    @Test
    public void objectShortAnnotatedTest() {
        Short objectShort = 25;
        save(new ShortFieldAnnotatedModel(objectShort));
        ShortFieldAnnotatedModel model = SugarRecord.findById(ShortFieldAnnotatedModel.class, 1);
        assertEquals(objectShort, model.getShort());
    }

    @Test
    public void rawShortAnnotatedTest() {
        save(new ShortFieldAnnotatedModel((short) 25));
        ShortFieldAnnotatedModel model = SugarRecord.findById(ShortFieldAnnotatedModel.class, 1);
        assertEquals((short) 25, model.getRawShort());
    }
}
