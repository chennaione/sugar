package com.orm;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SugarTransactionHelper {
    private static final String LOG_TAG = SugarTransactionHelper.class.getSimpleName();

    public static void doInTransaction(SugarTransactionHelper.Callback callback) {
        SQLiteDatabase database = SugarContext.getSugarContext().getSugarDb().getDB();
        database.beginTransaction();

        try {
            Log.d(LOG_TAG, "Callback executing within transaction");

            callback.manipulateInTransaction();
            database.setTransactionSuccessful();

            Log.d(LOG_TAG, "Callback successfully executed within transaction");
        } catch (Throwable e) {
            Log.d(LOG_TAG, "Could execute callback within transaction", e);
        } finally {
            database.endTransaction();
        }
    }

    public interface Callback {
        void manipulateInTransaction();
    }
}
