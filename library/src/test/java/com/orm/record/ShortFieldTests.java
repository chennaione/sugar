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
public final class ShortFieldTests {
	private Short aShort = Short.valueOf((short) 25);

	@Test
	public void nullShortExtendedTest() {
		save(new ShortFieldExtendedModel());
		ShortFieldExtendedModel model = findById(ShortFieldExtendedModel.class, 1);
		assertNull(model.getShort());
	}

	@Test
	public void nullRawShortExtendedTest() {
		save(new ShortFieldExtendedModel());
		ShortFieldExtendedModel model = findById(ShortFieldExtendedModel.class, 1);
		assertEquals((short) 0, model.getRawShort());
	}

	@Test
	public void nullShortAnnotatedTest() {
		save(new ShortFieldAnnotatedModel());
		ShortFieldAnnotatedModel model = findById(ShortFieldAnnotatedModel.class, 1);
		assertNull(model.getShort());
	}

	@Test
	public void nullRawShortAnnotatedTest() {
		save(new ShortFieldAnnotatedModel());
		ShortFieldAnnotatedModel model = findById(ShortFieldAnnotatedModel.class, 1);
		assertEquals((short) 0, model.getRawShort());
	}

	@Test
	public void objectShortExtendedTest() {
		save(new ShortFieldExtendedModel(aShort));
		ShortFieldExtendedModel model = findById(ShortFieldExtendedModel.class, 1);
		assertEquals(aShort, model.getShort());
	}

	@Test
	public void rawShortExtendedTest() {
		save(new ShortFieldExtendedModel(aShort.shortValue()));
		ShortFieldExtendedModel model = findById(ShortFieldExtendedModel.class, 1);
		assertEquals(aShort.shortValue(), model.getRawShort());
	}

	@Test
	public void objectShortAnnotatedTest() {
		save(new ShortFieldAnnotatedModel(aShort));
		ShortFieldAnnotatedModel model = findById(ShortFieldAnnotatedModel.class, 1);
		assertEquals(aShort, model.getShort());
	}

	@Test
	public void rawShortAnnotatedTest() {
		save(new ShortFieldAnnotatedModel(aShort.shortValue()));
		ShortFieldAnnotatedModel model = findById(ShortFieldAnnotatedModel.class, 1);
		assertEquals(aShort.shortValue(), model.getRawShort());
	}
}
