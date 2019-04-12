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
public final class NestedMixedAATests {

	@Test
	public void emptyDatabaseTest() throws Exception {
		assertEquals(0L, count(NestedMixedAAModel.class));
		assertEquals(0L, count(RelationshipMixedAModel.class));
		assertEquals(0L, count(SimpleAnnotatedModel.class));
	}

	@Test
	public void oneSaveTest() throws Exception {
		SimpleAnnotatedModel simple = new SimpleAnnotatedModel();
		save(simple);
		RelationshipMixedAModel nested = new RelationshipMixedAModel(simple);
		save(nested);
		save(new NestedMixedAAModel(nested));
		assertEquals(1L, count(SimpleAnnotatedModel.class));
		assertEquals(1L, count(RelationshipMixedAModel.class));
		assertEquals(1L, count(NestedMixedAAModel.class));
	}

	@Test
	public void twoSameSaveTest() throws Exception {
		SimpleAnnotatedModel simple = new SimpleAnnotatedModel();
		save(simple);
		RelationshipMixedAModel nested = new RelationshipMixedAModel(simple);
		save(nested);
		save(new NestedMixedAAModel(nested));
		save(new NestedMixedAAModel(nested));
		assertEquals(1L, count(SimpleAnnotatedModel.class));
		assertEquals(1L, count(RelationshipMixedAModel.class));
		assertEquals(2L, count(NestedMixedAAModel.class));
	}

	@Test
	public void twoDifferentSaveTest() throws Exception {
		SimpleAnnotatedModel simple = new SimpleAnnotatedModel();
		save(simple);
		SimpleAnnotatedModel anotherSimple = new SimpleAnnotatedModel();
		save(anotherSimple);
		RelationshipMixedAModel nested = new RelationshipMixedAModel(simple);
		save(nested);
		RelationshipMixedAModel anotherNested = new RelationshipMixedAModel(anotherSimple);
		save(anotherNested);
		save(new NestedMixedAAModel(nested));
		save(new NestedMixedAAModel(anotherNested));
		assertEquals(2L, count(SimpleAnnotatedModel.class));
		assertEquals(2L, count(RelationshipMixedAModel.class));
		assertEquals(2L, count(NestedMixedAAModel.class));
	}

	@Test
	public void manySameSaveTest() throws Exception {
		SimpleAnnotatedModel simple = new SimpleAnnotatedModel();
		save(simple);
		RelationshipMixedAModel nested = new RelationshipMixedAModel(simple);
		save(nested);
		for (int i = 1; i <= 100; i++) {
			save(new NestedMixedAAModel(nested));
		}
		assertEquals(1L, count(SimpleAnnotatedModel.class));
		assertEquals(1L, count(RelationshipMixedAModel.class));
		assertEquals(100L, count(NestedMixedAAModel.class));
	}

	@Test
	public void manyDifferentSaveTest() throws Exception {
		for (int i = 1; i <= 100; i++) {
			SimpleAnnotatedModel simple = new SimpleAnnotatedModel();
			save(simple);
			RelationshipMixedAModel nested = new RelationshipMixedAModel(simple);
			save(nested);
			save(new NestedMixedAAModel(nested));
		}
		assertEquals(100L, count(SimpleAnnotatedModel.class));
		assertEquals(100L, count(RelationshipMixedAModel.class));
		assertEquals(100L, count(NestedMixedAAModel.class));
	}

	@Test
	public void listAllSameTest() throws Exception {
		SimpleAnnotatedModel simple = new SimpleAnnotatedModel();
		save(simple);
		RelationshipMixedAModel nested = new RelationshipMixedAModel(simple);
		save(nested);

		for (int i = 1; i <= 100; i++) {
			save(new NestedMixedAAModel(nested));
		}

		List<NestedMixedAAModel> models = listAll(NestedMixedAAModel.class);
		assertEquals(100, models.size());

		for (NestedMixedAAModel model : models) {
			assertEquals(nested.getId(), model.getNested().getId());
			assertEquals(simple.getId(), model.getNested().getSimple().getId());
		}
	}

	@Test
	public void listAllDifferentTest() throws Exception {
		for (int i = 1; i <= 100; i++) {
			SimpleAnnotatedModel simple = new SimpleAnnotatedModel();
			save(simple);
			RelationshipMixedAModel nested = new RelationshipMixedAModel(simple);
			save(nested);
			save(new NestedMixedAAModel(nested));
		}

		List<NestedMixedAAModel> models = listAll(NestedMixedAAModel.class);
		assertEquals(100, models.size());

		for (NestedMixedAAModel model : models) {
			assertEquals(model.getId(), model.getNested().getId());
			assertEquals(model.getId(), model.getNested().getSimple().getId());
		}
	}
}
