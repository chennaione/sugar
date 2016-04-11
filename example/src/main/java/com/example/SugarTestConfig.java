package com.example;

import android.content.Context;
import android.provider.BaseColumns;

import com.orm.SugarConfiguration;
import com.orm.helper.ClassicSchemaGenerator;
import com.orm.helper.SugarDatabaseHelper;

import java.util.List;

/**
 * Created by bpappin on 16-04-10.
 */
public class SugarTestConfig extends SugarConfiguration {
	public SugarTestConfig(Context context) {
		super(context);
	}

	@Override
	public SugarDatabaseHelper getDatabaseHelper() {
		return new ClassicSchemaGenerator(this);
	}

	@Override
	public String getIdColumnName() {
		return BaseColumns._ID;
	}

	@Override
	public String getDatabaseName() {
		return "test_database.db";
	}

	@Override
	public String getAuthority() {
		return "com.example.sugar";
	}

	@Override
	public int getDatabaseVersion() {
		return 1;
	}

	@Override
	public Class<?>[] getModelClasses() {
		return null;
	}

	@Override
	public boolean isDebug() {
		return true;
	}

	@Override
	public String getDomain() {
		return "com.example.models";
	}
}
