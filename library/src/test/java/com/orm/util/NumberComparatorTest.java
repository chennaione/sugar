package com.orm.util;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class NumberComparatorTest {
    private NumberComparator numberComparator = new NumberComparator();

    @Test
    public void testNumberComparatorEquals() throws Exception {
        String a = "a";
        String b = "a";
        int compare = numberComparator.compare(a, b);
        assertEquals(0, compare);
    }

    @Test
    public void testNumberComparatorGreater() throws Exception {
        String a = "test";
        String b = "foo";
        int compare = numberComparator.compare(a, b);
        assertEquals(1, compare);
    }

    @Test
    public void testNumberComparatorLesser() throws Exception {
        String a = "foo";
        String b = "test";
        int compare = numberComparator.compare(a, b);
        assertEquals(-1, compare);
    }


}