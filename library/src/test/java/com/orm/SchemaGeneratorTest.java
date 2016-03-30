package com.orm;

import android.provider.BaseColumns;

import com.orm.helper.ClassicSchemaGenerator;
import com.orm.models.EmptyModel;
import com.orm.models.IntUniqueModel;
import com.orm.models.MultiColumnUniqueModel;
import com.orm.models.StringFieldAnnotatedModel;
import com.orm.models.StringFieldExtendedModel;
import com.orm.models.StringFieldExtendedModelAnnotatedColumn;
import com.orm.query.DummyContext;
import com.orm.util.NamingHelper;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class SchemaGeneratorTest {
	@Test
	public void testEmptyTableCreation() throws Exception {
		ClassicSchemaGenerator schemaGenerator = new ClassicSchemaGenerator(Configuration
				.maifest(new DummyContext()));
		String createSQL = schemaGenerator.createTableSQL(EmptyModel.class);
		assertEquals(
				"CREATE TABLE IF NOT EXISTS " + NamingHelper.toSQLName(EmptyModel.class) +
				" ( " + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT  ) ",
				createSQL);
	}

	@Test
	public void testSimpleColumnTableCreation() throws Exception {
		ClassicSchemaGenerator schemaGenerator = new ClassicSchemaGenerator(Configuration
				.maifest(new DummyContext()));
		String createSQL = schemaGenerator.createTableSQL(StringFieldExtendedModel.class);
		assertEquals(
				"CREATE TABLE IF NOT EXISTS " +
				NamingHelper.toSQLName(StringFieldExtendedModel.class) +
				" ( " + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
				"NAME TEXT ) ",
				createSQL);

		String createSQL2 = schemaGenerator.createTableSQL(StringFieldAnnotatedModel.class);

		assertEquals(
				"CREATE TABLE IF NOT EXISTS " +
				NamingHelper.toSQLName(StringFieldAnnotatedModel.class) +
				" ( " + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
				"NAME TEXT ) ",
				createSQL2);

		String createSQL3 = schemaGenerator
				.createTableSQL(StringFieldExtendedModelAnnotatedColumn.class);

		assertEquals(
				"CREATE TABLE IF NOT EXISTS " +
				NamingHelper.toSQLName(StringFieldExtendedModelAnnotatedColumn.class) +
				" ( " + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
				"anyName TEXT ) ",
				createSQL3);
	}

	@Test
	public void testUniqueTableCreation() {
		ClassicSchemaGenerator schemaGenerator = new ClassicSchemaGenerator(Configuration
				.maifest(new DummyContext()));
		String createSQL = schemaGenerator.createTableSQL(IntUniqueModel.class);
		assertEquals(
				"CREATE TABLE IF NOT EXISTS " + NamingHelper.toSQLName(IntUniqueModel.class) +
				" ( " + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
				"VALUE INTEGER UNIQUE ) ",
				createSQL);
	}

	@Test
	public void testMultiColumnUniqueTableCreation() {
		ClassicSchemaGenerator schemaGenerator = new ClassicSchemaGenerator(Configuration
				.maifest(new DummyContext()));
		String createSQL = schemaGenerator.createTableSQL(MultiColumnUniqueModel.class);
		assertEquals(
				"CREATE TABLE IF NOT EXISTS " +
				NamingHelper.toSQLName(MultiColumnUniqueModel.class) +
				" ( " + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
				"A INTEGER, B INTEGER, " +
				"UNIQUE(A, B) ON CONFLICT REPLACE ) ",
				createSQL);
	}
}
