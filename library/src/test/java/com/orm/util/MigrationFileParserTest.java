package com.orm.util;

import org.junit.Test;

import java.lang.String;

import static junit.framework.Assert.assertEquals;

public final class MigrationFileParserTest {

    @Test
    public void testSingleLineStatement() {
        MigrationFileParser singleLineComment = new MigrationFileParser("insert into table--comment");

        String statements[] = singleLineComment.getStatements();
        assertEquals("Testing single line statement size",1,statements.length);
        assertEquals("Testing single line statement content","insert into table",statements[0]);

        singleLineComment = new MigrationFileParser("insert into table--comment\n");

        singleLineComment.getStatements();
        assertEquals("Testing single line statement size",1,statements.length);
        assertEquals("Testing single line statement content","insert into table",statements[0]);
    }

    @Test
    public void testMultiLineComment() {
        MigrationFileParser multiLineComment = new MigrationFileParser("insert into table /**comment \n new line 2 \n new line 3 */hello");

        String statements[] = multiLineComment.getStatements();
        assertEquals("Testing multiline statement size",1,statements.length);
        assertEquals("Testing multiline comment","insert into table hello",statements[0]);
    }

    @Test
    public void testMixedComment() {
        MigrationFileParser mixedComment = new MigrationFileParser("insert into/*multiline\n **comment*/--comment");

        String statements[] = mixedComment.getStatements();

        assertEquals("Testing mixed comment statement size",1,statements.length);
        assertEquals("Testing mixed comment statments", "insert into", statements[0]);
    }
}
