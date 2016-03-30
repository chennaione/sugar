package com.orm.util;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.provider.BaseColumns;

public class SugarCursor extends CursorWrapper {
	public SugarCursor(Cursor cursor) {
		super(cursor);
	}

	@Override
	public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
		try {
			return super.getColumnIndexOrThrow(columnName);
		} catch (IllegalArgumentException e) {
			if (columnName.equals("_id") || columnName.equalsIgnoreCase("ID")) {
				return super.getColumnIndexOrThrow(BaseColumns._ID);
			} else {
				throw e;
			}
		}
	}

	@Override
	public int getColumnIndex(String columnName) {
		if (columnName.equals("_id") || columnName.equalsIgnoreCase("ID")) {
			columnName = BaseColumns._ID;
		}
		return super.getColumnIndex(columnName);
	}
}
