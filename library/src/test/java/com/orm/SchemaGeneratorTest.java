package com.orm;

import com.orm.models.EmptyModel;
import com.orm.models.IntUniqueModel;
import com.orm.models.MultiColumnUniqueModel;
import com.orm.models.StringFieldAnnotatedModel;
import com.orm.models.StringFieldExtendedModel;
import com.orm.models.StringFieldExtendedModelAnnotatedColumn;
import com.orm.query.DummyContext;
import com.orm.util.ContextUtil;
import com.orm.helper.NamingHelper;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class SchemaGeneratorTest {

    @Test
    public void testEmptyTableCreation() throws Exception {
        ContextUtil.init(new DummyContext());
        SchemaGenerator schemaGenerator = SchemaGenerator.getInstance();
        String createSQL = schemaGenerator.createTableSQL(EmptyModel.class);
        assertEquals(
                "CREATE TABLE IF NOT EXISTS " + NamingHelper.toSQLName(EmptyModel.class) +
                    " ( ID INTEGER PRIMARY KEY AUTOINCREMENT  ) ",
                createSQL);
    }

    @Test
    public void testSimpleColumnTableCreation() throws Exception {
        ContextUtil.init(new DummyContext());
        SchemaGenerator schemaGenerator = SchemaGenerator.getInstance();
        String createSQL = schemaGenerator.createTableSQL(StringFieldExtendedModel.class);
        assertEquals(
                "CREATE TABLE IF NOT EXISTS " + NamingHelper.toSQLName(StringFieldExtendedModel.class) +
                        " ( ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                        "STRING TEXT ) ",
                createSQL);

        String createSQL2 = schemaGenerator.createTableSQL(StringFieldAnnotatedModel.class);

        assertEquals(
                "CREATE TABLE IF NOT EXISTS " + NamingHelper.toSQLName(StringFieldAnnotatedModel.class) +
                        " ( ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                        "STRING TEXT ) ",
                createSQL2);

        String createSQL3 = schemaGenerator.createTableSQL(StringFieldExtendedModelAnnotatedColumn.class);

        assertEquals(
                "CREATE TABLE IF NOT EXISTS " + NamingHelper.toSQLName(StringFieldExtendedModelAnnotatedColumn.class) +
                        " ( ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                        "anyName TEXT ) ",
                createSQL3);
    }

    @Test
    public void testUniqueTableCreation() {
        ContextUtil.init(new DummyContext());
        SchemaGenerator schemaGenerator = SchemaGenerator.getInstance();
        String createSQL = schemaGenerator.createTableSQL(IntUniqueModel.class);
        assertEquals(
                "CREATE TABLE IF NOT EXISTS " + NamingHelper.toSQLName(IntUniqueModel.class) +
                        " ( ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                        "VALUE INTEGER UNIQUE ) ",
                createSQL);
    }

    @Test
    public void testMultiColumnUniqueTableCreation() {
        ContextUtil.init(new DummyContext());
        SchemaGenerator schemaGenerator = SchemaGenerator.getInstance();
        String createSQL = schemaGenerator.createTableSQL(MultiColumnUniqueModel.class);
        assertEquals(
                "CREATE TABLE IF NOT EXISTS " + NamingHelper.toSQLName(MultiColumnUniqueModel.class) +
                        " ( ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                        "A INTEGER, B INTEGER, " +
                        "UNIQUE(A, B) ON CONFLICT REPLACE ) ",
                createSQL);
    }
}
