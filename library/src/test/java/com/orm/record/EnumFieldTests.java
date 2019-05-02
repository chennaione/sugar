package com.orm.record;

import com.orm.app.*;
import com.orm.model.*;

import org.junit.*;
import org.junit.runner.*;
import org.robolectric.*;
import org.robolectric.annotation.*;

import static com.orm.SugarRecord.*;
import static com.orm.model.EnumFieldExtendedModel.*;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 18, /*constants = BuildConfig.class,*/ application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class EnumFieldTests {

	@Test
	public void nullDefaultEnumExtendedTest() {
		save(new EnumFieldExtendedModel());
		EnumFieldExtendedModel model = findById(EnumFieldExtendedModel.class, 1);
		assertNull(model.getDefaultEnum());
	}

	@Test
	public void nullOverriddenEnumExtendedTest() {
		save(new EnumFieldExtendedModel());
		EnumFieldExtendedModel model = findById(EnumFieldExtendedModel.class, 1);
		assertNull(model.getOverrideEnum());
	}
	@Test
	public void nullDefaultEnumAnnotatedTest() {
		save(new EnumFieldAnnotatedModel());
		EnumFieldAnnotatedModel model = findById(EnumFieldAnnotatedModel.class, 1);
		assertNull(model.getDefaultEnum());
	}

	@Test
	public void nullOverriddenEnumAnnotatedTest() {
		save(new EnumFieldAnnotatedModel());
		EnumFieldAnnotatedModel model = findById(EnumFieldAnnotatedModel.class, 1);
		assertNull(model.getOverrideEnum());
	}

	@Test
	public void defaultEnumExtendedTest() {
		save(new EnumFieldExtendedModel(OverrideEnum.ONE, DefaultEnum.TWO));
		EnumFieldExtendedModel model = findById(EnumFieldExtendedModel.class, 1);
		assertNotNull(model);
		assertEquals(model.getDefaultEnum(), DefaultEnum.TWO);
	}

	@Test
	public void overriddenEnumExtendedTest() {
		save(new EnumFieldExtendedModel(OverrideEnum.ONE, DefaultEnum.TWO));
		EnumFieldExtendedModel model = findById(EnumFieldExtendedModel.class, 1);
		assertNotNull(model);
		assertEquals(model.getOverrideEnum(), OverrideEnum.ONE);
	}

	@Test
	public void defaultEnumAnnotatedTest() {
		save(new EnumFieldAnnotatedModel(EnumFieldAnnotatedModel.OverrideEnum.ONE, EnumFieldAnnotatedModel.DefaultEnum.TWO));
		EnumFieldAnnotatedModel model = findById(EnumFieldAnnotatedModel.class, 1);
		assertNotNull(model);
		assertEquals(model.getDefaultEnum(), EnumFieldAnnotatedModel.DefaultEnum.TWO);
	}

	@Test
	public void overriddenEnumAnnotatedTest() {
		save(new EnumFieldAnnotatedModel(EnumFieldAnnotatedModel.OverrideEnum.ONE, EnumFieldAnnotatedModel.DefaultEnum.TWO));
		EnumFieldAnnotatedModel model = findById(EnumFieldAnnotatedModel.class, 1);
		assertNotNull(model);
		assertEquals(model.getOverrideEnum(), EnumFieldAnnotatedModel.OverrideEnum.ONE);
	}
}
