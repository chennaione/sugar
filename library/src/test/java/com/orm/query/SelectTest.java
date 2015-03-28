package com.orm.query;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

public class SelectTest {

    @Test
    public void testMergeCondition(){
        Select where = Select.from(TestRecord.class).where(Condition.prop("test").eq("satya"));
        assertEquals("test = ? ", where.getWhereCond());
        assertEquals(1, where.getArgs().length);
        assertEquals("satya", where.getArgs()[0]);

        where = Select.from(TestRecord.class).where(Condition.prop("test").eq("satya"), Condition.prop("prop").eq(2));
        assertEquals("test = ?  AND prop = ? ", where.getWhereCond());
        assertEquals(2, where.getArgs().length);
        assertEquals("satya", where.getArgs()[0]);
        assertEquals("2", where.getArgs()[1]);
    }


    @Test
    public void testWhere(){
        Select where = Select.from(TestRecord.class).where(Condition.prop("test").eq("satya"));
        assertEquals("test = ? ", where.getWhereCond());
        assertEquals(1, where.getArgs().length);
        assertEquals("satya", where.getArgs()[0]);

        where = Select.from(TestRecord.class).where(Condition.prop("test").eq("satya"), Condition.prop("prop").eq(2));
        assertEquals("test = ?  AND prop = ? ", where.getWhereCond());
        assertEquals(2, where.getArgs().length);
        assertEquals("satya", where.getArgs()[0]);
        assertEquals("2", where.getArgs()[1]);
    }


    @Test
    public void testWhereOr(){
        Select where = Select.from(TestRecord.class).whereOr(Condition.prop("test").eq("satya"));
        assertEquals("test = ? ", where.getWhereCond());
        assertEquals(1, where.getArgs().length);
        assertEquals("satya", where.getArgs()[0]);

        where = Select.from(TestRecord.class).whereOr(Condition.prop("test").eq("satya"), Condition.prop("prop").eq(2));
        assertEquals("test = ?  OR prop = ? ", where.getWhereCond());
        assertEquals(2, where.getArgs().length);
        assertEquals("satya", where.getArgs()[0]);
        assertEquals("2", where.getArgs()[1]);
    }

    @Test
    public void testAnd(){
        Select where = Select.from(TestRecord.class).whereOr(Condition.prop("test").eq("satya"));
        assertEquals("test = ? ", where.getWhereCond());
        assertEquals(1, where.getArgs().length);
        assertEquals("satya", where.getArgs()[0]);

        where.and(Condition.prop("prop").eq(2));

        assertEquals("test = ?  AND prop = ? ", where.getWhereCond());
        assertEquals(2, where.getArgs().length);
        assertEquals("satya", where.getArgs()[0]);
        assertEquals("2", where.getArgs()[1]);
    }

    @Test
    public void testOr(){
        Select where = Select.from(TestRecord.class).whereOr(Condition.prop("test").eq("satya"));
        assertEquals("test = ? ", where.getWhereCond());
        assertEquals(1, where.getArgs().length);
        assertEquals("satya", where.getArgs()[0]);

        where.or(Condition.prop("prop").eq(2));

        assertEquals("test = ?  OR prop = ? ", where.getWhereCond());
        assertEquals(2, where.getArgs().length);
        assertEquals("satya", where.getArgs()[0]);
        assertEquals("2", where.getArgs()[1]);
    }


}
