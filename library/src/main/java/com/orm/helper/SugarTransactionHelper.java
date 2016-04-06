package com.orm.helper;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static com.orm.SugarContext.getSugarContext;

public final class SugarTransactionHelper {
    private static final String LOG_TAG = SugarTransactionHelper.class.getSimpleName();

    //Prevent instantiation..
    private SugarTransactionHelper() { }

    public static void doInTransaction(Callback callback) {
        final SQLiteDatabase database = getSugarContext().getSugarDb().getDB();
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

            if (database.isOpen()) {
                database.close();
            }
        }
    }

    public interface Callback {
        void manipulateInTransaction();
    }
}
