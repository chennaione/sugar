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
public final class StringFieldTests {
	private String string = "Test String";

	@Test
	public void nullStringExtendedTest() {
		save(new StringFieldExtendedModel());
		StringFieldExtendedModel model = findById(StringFieldExtendedModel.class, 1);
		assertNull(model.getString());
	}

	@Test
	public void nullStringAnnotatedTest() {
		save(new StringFieldAnnotatedModel());
		StringFieldAnnotatedModel model = findById(StringFieldAnnotatedModel.class, 1);
		assertNull(model.getString());
	}

	@Test
	public void stringExtendedTest() {
		save(new StringFieldExtendedModel(string));
		StringFieldExtendedModel model = findById(StringFieldExtendedModel.class, 1);
		assertEquals(string, model.getString());
	}

	@Test
	public void stringAnnotatedTest() {
		save(new StringFieldAnnotatedModel(string));
		StringFieldAnnotatedModel model = findById(StringFieldAnnotatedModel.class, 1);
		assertEquals(string, model.getString());
	}
}
