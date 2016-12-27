package com.orm.query;

import com.orm.util.QueryBuilder;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public final class QueryBuilderTests {

    @Test(expected=RuntimeException.class)
    public void noArgumentsTest() {
        QueryBuilder.generatePlaceholders(0);
    }

    @Test()
    public void oneArgumentsTest() {
        assertEquals("?", QueryBuilder.generatePlaceholders(1));
    }

    @Test
    public void twoArgumentsTest() {
        assertEquals("?,?", QueryBuilder.generatePlaceholders(2));
    }

    @Test
    public void manyArgumentsTest() {
        assertEquals("?,?,?,?,?,?,?,?,?,?", QueryBuilder.generatePlaceholders(10));
    }
}
