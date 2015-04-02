package com.orm.query;

import com.orm.query.Condition;

import junit.framework.TestResult;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class SelectTest {

    @Test
    public void testMergeCondition(){
        Select where = Select.from(TestRecord.class).where(Condition.prop("test").eq("satya"));
        assertEquals("(test = ? )", where.getWhereCond());
        assertEquals(1, where.getArgs().length);
        assertEquals("satya", where.getArgs()[0]);

        where = Select.from(TestRecord.class).where(Condition.prop("test").eq("satya"), Condition.prop("prop").eq(2));
        assertEquals("(test = ?  AND prop = ? )", where.getWhereCond());
        assertEquals(2, where.getArgs().length);
        assertEquals("satya", where.getArgs()[0]);
        assertEquals("2", where.getArgs()[1]);
    }


    @Test
    public void testWhere(){
        Select where = Select.from(TestRecord.class).where(Condition.prop("test").eq("satya"));
        assertEquals("(test = ? )", where.getWhereCond());
        assertEquals(1, where.getArgs().length);
        assertEquals("satya", where.getArgs()[0]);

        where = Select.from(TestRecord.class).where(Condition.prop("test").eq("satya"), Condition.prop("prop").eq(2));
        assertEquals("(test = ?  AND prop = ? )", where.getWhereCond());
        assertEquals(2, where.getArgs().length);
        assertEquals("satya", where.getArgs()[0]);
        assertEquals("2", where.getArgs()[1]);
    }


    @Test
    public void testWhereOr(){
        Select where = Select.from(TestRecord.class).whereOr(Condition.prop("test").eq("satya"));
        assertEquals("(test = ? )", where.getWhereCond());
        assertEquals(1, where.getArgs().length);
        assertEquals("satya", where.getArgs()[0]);

        where = Select.from(TestRecord.class).whereOr(Condition.prop("test").eq("satya"), Condition.prop("prop").eq(2));
        assertEquals("(test = ?  OR prop = ? )", where.getWhereCond());
        assertEquals(2, where.getArgs().length);
        assertEquals("satya", where.getArgs()[0]);
        assertEquals("2", where.getArgs()[1]);
    }

    @Test
    public void testAnd(){
        Select where = Select.from(TestRecord.class).whereOr(Condition.prop("test").eq("satya"));
        assertEquals("(test = ? )", where.getWhereCond());
        assertEquals(1, where.getArgs().length);
        assertEquals("satya", where.getArgs()[0]);

        where.and(Condition.prop("prop").eq(2));

        assertEquals("(test = ? ) AND (prop = ? )", where.getWhereCond());
        assertEquals(2, where.getArgs().length);
        assertEquals("satya", where.getArgs()[0]);
        assertEquals("2", where.getArgs()[1]);
    }

    @Test
    public void testOr(){
        Select where = Select.from(TestRecord.class).whereOr(Condition.prop("test").eq("satya"));
        assertEquals("(test = ? )", where.getWhereCond());
        assertEquals(1, where.getArgs().length);
        assertEquals("satya", where.getArgs()[0]);

        where.or(Condition.prop("prop").eq(2));

        assertEquals("(test = ? ) OR (prop = ? )", where.getWhereCond());
        assertEquals(2, where.getArgs().length);
        assertEquals("satya", where.getArgs()[0]);
        assertEquals("2", where.getArgs()[1]);
    }

    @Test
    public void testIsNull() {
        Select where = Select.from(TestRecord.class).where(Condition.prop("test").isNull());
        assertEquals("(test IS NULL )", where.getWhereCond());
        assertEquals(0, where.getArgs().length);

        where = Select.from(TestRecord.class).where(Condition.prop("test").eq(null));
        assertEquals("(test IS NULL )", where.getWhereCond());
        assertEquals(0, where.getArgs().length);
    }

    @Test
    public void testIsNotNull() {
        Select where = Select.from(TestRecord.class).where(Condition.prop("test").isNotNull());
        assertEquals("(test IS NOT NULL )", where.getWhereCond());
        assertEquals(0, where.getArgs().length);

        where = Select.from(TestRecord.class).where(Condition.prop("test").notEq(null));
        assertEquals("(test IS NOT NULL )", where.getWhereCond());
        assertEquals(0, where.getArgs().length);
    }


}
