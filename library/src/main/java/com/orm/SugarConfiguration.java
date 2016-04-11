package com.orm;

import android.content.Context;
import android.provider.BaseColumns;

import com.orm.helper.DropTableSchemaGenerator;
import com.orm.helper.SugarDatabaseHelper;
import com.orm.util.ManifestHelper;

/**
 * Created by bpappin on 16-03-29.
 */
public abstract class SugarConfiguration {
	private static final String TAG = "SugarConfiguration";
	private Context context;

	//public static final SugarConfiguration get(Context context) {
	//	return manifest(context);
	//}

	public static SugarConfiguration manifest(Context context) {
		String className = ManifestHelper.getConfigurationClassName(context);
		try {
			Class<?> clazz = Class
					.forName(className, true, context.getClass().getClassLoader());
			final Object configObj = clazz.getDeclaredConstructor(Context.class)
										  .newInstance(context);
			if (!SugarConfiguration.class.isAssignableFrom(configObj.getClass())) {
				throw new RuntimeException(
						"Configuration class \"" + className + "\" is not an instance of " +
						SugarConfiguration.class.getName());
			}
			return (SugarConfiguration) configObj;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Configuration class was not found: " + className, e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(
					"Configuration class does not have a constructor that takes a Context object: " +
					className, e);
		} catch (Exception e) {
			throw new RuntimeException("Configuration class loading failed: " + className, e);
		}
	}

	public SugarConfiguration(Context context) {
		this.context = (context != null ? context.getApplicationContext() : context);
	}

	public Context getContext() {
		return context;
	}

	/**
	 * There are two included schema generation strategies {@link com.orm.helper.DropTableSchemaGenerator}
	 * and {@link com.orm.helper.ClassicSchemaGenerator}.
	 * <p>
	 * You can also create your own by extending {@link com.orm.helper.SugarSchemaGenerator}.
	 *
	 * @return a {@link com.orm.helper.SugarDatabaseHelper}, which defaults to {@link
	 * com.orm.helper.DropTableSchemaGenerator} if not overridden.
	 */
	public SugarDatabaseHelper getDatabaseHelper() {
		return new DropTableSchemaGenerator(this);
	}

	/**
	 * This is primarily used for backward compatability with legacy version of SugarORM.
	 * <p>
	 * This value has been changed fromt he classic "id" to the adnrodi standard "_id" so that we
	 * can work with ContentProviders.
	 *
	 * @return the name of the ID column in the database. Defaults to {@link
	 * android.provider.BaseColumns._ID} which will be "_id".
	 */
	@SuppressWarnings("JavadocReference")
	public String getIdColumnName() {
		return BaseColumns._ID;
	}

	/**
	 * The database name.
	 *
	 * @return
	 */
	public abstract String getDatabaseName();

	/**
	 * The database authority. This is important if you are using the SugarContentProvider class.
	 *
	 * @return
	 */
	public abstract String getAuthority();

	/**
	 * The version of the database. This will trigger the class returned by {@link
	 * #getDatabaseHelper()} when updated.
	 *
	 * @return
	 */
	public abstract int getDatabaseVersion();

	/**
	 * This method should return the model classes used by the the applicatons, annotated wioth the
	 * @Table annotations.
	 *
	 * @return
	 */
	public abstract Class<?>[] getModelClasses();

	/**
	 * Enable debugging.
	 *
	 * @return
	 */
	public abstract boolean isDebug();

	/**
	 * THis is for backward compatibility, but should no longer be used, as it does nto lwork with
	 * android gradle builds that don't use the dex plugin.
	 *
	 * @return
	 */
	public abstract String getDomain();
}
