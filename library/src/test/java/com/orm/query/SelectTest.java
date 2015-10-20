package com.orm.query;

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
    public void toSqlAllClauses(){
        String toSql = Select.from(TestRecord.class)
                .where("test")
                .groupBy("test")
                .orderBy("test")
                .limit("test")
                .offset("test")
                .toSql();
        assertEquals("SELECT * FROM TEST_RECORD WHERE test ORDER BY test GROUP BY test LIMIT test OFFSET test ", toSql);
    }

    @Test
    public void toSqlNoClauses(){
        String toSql = Select.from(TestRecord.class)
                .toSql();
        assertEquals("SELECT * FROM TEST_RECORD ", toSql);
    }

    @Test
    public void toSqlWhereLimitClauses(){
        String toSql = Select.from(TestRecord.class)
                .where("test")
                .limit("test")
                .toSql();
        assertEquals("SELECT * FROM TEST_RECORD WHERE test LIMIT test ", toSql);
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