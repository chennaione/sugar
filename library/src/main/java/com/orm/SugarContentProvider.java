package com.orm;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.util.SparseArray;

import com.orm.util.NamingHelper;
import com.orm.util.ReflectionUtil;

import java.util.List;

/**
 * To use this class, you must use the manifest version for configuration, because the content
 * provider may be created before the application.
 * <p/>
 * Created by bpappin on 16-03-29.
 */
public class SugarContentProvider extends android.content.ContentProvider {
	private static final String TAG = "SugarContentProvider";
	private static boolean DEBUG;

	private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	private static final SparseArray<Class<?>> typeCodes = new SparseArray<Class<?>>();
	private static SparseArray<String> mimeTypeCache = new SparseArray<String>();

	@Override
	public boolean onCreate() {

		// XXX This must always happen first.
		SugarContext.init(SugarConfiguration.manifest(getContext()));

		final SugarConfiguration configuration = SugarContext.getSugarContext().getConfiguration();
		DEBUG = configuration.isDebug();

		Log.d(TAG, "Debug mode enalbed: " + DEBUG);

		final String authority = configuration
				.getAuthority();

		if(DEBUG) {
			Log.d(TAG, "Content provider authority: " + authority);
		}

		List<Class> classList = ReflectionUtil
				.getDomainClasses(configuration);

		if(DEBUG) {
			Log.d(TAG, "Domain classes found: " + classList.size());
		}

		final int size = classList.size();
		for (int i = 0; i < size; i++) {
			Class<?> tableClass = classList.get(i);
			if (DEBUG) {
				Log.d(TAG, "Registering table for: " + tableClass.getSimpleName());
			}
			final int tableKey = (i * 2) + 1;
			final int itemKey = (i * 2) + 2;

			// content://<authority>/<table>
			uriMatcher
					.addURI(authority, NamingHelper.toSQLName(configuration, tableClass)
												   .toLowerCase(), tableKey);
			typeCodes.put(tableKey, tableClass);
			if (DEBUG) {
				Log.d(TAG, "Registering table key: " + tableKey + " for class " +
						   tableClass.getSimpleName());
			}

			// content://<authority>/<table>/<id>
			uriMatcher.addURI(authority,
					NamingHelper.toSQLName(configuration, tableClass).toLowerCase() +
					"/#", itemKey);
			typeCodes.put(itemKey, tableClass);
			if (DEBUG) {
				Log.d(TAG, "Registering item key: " + itemKey + " for class " +
						   tableClass.getSimpleName());
			}
		}

		return true;
	}

	@Override
	public String getType(Uri uri) {
		final int match = uriMatcher.match(uri);

		String cachedMimeType = mimeTypeCache.get(match);
		if (cachedMimeType != null) {
			return cachedMimeType;
		}

		final Class<?> type = getModelType(uri);
		final boolean single = ((match % 2) == 0);

		String mimeType = getMimeType(type, single);

		mimeTypeCache.append(match, mimeType);

		return mimeType;
	}

	private String getMimeType(Class<?> type, boolean single) {
		StringBuilder mimeTypeBufer = new StringBuilder();
		mimeTypeBufer.append("vnd");
		mimeTypeBufer.append(".");
		mimeTypeBufer.append(SugarContext.getSugarContext().getConfiguration().getAuthority());
		mimeTypeBufer.append(".");
		mimeTypeBufer.append(single ? "item" : "dir");
		mimeTypeBufer.append("/");
		mimeTypeBufer.append("vnd");
		mimeTypeBufer.append(".");
		mimeTypeBufer.append(SugarContext.getSugarContext().getConfiguration().getAuthority());
		mimeTypeBufer.append(".");
		mimeTypeBufer.append(NamingHelper.toSQLName(getConfiguration(), type));
		return mimeTypeBufer.toString();
	}


	@Override
	public Uri insert(Uri uri, ContentValues values) {
		final Class<?> type = getModelType(uri);
		final Long id = getDatabase()
				.insert(NamingHelper.toSQLName(getConfiguration(), type), null, values);

		if (id != null && id > 0) {
			Uri retUri = createUri(type, id);
			notifyChange(retUri);

			return retUri;
		}

		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		final Class<?> type = getModelType(uri);
		final int count = getDatabase()
				.update(NamingHelper
						.toSQLName(getConfiguration(), type), values, selection, selectionArgs);

		notifyChange(uri);

		return count;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		final Class<?> type = getModelType(uri);
		final int count = getDatabase()
				.delete(NamingHelper.toSQLName(getConfiguration(), type), selection, selectionArgs);

		notifyChange(uri);

		return count;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

		final Class<?> type = getModelType(uri);
		final Cursor cursor = getDatabase().query(
				NamingHelper.toSQLName(getConfiguration(), type),
				projection,
				selection,
				selectionArgs,
				null,
				null,
				sortOrder);

		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}


	public static Uri createUri(Class<?> type, Long id) {
		final StringBuilder uri = new StringBuilder();
		uri.append("content://");
		final SugarConfiguration configuration = SugarContext.getSugarContext().getConfiguration();
		uri.append(configuration.getAuthority());
		uri.append("/");
		uri.append(NamingHelper.toSQLName(configuration, type).toLowerCase());

		if (id != null) {
			uri.append("/");
			uri.append(id.toString());
		}

		return Uri.parse(uri.toString());
	}


	private Class<?> getModelType(Uri uri) {
		if (getConfiguration().isDebug()) {
			Log.d(TAG, "Getting model type for URI: " + uri);
		}
		final int code = uriMatcher.match(uri);
		if (getConfiguration().isDebug()) {
			Log.d(TAG, "\tGot matcher type code: " + code);
		}
		if (code != UriMatcher.NO_MATCH) {
			if (getConfiguration().isDebug()) {
				Log.d(TAG, "\tType code found...");
			}
			return typeCodes.get(code);
		}
		if (getConfiguration().isDebug()) {
			Log.d(TAG, "\tType code NO_MATCH.");
		}
		return null;
	}

	private void notifyChange(Uri uri) {
		getContext().getContentResolver().notifyChange(uri, null);
	}
	
	
	public SQLiteDatabase getDatabase() {
		return SugarContext.getSugarContext().getSugarDb().getDB();
	}
	
	public SugarConfiguration getConfiguration() {
		return SugarContext.getSugarContext().getConfiguration();
	}
}