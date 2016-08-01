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
	private static final SparseArray<TypeHolder> typeCodes = new SparseArray<TypeHolder>();
	private static SparseArray<String> mimeTypeCache = new SparseArray<String>();


	enum UriFlavour {
		TABLE, ITEM, FILTER
	}

	static class TypeHolder {
		UriFlavour flavour = UriFlavour.TABLE;
		Class<?> type = null;

		public TypeHolder(UriFlavour flavour, Class<?> type) {
			this.flavour = flavour;
			this.type = type;
		}

		public static final TypeHolder table(Class<?> type) {
			return new TypeHolder(UriFlavour.TABLE, type);
		}

		public static final TypeHolder item(Class<?> type) {
			return new TypeHolder(UriFlavour.ITEM, type);
		}

		public static final TypeHolder filter(Class<?> type) {
			return new TypeHolder(UriFlavour.FILTER, type);
		}
	}

	@Override
	public boolean onCreate() {

		// XXX This must always happen first.
		SugarContext.init(SugarConfiguration.manifest(getContext()));

		final SugarConfiguration configuration = SugarContext.getSugarContext().getConfiguration();
		DEBUG = configuration.isDebug();

		Log.d(TAG, "Debug mode enalbed: " + DEBUG);

		final String authority = configuration
				.getAuthority();

		if (DEBUG) {
			Log.d(TAG, "Content provider authority: " + authority);
		}

		List<Class> classList = ReflectionUtil
				.getDomainClasses(configuration);

		if (DEBUG) {
			Log.d(TAG, "Domain classes found: " + classList.size());
		}

		final int size = classList.size();
		for (int i = 0; i < size; i++) {
			Class<?> tableClass = classList.get(i);
			if (DEBUG) {
				Log.d(TAG, "Registering table for: " + tableClass.getSimpleName());
			}
			final int tableKey = (i * 3) + 1;
			final int itemKey = (i * 3) + 2;
			final int filterKey = (i * 3) + 3;

			// content://<authority>/<table>
			final String tablePath = NamingHelper.toSQLName(configuration, tableClass)
												 .toLowerCase();
			uriMatcher.addURI(authority, tablePath, tableKey);
			typeCodes.put(tableKey, TypeHolder.table(tableClass));
			if (DEBUG) {
				Log.d(TAG,
						"Registering [" + itemKey + "]: content://" + authority + "/" + tablePath);
			}

			// content://<authority>/<table>/<id>
			final String itemPath = tablePath + "/#";
			uriMatcher.addURI(authority, itemPath, itemKey);
			typeCodes.put(itemKey, TypeHolder.item(tableClass));
			if (DEBUG) {
				Log.d(TAG,
						"Registering [" + itemKey + "]: content://" + authority + "/" + itemPath);
			}

			// content://<authority>/<table>/filter/<term>
			final String filterPath = tablePath + "/filter/*";
			uriMatcher.addURI(authority, filterPath, filterKey);
			typeCodes.put(filterKey, TypeHolder.filter(tableClass));
			if (DEBUG) {
				Log.d(TAG,
						"Registering [" + itemKey + "]: content://" + authority + "/" + filterPath);
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

		final TypeHolder holder = getModelType(uri);
		final boolean single = UriFlavour.ITEM.equals(holder.flavour);

		String mimeType = getMimeType(holder.type, single);

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
		final TypeHolder holder = getModelType(uri);
		final Long id = getDatabase()
				.insert(NamingHelper.toSQLName(getConfiguration(), holder.type), null, values);

		if (id != null && id > 0) {
			Uri retUri = createUri(holder.type, id);
			notifyChange(retUri);

			return retUri;
		}

		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		final TypeHolder holder = getModelType(uri);
		final int count = getDatabase()
				.update(NamingHelper
						.toSQLName(getConfiguration(), holder.type), values, selection, selectionArgs);

		notifyChange(uri);

		return count;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		final TypeHolder holder = getModelType(uri);
		final int count = getDatabase()
				.delete(NamingHelper
						.toSQLName(getConfiguration(), holder.type), selection, selectionArgs);

		notifyChange(uri);

		return count;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

		final TypeHolder holder = getModelType(uri);
		Cursor cursor = null;
		switch (holder.flavour) {
			case TABLE:
				cursor = getDatabase().query(
						NamingHelper.toSQLName(getConfiguration(), holder.type),
						projection,
						selection,
						selectionArgs,
						null,
						null,
						sortOrder);
				break;
			case ITEM:

				String itemId = uri.getLastPathSegment();

				cursor = getDatabase().query(
						NamingHelper.toSQLName(getConfiguration(), holder.type),
						projection,
						selectionWithColumn(selection),
						selectionArgsWithId(itemId, selectionArgs),
						null,
						null,
						sortOrder);
				break;
			case FILTER:

				//selection = ProductTable.COLUMN_NAME + " like ?";
				//selectionArgs = new String[]{ "%" + filter +"%" };

				String term = uri.getLastPathSegment();

				cursor = getDatabase().query(
						NamingHelper.toSQLName(getConfiguration(), holder.type),
						projection,
						selectionWithColumn(getConfiguration().getIdColumnName(), selection),
						selectionArgsWithId(itemId, selectionArgs),
						null,
						null,
						sortOrder);

			default:
				throw new RuntimeException("Unknown content URI: " + uri);
		}


		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}


	/**
	 * @param type
	 * @param term
	 * @return
	 */
	public static Uri createFilterUri(Class<?> type, String term) {
		final StringBuilder uri = new StringBuilder();
		uri.append("content://");
		final SugarConfiguration configuration = SugarContext.getSugarContext().getConfiguration();
		uri.append(configuration.getAuthority());
		uri.append("/");
		uri.append(NamingHelper.toSQLName(configuration, type).toLowerCase());

		if (term != null) {
			uri.append("/filter/");
			uri.append(term);
		}

		return Uri.parse(uri.toString());
	}

	/**
	 * Greate a URI based on the type.
	 *
	 * @param type
	 * @param id
	 * @return
	 */
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


	private TypeHolder getModelType(Uri uri) {
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

	public String[] selectionArgsWithTerm(String term, String[] selectionArgs) {

		String[] buffArgs = selectionArgs;
		if (buffArgs == null) {
			return new String[]{"%" + term +"%"};

		}

		String[] outArgs = new String[buffArgs.length + 1];
		System.arraycopy(buffArgs, 0, outArgs, 1, buffArgs.length);
		outArgs[0] = "%" + term +"%";

		return outArgs;
	}

	public String[] selectionArgsWithId(String id, String[] selectionArgs) {

		String[] buffArgs = selectionArgs;
		if (buffArgs == null) {
			return new String[]{id};

		}

		String[] outArgs = new String[buffArgs.length + 1];
		System.arraycopy(buffArgs, 0, outArgs, 1, buffArgs.length);
		outArgs[0] = id;

		return outArgs;
	}

	public String selectionLikeColumn(String selection, String... columnNames) {

		StringBuilder builder = new StringBuilder();

		builder.append("(").append(columnName + " like ?").append(")");

		if (!isEmpty(selection)) {
			builder.append(" AND (").append(selection).append(")");
		}

		return builder.toString();
	}

	public String selectionWithId(String selection) {
		return selectionWithColumn(getConfiguration().getIdColumnName(), selection);
	}

	public String selectionWithColumn(String columnName, String selection) {

		StringBuilder builder = new StringBuilder();
		builder.append("(").append(columnName + " = ?").append(")");

		if (!isEmpty(selection)) {
			builder.append(" AND (").append(selection).append(")");
		}

		return builder.toString();
	}

	/**
	 * Returns true if the string is null or 0-length.
	 *
	 * @param str
	 * 		the string to be examined
	 * @return true if str is null or zero length
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
