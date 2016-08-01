package com.orm;

import com.orm.query.DummyContext;
import com.orm.util.NamingHelper;

import static junit.framework.Assert.assertEquals;

public class BaseSugarTest {
	private static SugarConfiguration config;

	static {
		config = SugarConfiguration
				.manifest(new DummyContext());
		SugarContext.init(config);
	}

	public static SugarConfiguration getConfig() {
		return config;
	}

	/**
	 * Helper method that asserts a CamelCaseString is converted to UPPER_CASE_UNDER_SCORE.
	 *
	 * @param expected
	 * 		a CamelCaseString
	 * @param actual
	 * 		the expected UPPER_CASE_UNDER_SCORE string
	 */
	protected static void assertToSqlNameEquals(String expected, String actual) {
		assertEquals(expected, NamingHelper.toSQLNameDefault(config, actual));
	}

}
