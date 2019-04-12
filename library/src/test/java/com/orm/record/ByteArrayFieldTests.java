package com.orm.record;

import com.orm.app.*;
import com.orm.model.*;

import org.junit.*;
import org.junit.runner.*;
import org.robolectric.*;
import org.robolectric.annotation.*;

import static com.orm.SugarRecord.*;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 18, /*constants = BuildConfig.class,*/ application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class ByteArrayFieldTests {

	@Test
	public void nullByteArrayExtendedTest() {
		byte[] array = "".getBytes();
		save(new ByteArrayExtendedModel());
		ByteArrayExtendedModel model = findById(ByteArrayExtendedModel.class, 1);
		assertEquals(new String(array), new String(model.getByteArray()));
		assertArrayEquals(array, model.getByteArray());
	}

	@Test
	public void nullByteArrayAnnotatedTest() {
		byte[] array = "".getBytes();
		save(new ByteArrayAnnotatedModel());
		ByteArrayAnnotatedModel model = findById(ByteArrayAnnotatedModel.class, 1);
		assertEquals(new String(array), new String(model.getByteArray()));
		assertArrayEquals(array, model.getByteArray());
	}

	@Test
	public void byteArrayExtendedTest() {
		byte[] array = "hello".getBytes();
		save(new ByteArrayExtendedModel(array));
		ByteArrayExtendedModel model = findById(ByteArrayExtendedModel.class, 1);
		assertEquals(new String(array), new String(model.getByteArray()));
		assertArrayEquals(array, model.getByteArray());
	}

	@Test
	public void byteArrayAnnotatedTest() {
		byte[] array = "hello".getBytes();
		save(new ByteArrayAnnotatedModel(array));
		ByteArrayAnnotatedModel model = findById(ByteArrayAnnotatedModel.class, 1);
		assertEquals(new String(array), new String(model.getByteArray()));
		assertArrayEquals(array, model.getByteArray());
	}
}
