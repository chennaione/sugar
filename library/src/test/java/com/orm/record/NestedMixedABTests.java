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
public class NestedMixedABTests {

	@Test
	public void emptyDatabaseTest() throws Exception {
		assertEquals(0L, count(NestedMixedABModel.class));
		assertEquals(0L, count(RelationshipMixedBModel.class));
		assertEquals(0L, count(SimpleExtendedModel.class));
	}

	@Test
	public void oneSaveTest() throws Exception {
		SimpleExtendedModel simple = new SimpleExtendedModel();
		save(simple);
		RelationshipMixedBModel nested = new RelationshipMixedBModel(simple);

		save(nested);
		save(new NestedMixedABModel(nested));

		assertEquals(1L, count(SimpleExtendedModel.class));
		assertEquals(1L, count(RelationshipMixedBModel.class));
		assertEquals(1L, count(NestedMixedABModel.class));
	}

	@Test
	public void twoSameSaveTest() throws Exception {
		SimpleExtendedModel simple = new SimpleExtendedModel();
		save(simple);
		RelationshipMixedBModel nested = new RelationshipMixedBModel(simple);

		save(nested);
		save(new NestedMixedABModel(nested));
		save(new NestedMixedABModel(nested));

		assertEquals(1L, count(SimpleExtendedModel.class));
		assertEquals(1L, count(RelationshipMixedBModel.class));
		assertEquals(2L, count(NestedMixedABModel.class));
	}

	@Test
	public void twoDifferentSaveTest() throws Exception {
		SimpleExtendedModel simple = new SimpleExtendedModel();
		save(simple);
		SimpleExtendedModel anotherSimple = new SimpleExtendedModel();
		save(anotherSimple);
		RelationshipMixedBModel nested = new RelationshipMixedBModel(simple);
		save(nested);
		RelationshipMixedBModel anotherNested = new RelationshipMixedBModel(anotherSimple);

		save(anotherNested);
		save(new NestedMixedABModel(nested));
		save(new NestedMixedABModel(anotherNested));

		assertEquals(2L, count(SimpleExtendedModel.class));
		assertEquals(2L, count(RelationshipMixedBModel.class));
		assertEquals(2L, count(NestedMixedABModel.class));
	}

	@Test
	public void manySameSaveTest() throws Exception {
		SimpleExtendedModel simple = new SimpleExtendedModel();
		save(simple);
		RelationshipMixedBModel nested = new RelationshipMixedBModel(simple);
		save(nested);

		for (int i = 1; i <= 100; i++) {
			save(new NestedMixedABModel(nested));
		}

		assertEquals(1L, count(SimpleExtendedModel.class));
		assertEquals(1L, count(RelationshipMixedBModel.class));
		assertEquals(100L, count(NestedMixedABModel.class));
	}

	@Test
	public void manyDifferentSaveTest() throws Exception {
		for (int i = 1; i <= 100; i++) {
			SimpleExtendedModel simple = new SimpleExtendedModel();
			save(simple);
			RelationshipMixedBModel nested = new RelationshipMixedBModel(simple);
			save(nested);
			save(new NestedMixedABModel(nested));
		}

		assertEquals(100L, count(SimpleExtendedModel.class));
		assertEquals(100L, count(RelationshipMixedBModel.class));
		assertEquals(100L, count(NestedMixedABModel.class));
	}

	@Test
	public void listAllSameTest() throws Exception {
		SimpleExtendedModel simple = new SimpleExtendedModel();
		save(simple);
		RelationshipMixedBModel nested = new RelationshipMixedBModel(simple);
		save(nested);

		for (int i = 1; i <= 100; i++) {
			save(new NestedMixedABModel(nested));
		}

		List<NestedMixedABModel> models = listAll(NestedMixedABModel.class);
		assertEquals(100, models.size());

		for (NestedMixedABModel model : models) {
			assertEquals(nested.getId(), model.getNested().getId());
			assertEquals(simple.getId(), model.getNested().getSimple().getId());
		}
	}

	@Test
	public void listAllDifferentTest() throws Exception {
		for (int i = 1; i <= 100; i++) {
			SimpleExtendedModel simple = new SimpleExtendedModel();
			save(simple);
			RelationshipMixedBModel nested = new RelationshipMixedBModel(simple);
			save(nested);
			save(new NestedMixedABModel(nested));
		}

		List<NestedMixedABModel> models = listAll(NestedMixedABModel.class);
		assertEquals(100, models.size());

		for (NestedMixedABModel model : models) {
			assertEquals(model.getId(), model.getNested().getId());
			assertEquals(model.getId(), model.getNested().getSimple().getId());
		}
	}
}
