package com.orm;

import com.orm.app.ClientApp;
import com.orm.dsl.BuildConfig;
import com.orm.model.EmptyModel;
import com.orm.model.IntUniqueModel;
import com.orm.model.MultiColumnUniqueModel;
import com.orm.model.StringFieldAnnotatedModel;
import com.orm.model.StringFieldExtendedModel;
import com.orm.model.StringFieldExtendedModelAnnotatedColumn;
import com.orm.helper.NamingHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, constants = BuildConfig.class, application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class SchemaGeneratorTest {

    @Test
    public void testEmptyTableCreation() throws Exception {
        SchemaGenerator schemaGenerator = SchemaGenerator.getInstance();
        String createSQL = schemaGenerator.createTableSQL(EmptyModel.class);
        assertEquals(
                "CREATE TABLE IF NOT EXISTS " + NamingHelper.toTableName(EmptyModel.class) +
                    " ( ID INTEGER PRIMARY KEY AUTOINCREMENT  ) ",
                createSQL);
    }

    @Test
    public void testSimpleColumnTableCreation() throws Exception {
        SchemaGenerator schemaGenerator = SchemaGenerator.getInstance();
        String createSQL = schemaGenerator.createTableSQL(StringFieldExtendedModel.class);
        assertEquals(
                "CREATE TABLE IF NOT EXISTS " + NamingHelper.toTableName(StringFieldExtendedModel.class) +
                        " ( ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                        "STRING TEXT ) ",
                createSQL);

        String createSQL2 = schemaGenerator.createTableSQL(StringFieldAnnotatedModel.class);

        assertEquals("CREATE TABLE IF NOT EXISTS " + NamingHelper.toTableName(StringFieldAnnotatedModel.class) +
                        " ( ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                        "STRING TEXT ) ",
                createSQL2);

        String createSQL3 = schemaGenerator.createTableSQL(StringFieldExtendedModelAnnotatedColumn.class);

        assertEquals("CREATE TABLE IF NOT EXISTS " + NamingHelper.toTableName(StringFieldExtendedModelAnnotatedColumn.class) +
                        " ( ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                        "anyName TEXT ) ",
                createSQL3);
    }

    @Test
    public void testUniqueTableCreation() {
        SchemaGenerator schemaGenerator = SchemaGenerator.getInstance();
        String createSQL = schemaGenerator.createTableSQL(IntUniqueModel.class);
        assertEquals(
                "CREATE TABLE IF NOT EXISTS " + NamingHelper.toTableName(IntUniqueModel.class) +
                        " ( ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                        "VALUE INTEGER UNIQUE ) ",
                createSQL);
    }

    @Test
    public void testMultiColumnUniqueTableCreation() {
        SchemaGenerator schemaGenerator = SchemaGenerator.getInstance();
        String createSQL = schemaGenerator.createTableSQL(MultiColumnUniqueModel.class);
        assertEquals(
                "CREATE TABLE IF NOT EXISTS " + NamingHelper.toTableName(MultiColumnUniqueModel.class) +
                        " ( ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                        "A INTEGER, B INTEGER, " +
                        "UNIQUE(A, B) ON CONFLICT REPLACE ) ",
                createSQL);
    }
}
