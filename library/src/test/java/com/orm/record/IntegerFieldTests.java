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
public final class IntegerFieldTests {
	private Integer integer = 25;

	@Test
	public void nullIntegerExtendedTest() {
		save(new IntegerFieldExtendedModel());
		IntegerFieldExtendedModel model = findById(IntegerFieldExtendedModel.class, 1);
		assertNull(model.getInteger());
	}

	@Test
	public void nullIntExtendedTest() {
		save(new IntegerFieldExtendedModel());
		IntegerFieldExtendedModel model = findById(IntegerFieldExtendedModel.class, 1);
		assertEquals(0, model.getInt());
	}

	@Test
	public void nullIntegerAnnotatedTest() {
		save(new IntegerFieldAnnotatedModel());
		IntegerFieldAnnotatedModel model = findById(IntegerFieldAnnotatedModel.class, 1);
		assertNull(model.getInteger());
	}

	@Test
	public void nullIntAnnotatedTest() {
		save(new IntegerFieldAnnotatedModel());
		IntegerFieldAnnotatedModel model = findById(IntegerFieldAnnotatedModel.class, 1);
		assertEquals(0, model.getInt());
	}

	@Test
	public void integerExtendedTest() {
		save(new IntegerFieldExtendedModel(integer));
		IntegerFieldExtendedModel model = findById(IntegerFieldExtendedModel.class, 1);
		assertEquals(integer, model.getInteger());
	}

	@Test
	public void intExtendedTest() {
		save(new IntegerFieldExtendedModel(integer.intValue()));
		IntegerFieldExtendedModel model = findById(IntegerFieldExtendedModel.class, 1);
		assertEquals(integer.intValue(), model.getInt());
	}

	@Test
	public void integerAnnotatedTest() {
		save(new IntegerFieldAnnotatedModel(integer));
		IntegerFieldAnnotatedModel model = findById(IntegerFieldAnnotatedModel.class, 1);
		assertEquals(integer, model.getInteger());
	}

	@Test
	public void intAnnotatedTest() {
		save(new IntegerFieldAnnotatedModel(integer.intValue()));
		IntegerFieldAnnotatedModel model = findById(IntegerFieldAnnotatedModel.class, 1);
		assertEquals(integer.intValue(), model.getInt());
	}

	@Test
	public void sumTest() {
		save(new IntegerFieldAnnotatedModel(integer.intValue()));
		save(new IntegerFieldAnnotatedModel(integer.intValue()));
		assertEquals(2 * integer, sum(IntegerFieldAnnotatedModel.class, "raw_integer"));
	}

	@Test
	public void whereSumTest() {
		save(new IntegerFieldAnnotatedModel(integer.intValue()));
		save(new IntegerFieldAnnotatedModel(integer.intValue()));
		assertEquals((long) integer, sum(IntegerFieldAnnotatedModel.class, "raw_integer", "id = ?", "1"));
	}

	@Test
	public void noSumTest() {
		assertEquals(0, sum(IntegerFieldAnnotatedModel.class, "raw_integer"));
	}

	@Test
	public void brokenSumTest() {
		save(new IntegerFieldAnnotatedModel(integer.intValue()));
		save(new IntegerFieldAnnotatedModel(integer.intValue()));
		assertEquals(-1, sum(IntegerFieldAnnotatedModel.class, "wrongfield"));
	}
}
