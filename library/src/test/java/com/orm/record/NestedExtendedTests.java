package com.orm.record;

import com.orm.app.*;
import com.orm.model.*;

import org.junit.*;
import org.junit.runner.*;
import org.robolectric.*;
import org.robolectric.annotation.*;

import java.util.*;

import static com.orm.SugarRecord.*;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 18, /*constants = BuildConfig.class,*/ application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class NestedExtendedTests {

	@Test
	public void emptyDatabaseTest() throws Exception {
		assertEquals(0L, count(NestedExtendedModel.class));
		assertEquals(0L, count(RelationshipExtendedModel.class));
		assertEquals(0L, count(SimpleExtendedModel.class));
	}

	@Test
	public void oneSaveTest() throws Exception {
		SimpleExtendedModel simple = new SimpleExtendedModel();
		save(simple);
		RelationshipExtendedModel nested = new RelationshipExtendedModel(simple);
		save(nested);
		save(new NestedExtendedModel(nested));
		assertEquals(1L, count(SimpleExtendedModel.class));
		assertEquals(1L, count(RelationshipExtendedModel.class));
		assertEquals(1L, count(NestedExtendedModel.class));
	}

	@Test
	public void twoSameSaveTest() throws Exception {
		SimpleExtendedModel simple = new SimpleExtendedModel();
		save(simple);
		RelationshipExtendedModel nested = new RelationshipExtendedModel(simple);
		save(nested);
		save(new NestedExtendedModel(nested));
		save(new NestedExtendedModel(nested));
		assertEquals(1L, count(SimpleExtendedModel.class));
		assertEquals(1L, count(RelationshipExtendedModel.class));
		assertEquals(2L, count(NestedExtendedModel.class));
	}

	@Test
	public void twoDifferentSaveTest() throws Exception {
		SimpleExtendedModel simple = new SimpleExtendedModel();
		save(simple);
		SimpleExtendedModel anotherSimple = new SimpleExtendedModel();
		save(anotherSimple);
		RelationshipExtendedModel nested = new RelationshipExtendedModel(simple);
		save(nested);
		RelationshipExtendedModel anotherNested = new RelationshipExtendedModel(anotherSimple);
		save(anotherNested);
		save(new NestedExtendedModel(nested));
		save(new NestedExtendedModel(anotherNested));
		assertEquals(2L, count(SimpleExtendedModel.class));
		assertEquals(2L, count(RelationshipExtendedModel.class));
		assertEquals(2L, count(NestedExtendedModel.class));
	}

	@Test
	public void manySameSaveTest() throws Exception {
		SimpleExtendedModel simple = new SimpleExtendedModel();
		save(simple);
		RelationshipExtendedModel nested = new RelationshipExtendedModel(simple);
		save(nested);
		for (int i = 1; i <= 100; i++) {
			save(new NestedExtendedModel(nested));
		}
		assertEquals(1L, count(SimpleExtendedModel.class));
		assertEquals(1L, count(RelationshipExtendedModel.class));
		assertEquals(100L, count(NestedExtendedModel.class));
	}

	@Test
	public void manyDifferentSaveTest() throws Exception {
		for (int i = 1; i <= 100; i++) {
			SimpleExtendedModel simple = new SimpleExtendedModel();
			save(simple);
			RelationshipExtendedModel nested = new RelationshipExtendedModel(simple);
			save(nested);
			save(new NestedExtendedModel(nested));
		}
		assertEquals(100L, count(SimpleExtendedModel.class));
		assertEquals(100L, count(RelationshipExtendedModel.class));
		assertEquals(100L, count(NestedExtendedModel.class));
	}

	@Test
	public void listAllSameTest() throws Exception {
		SimpleExtendedModel simple = new SimpleExtendedModel();
		save(simple);
		RelationshipExtendedModel nested = new RelationshipExtendedModel(simple);
		save(nested);
		for (int i = 1; i <= 100; i++) {
			save(new NestedExtendedModel(nested));
		}

		List<NestedExtendedModel> models = listAll(NestedExtendedModel.class);
		assertEquals(100, models.size());

		for (NestedExtendedModel model : models) {
			assertEquals(nested.getId(), model.getNested().getId());
			assertEquals(simple.getId(), model.getNested().getSimple().getId());
		}
	}

	@Test
	public void listAllDifferentTest() throws Exception {
		for (int i = 1; i <= 100; i++) {
			SimpleExtendedModel simple = new SimpleExtendedModel();
			save(simple);
			RelationshipExtendedModel nested = new RelationshipExtendedModel(simple);
			save(nested);
			save(new NestedExtendedModel(nested));
		}

		List<NestedExtendedModel> models = listAll(NestedExtendedModel.class);
		assertEquals(100, models.size());

		for (NestedExtendedModel model : models) {
			assertEquals(model.getId(), model.getNested().getId());
			assertEquals(model.getId(), model.getNested().getSimple().getId());
		}
	}
}
