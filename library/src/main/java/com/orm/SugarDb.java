package com.orm;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.orm.util.SugarCursorFactory;

import java.util.Locale;

import static com.orm.util.ManifestHelper.getDatabaseVersion;
import static com.orm.util.ManifestHelper.getDebugEnabled;
import static com.orm.util.ManifestHelper.getDbName;

import static com.orm.util.ContextUtil.*;

public class SugarDb extends SQLiteOpenHelper {
    private static final String LOG_TAG = "Sugar";

    private final SchemaGenerator schemaGenerator;
    private SQLiteDatabase sqLiteDatabase;
    private int openedConnections = 0;

    //Prevent instantiation
    private SugarDb() {
        super(getContext(), getDbName(), new SugarCursorFactory(getDebugEnabled()), getDatabaseVersion());
        schemaGenerator = SchemaGenerator.getInstance();
    }

    public static SugarDb getInstance() {
        return new SugarDb();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        schemaGenerator.createDatabase(sqLiteDatabase);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setLocale(Locale.getDefault());
        super.onConfigure(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        schemaGenerator.doUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }

    public synchronized SQLiteDatabase getDB() {
        if (this.sqLiteDatabase == null) {
            this.sqLiteDatabase = getWritableDatabase();
        }

        return this.sqLiteDatabase;
    }

    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        Log.d(LOG_TAG, "getReadableDatabase");
        openedConnections++;
        return super.getReadableDatabase();
    }

    @Override
    public synchronized void close() {
        Log.d(LOG_TAG, "getReadableDatabase");
        openedConnections--;
        if(openedConnections == 0) {
            Log.d(LOG_TAG, "closing");
            super.close();
        }
    }
}
