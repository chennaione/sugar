package com.orm.inflater.field;

import android.database.Cursor;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by Łukasz Wesołowski on 03.08.2016.
 */
public class RelationEntityFieldInflater extends EntityFieldInflater {
    private static final String LOG_TAG = "RelEntityFieldInflater";

    protected Object relationObject;

    public RelationEntityFieldInflater(Field field, Cursor cursor, Object object, Class<?> fieldType, Object relationObject) {
        super(field, cursor, object, fieldType);
        this.relationObject = relationObject;
    }

    @Override
    public void inflate() {
        try {
            field.set(object, relationObject);
        } catch (IllegalAccessException e) {
            Log.e(LOG_TAG, String.format("Error while inflating %s field", field), e);
        }
    }
}
