package com.orm;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class SugarContentProviderTest extends BaseSugarTest {

	public static final String[] TEST_TWO_ARG = {"bob", "doug"};
	public static final String[] TEST_ONE_ARG = {"bob"};
	public static final String[] TEST_ZERO_ARG = {};
	private static final String ACTUAL_ID_ONLY = "(_id = ?)";
	private static final String ACTUAL_ID_NAME = "(_id = ?) AND (name = ?)";
	private static final String VALUE_ID_VALUE = "A3";
	private static final String VALUE_NAME_PARAM = "name = ?";
	private static final String VALUE_BOB_VALUE = "bob";
	private static final String VALUE_DOUG_VALUE = "doug";
	private static final String VALUE_EMPTY_STRING = "";
	private static final String VALUE_ONE_SPACE = " ";


	private SugarContentProvider provider;

	@Before
	public void onSetupTest() {
		provider = new SugarContentProvider();
	}

	@Test
	public void testSelectionAddsIdWhenNull() throws Exception {
		String actual = provider.selectionWithColumn(null);

		assertEquals(ACTUAL_ID_ONLY, actual);
	}

	@Test
	public void testSelectionAddsIdWhenEmpty() throws Exception {
		String actual = provider.selectionWithColumn(VALUE_EMPTY_STRING);

		assertEquals(ACTUAL_ID_ONLY, actual);
	}

	@Test
	public void testSelectionAddsIdWhenEmptySpaces() throws Exception {
		String actual = provider.selectionWithColumn(VALUE_ONE_SPACE);

		assertEquals(ACTUAL_ID_ONLY, actual);
	}

	@Test
	public void testSelectionAddsIdWhenNotEmpty() throws Exception {
		String actual = provider.selectionWithColumn(VALUE_NAME_PARAM);

		assertEquals(ACTUAL_ID_NAME, actual);
	}

	@Test
	public void testSelectionArgsAddsIdWhenNull() throws Exception {
		String[] actual = provider.selectionArgsWithId(VALUE_ID_VALUE, null);

		assertEquals(1, actual.length);
		assertEquals(VALUE_ID_VALUE, actual[0]);
	}

	@Test
	public void testSelectionArgsAddsIdWhenEmpty() throws Exception {
		String[] actual = provider.selectionArgsWithId(VALUE_ID_VALUE, TEST_ZERO_ARG);

		assertEquals(1, actual.length);
		assertEquals(VALUE_ID_VALUE, actual[0]);
	}

	@Test
	public void testSelectionArgsAddsIdWhenOneArg() throws Exception {
		String[] actual = provider.selectionArgsWithId(VALUE_ID_VALUE, TEST_ONE_ARG);

		assertEquals(2, actual.length);
		assertEquals(VALUE_ID_VALUE, actual[0]);
		assertEquals(VALUE_BOB_VALUE, actual[1]);
	}

	@Test
	public void testSelectionArgsAddsIdWhenTwoArgs() throws Exception {
		String[] actual = provider.selectionArgsWithId(VALUE_ID_VALUE, TEST_TWO_ARG);

		assertEquals(3, actual.length);
		assertEquals(VALUE_ID_VALUE, actual[0]);
		assertEquals(VALUE_BOB_VALUE, actual[1]);
		assertEquals(VALUE_DOUG_VALUE, actual[2]);
	}

	@Test
	public void testIsEmptyNull() throws Exception {
		assertTrue(provider.isEmpty(null));
	}

	@Test
	public void testIsEmptyZeroLength() throws Exception {
		assertTrue(provider.isEmpty(VALUE_EMPTY_STRING));
	}

	@Test
	public void testIsEmptySpace() throws Exception {
		assertTrue(provider.isEmpty(VALUE_ONE_SPACE));
	}

	@Test
	public void testIsNotEmpty() throws Exception {
		assertFalse(provider.isEmpty(VALUE_ID_VALUE));
	}
}
