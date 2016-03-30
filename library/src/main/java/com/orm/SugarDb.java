package com.orm;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.orm.helper.SugarDatabaseHelper;
import com.orm.util.SugarCursorFactory;

public class SugarDb extends SQLiteOpenHelper {

	private final SugarDatabaseHelper databaseHelper;
	private SQLiteDatabase sqLiteDatabase;
	private int openedConnections = 0;

	public SugarDb(Configuration configuration) {
		super(configuration.getContext(), configuration.getDatabaseName(),
				new SugarCursorFactory(configuration.isDebug()), configuration
				.getDatabaseVersion());
		databaseHelper = configuration.getDatabaseHelper();
	}

	/**
	 * Called when the database is created for the first time. This is where the
	 * creation of tables and the initial population of the tables should happen.
	 *
	 * @param db
	 * 		The database.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		databaseHelper.onCreate(db);
	}

	/**
	 * Called when the database needs to be upgraded. The implementation
	 * should use this method to drop tables, add tables, or do anything else it
	 * needs to upgrade to the new schema version.
	 * <p>
	 * <p>
	 * The SQLite ALTER TABLE documentation can be found
	 * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
	 * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
	 * you can use ALTER TABLE to rename the old table, then create the new table and then
	 * populate the new table with the contents of the old table.
	 * </p><p>
	 * This method executes within a transaction.  If an exception is thrown, all changes
	 * will automatically be rolled back.
	 * </p>
	 *
	 * @param db
	 * 		The database.
	 * @param oldVersion
	 * 		The old database version.
	 * @param newVersion
	 * 		The new database version.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		databaseHelper.onUpgrade(db, oldVersion, newVersion);
	}


	/**
	 * Called when the database connection is being configured, to enable features
	 * such as write-ahead logging or foreign key support.
	 * <p>
	 * This method is called before {@link #onCreate}, {@link #onUpgrade},
	 * {@link #onDowngrade}, or {@link #onOpen} are called.  It should not modify
	 * the database except to configure the database connection as required.
	 * </p><p>
	 * This method should only call methods that configure the parameters of the
	 * database connection, such as {@link SQLiteDatabase#enableWriteAheadLogging}
	 * {@link SQLiteDatabase#setForeignKeyConstraintsEnabled},
	 * {@link SQLiteDatabase#setLocale}, {@link SQLiteDatabase#setMaximumSize},
	 * or executing PRAGMA statements.
	 * </p>
	 *
	 * @param db
	 * 		The database.
	 */
	public void onConfigure(SQLiteDatabase db) {
		databaseHelper.onConfigure(db);
	}

	/**
	 * Called when the database needs to be downgraded. This is strictly similar to
	 * {@link #onUpgrade} method, but is called whenever current version is newer than requested
	 * one.
	 * However, this method is not abstract, so it is not mandatory for a customer to
	 * implement it. If not overridden, default implementation will reject downgrade and
	 * throws SQLiteException
	 * <p>
	 * <p>
	 * This method executes within a transaction.  If an exception is thrown, all changes
	 * will automatically be rolled back.
	 * </p>
	 *
	 * @param db
	 * 		The database.
	 * @param oldVersion
	 * 		The old database version.
	 * @param newVersion
	 * 		The new database version.
	 */
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		databaseHelper.onDowngrade(db, oldVersion, newVersion);
	}

	/**
	 * Called when the database has been opened.  The implementation
	 * should check {@link SQLiteDatabase#isReadOnly} before updating the
	 * database.
	 * <p>
	 * This method is called after the database connection has been configured
	 * and after the database schema has been created, upgraded or downgraded as necessary.
	 * If the database connection must be configured in some way before the schema
	 * is created, upgraded, or downgraded, do it in {@link #onConfigure} instead.
	 * </p>
	 *
	 * @param db
	 * 		The database.
	 */
	public void onOpen(SQLiteDatabase db) {
		databaseHelper.onOpen(db);
	}

	public synchronized SQLiteDatabase getDB() {
		if (this.sqLiteDatabase == null) {
			this.sqLiteDatabase = getWritableDatabase();
		}

		return this.sqLiteDatabase;
	}

	@Override
	public synchronized SQLiteDatabase getReadableDatabase() {
		Log.d("TAG", "getReadableDatabase");
		openedConnections++;
		return super.getReadableDatabase();
	}

	@Override
	public synchronized void close() {
		Log.d("TAG", "getReadableDatabase");
		openedConnections--;
		if (openedConnections == 0) {
			Log.d("TAG", "closing");
			super.close();
		}
	}
}
