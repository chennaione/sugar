package com.orm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.util.Log;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.orm.util.SugarConfig;
import com.orm.util.SugarCursorFactory;

import static com.orm.util.SugarConfig.getDatabaseVersion;
import static com.orm.util.SugarConfig.getDebugEnabled;

public class SugarDb extends SQLiteOpenHelper {
    private final SchemaGenerator schemaGenerator;
    private SQLiteDatabase sqLiteDatabase;

    public SugarDb(Context context) {
        super(context, SugarConfig.getDatabaseName(context), new SugarCursorFactory(getDebugEnabled(context)), getDatabaseVersion(context));
        schemaGenerator = new SchemaGenerator(context);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        schemaGenerator.createDatabase(sqLiteDatabase);
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

    public static void dropTestDatabase() {
        SugarApp.getSugarContext().deleteDatabase(SugarConfig.getDatabaseName(SugarApp.getSugarContext(), true));
    }

    public static void prepareTestDatabase() {
        File dbFile = SugarApp.getSugarContext().getDatabasePath(
                SugarConfig.getDatabaseName(SugarApp.getSugarContext(), false));
        File dbFileTest = SugarApp.getSugarContext().getDatabasePath(
                SugarConfig.getDatabaseName(SugarApp.getSugarContext(), true));

        try {
            if (dbFile.exists()) {
                InputStream in = new FileInputStream(dbFile);
                OutputStream out = new FileOutputStream(dbFileTest);

                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            }
            Log.d("Sugar", "Copy database from development to tests");
        } catch (IOException e) {
            Log.d("Sugar", "Couldn't copy development database to tests database");
            e.printStackTrace();
        }
    }
}
