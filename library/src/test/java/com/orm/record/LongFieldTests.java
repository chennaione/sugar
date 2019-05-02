package com.orm.record;

import com.orm.app.*;
import com.orm.model.*;

import org.junit.*;
import org.junit.runner.*;
import org.robolectric.*;
import org.robolectric.annotation.*;

import static com.orm.SugarRecord.*;
import static org.junit.Assert.*;

@SuppressWarnings("all")
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 18, /*constants = BuildConfig.class,*/ application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class LongFieldTests {
	private Long aLong = Long.valueOf(25L);

	@Test
	public void nullLongExtendedTest() {
		save(new LongFieldExtendedModel());
		LongFieldExtendedModel model = findById(LongFieldExtendedModel.class, 1);
		assertNull(model.getLong());
	}

	@Test
	public void nullRawLongExtendedTest() {
		save(new LongFieldExtendedModel());
		LongFieldExtendedModel model = findById(LongFieldExtendedModel.class, 1);
		assertEquals(0L, model.getRawLong());
	}

	@Test
	public void nullLongAnnotatedTest() {
		save(new LongFieldAnnotatedModel());
		LongFieldAnnotatedModel model = findById(LongFieldAnnotatedModel.class, 1);
		assertNull(model.getLong());
	}

	@Test
	public void nullRawLongAnnotatedTest() {
		save(new LongFieldAnnotatedModel());
		LongFieldAnnotatedModel model = findById(LongFieldAnnotatedModel.class, 1);
		assertEquals(0L, model.getRawLong());
	}

	@Test
	public void objectLongExtendedTest() {
		save(new LongFieldExtendedModel(aLong));
		LongFieldExtendedModel model = findById(LongFieldExtendedModel.class, 1);
		assertEquals(aLong, model.getLong());
	}

	@Test
	public void rawLongExtendedTest() {
		save(new LongFieldExtendedModel(aLong.longValue()));
		LongFieldExtendedModel model = findById(LongFieldExtendedModel.class, 1);
		assertEquals(aLong.longValue(), model.getRawLong());
	}

	@Test
	public void objectLongAnnotatedTest() {
		save(new LongFieldAnnotatedModel(aLong));
		LongFieldAnnotatedModel model = findById(LongFieldAnnotatedModel.class, 1);
		assertEquals(aLong, model.getLong());
	}

	@Test
	public void rawLongAnnotatedTest() {
		save(new LongFieldAnnotatedModel(aLong.longValue()));
		LongFieldAnnotatedModel model = findById(LongFieldAnnotatedModel.class, 1);
		assertEquals(aLong.longValue(), model.getRawLong());
	}
}
