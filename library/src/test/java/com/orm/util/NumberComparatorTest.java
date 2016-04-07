package com.orm.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Nursultan Turdaliev on 12/6/15.
 */
public class NumberComparatorTest {

    private String ten;
    private String eleven;
    private NumberComparator numberComparator;

    @Before
    public void setUp() throws Exception {

        ten = "10.sql";
        eleven = "11.sql";
        numberComparator =  new NumberComparator();

    }

    @Test
    public void testCompare() throws Exception {

        assertEquals("Testing a small number with a big number",-1,numberComparator.compare(ten,eleven));
        assertEquals("Testing equal numbers",0,numberComparator.compare(ten, ten));
        assertEquals("Testing a big number with a small number  ",1,numberComparator.compare(eleven,ten));
    }
}