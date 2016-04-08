package com.orm.record;

import com.orm.ClientApp;
import com.orm.RobolectricGradleTestRunner;
import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.orm.models.ByteArrayAnnotatedModel;
import com.orm.models.ByteArrayExtendedModel;

import org.junit.Before;
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


    @Before
    public void setUp() {
        SugarContext.init(RuntimeEnvironment.application);
    }

    @Test
    public void nullByteArrayExtendedTest() {
        byte[] array = "".getBytes();
        save(new ByteArrayExtendedModel());
        ByteArrayExtendedModel model = SugarRecord.findById(ByteArrayExtendedModel.class, 1);
        assertEquals(new String(array), new String(model.getByteArray()));
        assertArrayEquals(array, model.getByteArray());
    }

    @Test
    public void nullByteArrayAnnotatedTest() {
        byte[] array = "".getBytes();
        save(new ByteArrayAnnotatedModel());
        ByteArrayAnnotatedModel model = SugarRecord.findById(ByteArrayAnnotatedModel.class, 1);
        assertEquals(new String(array), new String(model.getByteArray()));
        assertArrayEquals(array, model.getByteArray());
    }

    @Test
    public void byteArrayExtendedTest() {
        byte[] array = "hello".getBytes();
        save(new ByteArrayExtendedModel(array));
        ByteArrayExtendedModel model = SugarRecord.findById(ByteArrayExtendedModel.class, 1);
        assertEquals(new String(array), new String(model.getByteArray()));
        assertArrayEquals(array, model.getByteArray());
    }

    @Test
    public void byteArrayAnnotatedTest() {
        byte[] array = "hello".getBytes();
        save(new ByteArrayAnnotatedModel(array));
        ByteArrayAnnotatedModel model = SugarRecord.findById(ByteArrayAnnotatedModel.class, 1);
        assertEquals(new String(array), new String(model.getByteArray()));
        assertArrayEquals(array, model.getByteArray());
    }
}
