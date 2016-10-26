package com.orm.util;

import net.sqlcipher.Cursor;
import net.sqlcipher.CursorWrapper;

public class SugarCursor extends CursorWrapper {

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
        if (columnName.equals("_id"))
            columnName = "ID";
        return super.getColumnIndex(columnName);
    }
}
