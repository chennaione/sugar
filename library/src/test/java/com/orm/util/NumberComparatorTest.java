package com.orm.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author jonatan.salas
 */
public final class NumberComparatorTest {
    private NumberComparator comparator;

    @Before
    public void setUp() {
        comparator = new NumberComparator();
    }

    @Test
    public void testNumberComparatorWithoutNumbers() {
        int result = comparator.compare("hola", "hola");
        assertEquals(0, result);
    }

    @Test
    public void testNumberComparatorWithNumbers() {
        int result = comparator.compare("1", "2");
        assertEquals(-1, result);
    }

    @Test
    public void testComparatorWithNumbers() {
        int result = comparator.compare("4", "2");
        assertEquals(1, result);
    }

    @Test
    public void testCompareRight() {
        int result = comparator.compareRight("hola", "hola");
        assertEquals(0, result);
    }

    @Test
    public void testCharAt() {
        Character c = NumberComparator.charAt("Hola", 0);
        assertEquals("H", c.toString());
    }
}
