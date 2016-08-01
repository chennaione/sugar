package com.orm;

import org.junit.Test;

public class NamingHelperTest extends BaseSugarTest {


	@Test
	public void testToSQLNameCaseConversion() throws Exception {
		assertToSqlNameEquals("TESTLOWERCASE", "testlowercase");
		assertToSqlNameEquals("TESTUPPERCASE", "TESTUPPERCASE");
	}

	@Test
	public void testToSQLNameUnderscore() {
		assertToSqlNameEquals("TEST_UNDERSCORE", "testUnderscore");
		assertToSqlNameEquals("AB_CD", "AbCd");
		assertToSqlNameEquals("AB_CD", "ABCd");
		assertToSqlNameEquals("AB_CD", "AbCD");
		assertToSqlNameEquals("SOME_DETAILS_OBJECT", "SomeDetailsObject");
		assertToSqlNameEquals("H_OL_A", "hOlA");
		assertToSqlNameEquals("A", "a");
	}


}
