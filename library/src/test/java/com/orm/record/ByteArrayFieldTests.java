package com.orm.record;

import com.orm.ClientApp;
import com.orm.RobolectricGradleTestRunner;
import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.orm.models.ByteArrayAnnotatedModel;
import com.orm.models.ByteArrayExtendedModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk=18, application = ClientApp.class)
public class ByteArrayFieldTests {

    @Test
    public void nullByteArrayExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        byte[] array = "".getBytes();
        save(new ByteArrayExtendedModel());
        ByteArrayExtendedModel model = SugarRecord.findById(ByteArrayExtendedModel.class, 1);
        assertEquals(new String(array), new String(model.getByteArray()));
        assertArrayEquals(array, model.getByteArray());
    }

    @Test
    public void nullByteArrayAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        byte[] array = "".getBytes();
        save(new ByteArrayAnnotatedModel());
        ByteArrayAnnotatedModel model = SugarRecord.findById(ByteArrayAnnotatedModel.class, 1);
        assertEquals(new String(array), new String(model.getByteArray()));
        assertArrayEquals(array, model.getByteArray());
    }

    @Test
    public void byteArrayExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        byte[] array = "hello".getBytes();
        save(new ByteArrayExtendedModel(array));
        ByteArrayExtendedModel model = SugarRecord.findById(ByteArrayExtendedModel.class, 1);
        assertEquals(new String(array), new String(model.getByteArray()));
        assertArrayEquals(array, model.getByteArray());
    }

    @Test
    public void byteArrayAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        byte[] array = "hello".getBytes();
        save(new ByteArrayAnnotatedModel(array));
        ByteArrayAnnotatedModel model = SugarRecord.findById(ByteArrayAnnotatedModel.class, 1);
        assertEquals(new String(array), new String(model.getByteArray()));
        assertArrayEquals(array, model.getByteArray());
    }
}
