package com.orm.inflater.field;

import android.database.Cursor;
import com.orm.SugarRecord;
import com.orm.helper.NamingHelper;

import java.lang.reflect.Field;

/**
 * Created by Łukasz Wesołowski on 03.08.2016.
 */
public class EntityFieldInflater extends FieldInflater {

    public EntityFieldInflater(Field field, Cursor cursor, Object object, Class<?> fieldType) {
        super(field, cursor, object, fieldType);
    }

    @Override
    public void inflate() {
        try {
            long id = cursor.getLong(cursor.getColumnIndex(NamingHelper.toColumnName(field)));
            field.set(object, (id > 0) ? SugarRecord.findById(fieldType, id) : null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
