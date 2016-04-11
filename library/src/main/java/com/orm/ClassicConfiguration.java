package com.orm;

import android.content.Context;
import android.provider.BaseColumns;

import com.orm.helper.ClassicSchemaGenerator;
import com.orm.helper.SugarDatabaseHelper;
import com.orm.util.ManifestHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bpappin on 16-03-29.
 */
public class ClassicConfiguration extends SugarConfiguration {

	private String databaseName;
	private String authority;
	private int databaseVersion;
	private Class<?>[] modelClasses = {};
	private SugarDatabaseHelper databaseHelper;
	private boolean debug = false;
	private String domain;
	private String idColumnName = BaseColumns._ID;


	public ClassicConfiguration(Context context) {
		super(context);
		fromManifest(context, this);
	}

	public static ClassicConfiguration manifest(Context context) {
		return ClassicConfiguration.get(context);
	}

	private static ClassicConfiguration fromManifest(Context context, ClassicConfiguration classicConfiguration) {
		return classicConfiguration.name(ManifestHelper.getDatabaseName(context))
								   .version(ManifestHelper.getDatabaseVersion(context))
								   .debug(ManifestHelper.getDebugEnabled(context))
								   .domain(ManifestHelper.getDomainPackageName(context))
								   .authority(ManifestHelper.getAuthority(context))
								   .helper()
								   .helper(ManifestHelper.getHelper(context))
								   .idColumnName(ManifestHelper.getIdColumnName(context))
								   .model(ManifestHelper.getModels(context));
	}

	public static final ClassicConfiguration get(Context context) {
		return new ClassicConfiguration(context);
	}

	public ClassicConfiguration name(String name) {
		this.databaseName = name;
		return this;
	}

	public ClassicConfiguration version(int version) {
		this.databaseVersion = version;
		return this;
	}

	public ClassicConfiguration model(Class<?>... models) {
		//for (int i = 0; i < models.length; i++) {
		//	modelClasses.add(models[i]);
		//}
		modelClasses = models;
		return this;
	}

	public ClassicConfiguration domain(String domain) {
		this.domain = domain;
		return this;
	}

	public ClassicConfiguration debug(boolean debug) {
		this.debug = debug;
		return this;
	}

	public ClassicConfiguration authority(String authority) {
		this.authority = authority;
		return this;
	}

	public ClassicConfiguration helper(SugarDatabaseHelper helper) {
		helper.setConfiguration(this);
		this.databaseHelper = helper;
		return this;
	}

	public ClassicConfiguration idColumnName(String name) {
		this.idColumnName = name;
		return this;
	}

	/**
	 * Uses the classic helper.
	 *
	 * @return
	 */
	public ClassicConfiguration helper() {
		this.databaseHelper = new ClassicSchemaGenerator(this);
		return this;
	}

	@Override
	public SugarDatabaseHelper getDatabaseHelper() {
		return databaseHelper;
	}
	@Override
	public String getIdColumnName() {
		return idColumnName;
	}
	@Override
	public String getDatabaseName() {
		return databaseName;
	}
	@Override
	public String getAuthority() {
		return authority;
	}
	@Override
	public int getDatabaseVersion() {
		return databaseVersion;
	}
	@Override
	public Class<?>[] getModelClasses() {
		return modelClasses;
	}
	@Override
	public boolean isDebug() {
		return debug;
	}
	@Override
	public String getDomain() {
		return domain;
	}
}
