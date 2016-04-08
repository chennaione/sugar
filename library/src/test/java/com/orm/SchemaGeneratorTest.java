package com.orm;

import com.orm.models.EmptyModel;
import com.orm.models.IntUniqueModel;
import com.orm.models.MultiColumnUniqueModel;
import com.orm.models.StringFieldAnnotatedModel;
import com.orm.models.StringFieldExtendedModel;
import com.orm.models.StringFieldExtendedModelAnnotatedColumn;
import com.orm.helper.NamingHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, application = ClientApp.class, manifest = Config.NONE)
public class SchemaGeneratorTest {

    @Before
    public void setUp() {
        SugarContext.init(RuntimeEnvironment.application);
    }

    @Test
    public void testEmptyTableCreation() throws Exception {
        SchemaGenerator schemaGenerator = SchemaGenerator.getInstance();
        String createSQL = schemaGenerator.createTableSQL(EmptyModel.class);
        assertEquals(
                "CREATE TABLE IF NOT EXISTS " + NamingHelper.toSQLName(EmptyModel.class) +
                    " ( ID INTEGER PRIMARY KEY AUTOINCREMENT  ) ",
                createSQL);
    }

    @Test
    public void testSimpleColumnTableCreation() throws Exception {
        SchemaGenerator schemaGenerator = SchemaGenerator.getInstance();
        String createSQL = schemaGenerator.createTableSQL(StringFieldExtendedModel.class);
        assertEquals(
                "CREATE TABLE IF NOT EXISTS " + NamingHelper.toSQLName(StringFieldExtendedModel.class) +
                        " ( ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                        "STRING TEXT ) ",
                createSQL);

        String createSQL2 = schemaGenerator.createTableSQL(StringFieldAnnotatedModel.class);

        assertEquals("CREATE TABLE IF NOT EXISTS " + NamingHelper.toSQLName(StringFieldAnnotatedModel.class) +
                        " ( ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                        "STRING TEXT ) ",
                createSQL2);

        String createSQL3 = schemaGenerator.createTableSQL(StringFieldExtendedModelAnnotatedColumn.class);

        assertEquals("CREATE TABLE IF NOT EXISTS " + NamingHelper.toSQLName(StringFieldExtendedModelAnnotatedColumn.class) +
                        " ( ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                        "anyName TEXT ) ",
                createSQL3);
    }

    @Test
    public void testUniqueTableCreation() {
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
