package com.orm.record;

import com.orm.ClientApp;
import com.orm.RobolectricGradleTestRunner;
import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.orm.models.ShortFieldAnnotatedModel;
import com.orm.models.ShortFieldExtendedModel;

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

    @Test
    public void nullShortExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new ShortFieldExtendedModel());
        ShortFieldExtendedModel model = SugarRecord.findById(ShortFieldExtendedModel.class, 1);
        assertNull(model.getShort());
    }

    @Test
    public void nullRawShortExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new ShortFieldExtendedModel());
        ShortFieldExtendedModel model = SugarRecord.findById(ShortFieldExtendedModel.class, 1);
        assertEquals((short) 0, model.getRawShort());
    }

    @Test
    public void nullShortAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new ShortFieldAnnotatedModel());
        ShortFieldAnnotatedModel model = SugarRecord.findById(ShortFieldAnnotatedModel.class, 1);
        assertNull(model.getShort());
    }

    @Test
    public void nullRawShortAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new ShortFieldAnnotatedModel());
        ShortFieldAnnotatedModel model = SugarRecord.findById(ShortFieldAnnotatedModel.class, 1);
        assertEquals((short) 0, model.getRawShort());
    }

    @Test
    public void objectShortExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        Short objectShort = 25;
        save(new ShortFieldExtendedModel(objectShort));
        ShortFieldExtendedModel model = SugarRecord.findById(ShortFieldExtendedModel.class, 1);
        assertEquals(objectShort, model.getShort());
    }

    @Test
    public void rawShortExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new ShortFieldExtendedModel((short) 25));
        ShortFieldExtendedModel model = SugarRecord.findById(ShortFieldExtendedModel.class, 1);
        assertEquals((short) 25, model.getRawShort());
    }

    @Test
    public void objectShortAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        Short objectShort = 25;
        save(new ShortFieldAnnotatedModel(objectShort));
        ShortFieldAnnotatedModel model = SugarRecord.findById(ShortFieldAnnotatedModel.class, 1);
        assertEquals(objectShort, model.getShort());
    }

    @Test
    public void rawShortAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new ShortFieldAnnotatedModel((short) 25));
        ShortFieldAnnotatedModel model = SugarRecord.findById(ShortFieldAnnotatedModel.class, 1);
        assertEquals((short) 25, model.getRawShort());
    }
}
