package com.orm.util;

import android.database.Cursor;
import android.database.CursorWrapper;

public class SugarCursor extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public SugarCursor(Cursor cursor) {
        super(cursor);
    }

    @Override
    public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
        try {
            return super.getColumnIndexOrThrow(columnName);
        } catch (IllegalArgumentException e) {
            if (columnName.equals("_id"))
                return super.getColumnIndexOrThrow("ID");
            else
                throw e;
        }
    }

    @Override
    public int getColumnIndex(String columnName) {
        try {
            return super.getColumnIndex(columnName);
        } catch (Exception e) {
            if (columnName.equals("_id"))
                return super.getColumnIndex("ID");
            else
                throw e;
        }
    }
}
