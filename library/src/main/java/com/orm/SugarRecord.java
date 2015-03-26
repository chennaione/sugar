package com.orm;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;
import android.util.Log;

import com.orm.dsl.Table;
import com.orm.util.NamingHelper;
import com.orm.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static com.orm.SugarContext.getSugarContext;

public class SugarRecord {

    protected Long id = null;

    public static <T> void deleteAll(Class<T> type) {
        SugarDb db = getSugarContext().getSugarDb();
        SQLiteDatabase sqLiteDatabase = db.getDB();
        sqLiteDatabase.delete(NamingHelper.toSQLName(type), null, null);
    }

    public static <T> void deleteAll(Class<T> type, String whereClause, String... whereArgs) {
        SugarDb db = getSugarContext().getSugarDb();
        SQLiteDatabase sqLiteDatabase = db.getDB();
        sqLiteDatabase.delete(NamingHelper.toSQLName(type), whereClause, whereArgs);
    }

    @SuppressWarnings("deprecation")
    public static <T> void saveInTx(T... objects) {
        saveInTx(Arrays.asList(objects));
    }

    @SuppressWarnings("deprecation")
    public static <T> void saveInTx(Collection<T> objects) {
        SQLiteDatabase sqLiteDatabase = getSugarContext().getSugarDb().getDB();
        try {
            sqLiteDatabase.beginTransaction();
            sqLiteDatabase.setLockingEnabled(false);
            for (T object: objects) {
                SugarRecord.save(object);
            }
            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            Log.i("Sugar", "Error in saving in transaction " + e.getMessage());
        } finally {
            sqLiteDatabase.endTransaction();
            sqLiteDatabase.setLockingEnabled(true);
        }
    }

    public static <T> List<T> listAll(Class<T> type) {
        return find(type, null, null, null, null, null);
    }
    
    public static <T> List<T> listAll(Class<T> type, String orderBy) {
        return find(type, null, null, null, orderBy, null);
    }

    public static <T> T findById(Class<T> type, Long id) {
        List<T> list = find(type, "id=?", new String[]{String.valueOf(id)}, null, null, "1");
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    public static <T> T findById(Class<T> type, Integer id) {
        return findById(type, Long.valueOf(id));
    }

    public static <T> Iterator<T> findAll(Class<T> type) {
        return findAsIterator(type, null, null, null, null, null);
    }

    public static <T> Iterator<T> findAsIterator(Class<T> type, String whereClause, String... whereArgs) {
        return findAsIterator(type, whereClause, whereArgs, null, null, null);
    }

    public static <T> Iterator<T> findWithQueryAsIterator(Class<T> type, String query, String... arguments) {
        SugarDb db = getSugarContext().getSugarDb();
        SQLiteDatabase sqLiteDatabase = db.getDB();
        Cursor c = sqLiteDatabase.rawQuery(query, arguments);
        return new CursorIterator<T>(type, c);
    }

    public static <T> Iterator<T> findAsIterator(Class<T> type, String whereClause, String[] whereArgs, String groupBy, String orderBy, String limit) {
        SugarDb db = getSugarContext().getSugarDb();
        SQLiteDatabase sqLiteDatabase = db.getDB();
        Cursor c = sqLiteDatabase.query(NamingHelper.toSQLName(type), null, whereClause, whereArgs,
                groupBy, null, orderBy, limit);
        return new CursorIterator<T>(type, c);
    }

    public static <T> List<T> find(Class<T> type, String whereClause, String... whereArgs) {
        return find(type, whereClause, whereArgs, null, null, null);
    }

    public static <T> List<T> findWithQuery(Class<T> type, String query, String... arguments) {
        SugarDb db = getSugarContext().getSugarDb();
        SQLiteDatabase sqLiteDatabase = db.getDB();
        T entity;
        List<T> toRet = new ArrayList<T>();
        Cursor c = sqLiteDatabase.rawQuery(query, arguments);

        try {
            while (c.moveToNext()) {
                entity = type.getDeclaredConstructor().newInstance();
                SugarRecord.inflate(c, entity);
                toRet.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c.close();
        }

        return toRet;
    }

    public static void executeQuery(String query, String... arguments) {
        getSugarContext().getSugarDb().getDB().execSQL(query, arguments);
    }

    public static <T> List<T> find(Class<T> type, String whereClause, String[] whereArgs, String groupBy, String orderBy, String limit) {
        SugarDb db = getSugarContext().getSugarDb();
        SQLiteDatabase sqLiteDatabase = db.getDB();
        T entity;
        List<T> toRet = new ArrayList<T>();
        Cursor c = sqLiteDatabase.query(NamingHelper.toSQLName(type), null, whereClause, whereArgs,
                groupBy, null, orderBy, limit);
        try {
            while (c.moveToNext()) {
                entity = type.getDeclaredConstructor().newInstance();
                SugarRecord.inflate(c, entity);
                toRet.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c.close();
        }
        return toRet;
    }

    public static <T> long count(Class<?> type) {
        return count(type, null, null, null, null, null);
    }

    public static <T> long count(Class<?> type, String whereClause, String[] whereArgs) {
    	return count(type, whereClause, whereArgs, null, null, null);
    }

    public static <T> long count(Class<?> type, String whereClause, String[] whereArgs, String groupBy, String orderBy, String limit) {
        SugarDb db = getSugarContext().getSugarDb();
        SQLiteDatabase sqLiteDatabase = db.getDB();

        long toRet = -1;
        String filter = (!TextUtils.isEmpty(whereClause)) ? " where "  + whereClause : "";
        SQLiteStatement sqliteStatement;
        try {
            sqliteStatement = sqLiteDatabase.compileStatement("SELECT count(*) FROM " + NamingHelper.toSQLName(type) + filter);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return toRet;
        }

        if (whereArgs != null) {
            for (int i = whereArgs.length; i != 0; i--) {
                sqliteStatement.bindString(i, whereArgs[i - 1]);
            }
        }

        try {
            toRet = sqliteStatement.simpleQueryForLong();
        } finally {
            sqliteStatement.close();
        }

        return toRet;
    }

    public static long save(Object object) {
        return save(getSugarContext().getSugarDb().getDB(), object);
    }

    static long save(SQLiteDatabase db, Object object) {
        List<Field> columns = ReflectionUtil.getTableFields(object.getClass());
        ContentValues values = new ContentValues(columns.size());
        Field idField = null;
        for (Field column : columns) {
            ReflectionUtil.addFieldValueToColumn(values, column, object);
            if (column.getName() == "id") {
                idField = column;
            }
        }

        long id = db.insertWithOnConflict(NamingHelper.toSQLName(object.getClass()), null, values,
                SQLiteDatabase.CONFLICT_REPLACE);

        if (object.getClass().isAnnotationPresent(Table.class)) {
            if (idField != null) {
                idField.setAccessible(true);
                try {
                    idField.set(object, new Long(id));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } else if (SugarRecord.class.isAssignableFrom(object.getClass())) {
            ((SugarRecord) object).setId(id);
        }

        Log.i("Sugar", object.getClass().getSimpleName() + " saved : " + id);

        return id;
    }

    private static void inflate(Cursor cursor, Object object) {
        List<Field> columns = ReflectionUtil.getTableFields(object.getClass());

        for (Field field : columns) {
        	field.setAccessible(true);
            Class<?> fieldType = field.getType();
            if (fieldType.isAnnotationPresent(Table.class) ||
                    SugarRecord.class.isAssignableFrom(fieldType)) {
                try {
                    long id = cursor.getLong(cursor.getColumnIndex(NamingHelper.toSQLName(field)));
                    field.set(object, (id > 0) ? findById(fieldType, id) : null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                ReflectionUtil.setFieldValueFromCursor(cursor, field, object);
            }
        }
    }

    public void delete() {
        SQLiteDatabase db = getSugarContext().getSugarDb().getDB();
        db.delete(NamingHelper.toSQLName(getClass()), "Id=?", new String[]{getId().toString()});
        Log.i("Sugar", getClass().getSimpleName() + " deleted : " + getId().toString());
    }

    public long save() {
        return save(getSugarContext().getSugarDb().getDB(), this);
    }

    @SuppressWarnings("unchecked")
    void inflate(Cursor cursor) {
        inflate(cursor, this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    static class CursorIterator<E> implements Iterator<E> {
        Class<E> type;
        Cursor cursor;

        public CursorIterator(Class<E> type, Cursor cursor) {
            this.type = type;
            this.cursor = cursor;
        }

        @Override
        public boolean hasNext() {
            return cursor != null && !cursor.isClosed() && !cursor.isAfterLast();
        }

        @Override
        public E next() {
            E entity = null;
            if (cursor == null || cursor.isAfterLast()) {
                throw new NoSuchElementException();
            }

            if (cursor.isBeforeFirst()) {
                cursor.moveToFirst();
            }

            try {
                entity = type.getDeclaredConstructor().newInstance();
                SugarRecord.inflate(cursor, entity);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.moveToNext();
                if (cursor.isAfterLast()) {
                    cursor.close();
                }
            }

            return entity;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
