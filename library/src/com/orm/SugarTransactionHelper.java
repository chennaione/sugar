package com.orm;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SugarTransactionHelper {

    public static void doInTansaction(SugarTransactionHelper.Callback callback) {

        SQLiteDatabase database = SugarApp.getSugarContext().getDatabase().getDB();

        database.beginTransaction();

        try {
            Log.d(SugarTransactionHelper.class.getSimpleName(), "callback executing within transaction");
            callback.manipulateInTransaction();
            database.setTransactionSuccessful();
            Log.d(SugarTransactionHelper.class.getSimpleName(), "callback successfully executed within transaction");
        } catch (Throwable e) {
            Log.d(SugarTransactionHelper.class.getSimpleName(), "could execute callback within transaction", e);
        } finally {
            database.endTransaction();
        }
    }

    public static interface Callback {
        void manipulateInTransaction();
    }
}
