package com.orm;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;

import static com.orm.SugarApp.getSugarContext;

public class SugarRecord<T>{

    protected Long id = null;

    public static <T extends SugarRecord<?>> void deleteAll(Class<T> type) {
        Database db = getSugarContext().getDatabase();
        SQLiteDatabase sqLiteDatabase = db.getDB();
        sqLiteDatabase.delete(StringUtil.toSQLName(type), null, null);
    }

    public static <T extends SugarRecord<?>> void deleteAll(Class<T> type, String whereClause, String... whereArgs ) {
        Database db = getSugarContext().getDatabase();
        SQLiteDatabase sqLiteDatabase = db.getDB();
        sqLiteDatabase.delete(StringUtil.toSQLName(type), whereClause, whereArgs);
    }

    @SuppressWarnings("deprecation")
    public static <T extends SugarRecord<?>> void saveInTx(T... objects ) {
        saveInTx(Arrays.asList(objects));
    }

    @SuppressWarnings("deprecation")
    public static <T extends SugarRecord<?>> void saveInTx(Collection<T> objects ) {
        SQLiteDatabase sqLiteDatabase = getSugarContext().getDatabase().getDB();

        try{
            sqLiteDatabase.beginTransaction();
            sqLiteDatabase.setLockingEnabled(false);
            for(T object: objects){
                object.save(sqLiteDatabase);
            }
            sqLiteDatabase.setTransactionSuccessful();
        }catch (Exception e){
            Log.i("Sugar", "Error in saving in transaction " + e.getMessage());
        }finally {
            sqLiteDatabase.endTransaction();
            sqLiteDatabase.setLockingEnabled(true);
        }

    }

    public static <T extends SugarRecord<?>> List<T> listAll(Class<T> type) {
        return find(type, null, null, null, null, null);
    }

    public static <T extends SugarRecord<?>> T findById(Class<T> type, Long id) {
        List<T> list = find(type, "id=?", new String[]{String.valueOf(id)}, null, null, "1");
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    public static <T extends SugarRecord<?>> T findById(Class<T> type, Integer id) {
        return findById(type, Long.valueOf(id));
    }

    public static <T extends SugarRecord<?>> Iterator<T> findAll(Class<T> type) {
        return findAsIterator(type, null, null, null, null, null);
    }

    public static <T extends SugarRecord<?>> Iterator<T> findAsIterator(Class<T> type,
                                                                        String whereClause, String... whereArgs) {
        return findAsIterator(type, whereClause, whereArgs, null, null, null);
    }

    public static <T extends SugarRecord<?>> Iterator<T> findWithQueryAsIterator(Class<T> type, String query, String... arguments) {
        Database db = getSugarContext().getDatabase();
        SQLiteDatabase sqLiteDatabase = db.getDB();
        Cursor c = sqLiteDatabase.rawQuery(query, arguments);
        return new CursorIterator<T>(type, c);
    }

    public static <T extends SugarRecord<?>> Iterator<T> findAsIterator(Class<T> type,
                                                                    String whereClause, String[] whereArgs,
                                                                    String groupBy, String orderBy, String limit) {

        Database db = getSugarContext().getDatabase();
        SQLiteDatabase sqLiteDatabase = db.getDB();
        Cursor c = sqLiteDatabase.query(StringUtil.toSQLName(type), null,
                whereClause, whereArgs, groupBy, null, orderBy, limit);
        return new CursorIterator<T>(type, c);
    }

    public static <T extends SugarRecord<?>> List<T> find(Class<T> type,
                                                       String whereClause, String... whereArgs) {
        return find(type, whereClause, whereArgs, null, null, null);
    }

    public static <T extends SugarRecord<?>> List<T> findWithQuery(Class<T> type, String query, String... arguments){

        Database db = getSugarContext().getDatabase();
        SQLiteDatabase sqLiteDatabase = db.getDB();
        T entity;
        List<T> toRet = new ArrayList<T>();
        Cursor c = sqLiteDatabase.rawQuery(query, arguments);

        try {
            while (c.moveToNext()) {
                entity = type.getDeclaredConstructor().newInstance();
                entity.inflate(c);
                toRet.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c.close();
        }
        return toRet;
    }

    public static void executeQuery(String query, String... arguments){
        getSugarContext().getDatabase().getDB().execSQL(query, arguments);
    }

    public static <T extends SugarRecord<?>> List<T> find(Class<T> type,
                                                       String whereClause, String[] whereArgs,
                                                       String groupBy, String orderBy, String limit) {
        Database db = getSugarContext().getDatabase();
        SQLiteDatabase sqLiteDatabase = db.getDB();
        T entity;
        List<T> toRet = new ArrayList<T>();
        Cursor c = sqLiteDatabase.query(StringUtil.toSQLName(type), null,
                whereClause, whereArgs, groupBy, null, orderBy, limit);
        try {
            while (c.moveToNext()) {
                entity = type.getDeclaredConstructor().newInstance();
                entity.inflate(c);
                toRet.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c.close();
        }
        return toRet;
    }

    public static <T extends SugarRecord<?>> long count(Class<?> type) {
        return count(type, null, null, null, null, null);
    }

    public static <T extends SugarRecord<?>> long count(Class<?> type,
            String whereClause, String[] whereArgs) {
    	return count(type, whereClause, whereArgs, null, null, null);
    }

    public static <T extends SugarRecord<?>> long count(Class<?> type,
            String whereClause, String[] whereArgs,
            String groupBy, String orderBy, String limit) {

    	Database db = getSugarContext().getDatabase();
        SQLiteDatabase sqLiteDatabase = db.getDB();

        long toRet = -1;
        String filter = (!TextUtils.isEmpty(whereClause)) ? " where "  + whereClause : "";
        SQLiteStatement sqLiteStatament = sqLiteDatabase.compileStatement("SELECT count(*) FROM " + StringUtil.toSQLName(type) + filter);

        if (whereArgs != null) {
            for (int i = whereArgs.length; i != 0; i--) {
                sqLiteStatament.bindString(i, whereArgs[i - 1]);
            }
        }

        try {
            toRet = sqLiteStatament.simpleQueryForLong();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqLiteStatament.close();
        }

        return toRet;
    }

    public void delete() {
        SQLiteDatabase db = getSugarContext().getDatabase().getDB();
        db.delete(StringUtil.toSQLName(getClass()), "Id=?", new String[]{getId().toString()});
    }

    public long save() {
        return save(getSugarContext().getDatabase().getDB());
    }

    long save(SQLiteDatabase db) {

        List<Field> columns = SugarDb.getTableFields(getClass());
        ContentValues values = new ContentValues(columns.size());
        for (Field column : columns) {
            column.setAccessible(true);
            Class<?> columnType = column.getType();
            try {
                String columnName = StringUtil.toSQLName(column);
                Object columnValue = column.get(this);
                if (SugarRecord.class.isAssignableFrom(columnType)) {
                    values.put(columnName,
                            (columnValue != null)
                                    ? String.valueOf(((SugarRecord) columnValue).id)
                                    : "0");
                } else {
                    if (columnType.equals(Short.class) || columnType.equals(short.class)) {
                        values.put(columnName, (Short) columnValue);
                    } else if (columnType.equals(Integer.class) || columnType.equals(int.class)) {
                        values.put(columnName, (Integer) columnValue);
                    } else if (columnType.equals(Long.class) || columnType.equals(long.class)) {
                        values.put(columnName, (Long) columnValue);
                    } else if (columnType.equals(Float.class) || columnType.equals(float.class)) {
                        values.put(columnName, (Float) columnValue);
                    } else if (columnType.equals(Double.class) || columnType.equals(double.class)) {
                        values.put(columnName, (Double) columnValue);
                    } else if (columnType.equals(Boolean.class) || columnType.equals(boolean.class)) {
                        values.put(columnName, (Boolean) columnValue);
                    } else if (Date.class.equals(columnType)) {
                        try {
                            values.put(columnName, ((Date) column.get(this)).getTime());
                        } catch (NullPointerException e) {
                            values.put(columnName, (Long) null);
                        }
                    } else if (Calendar.class.equals(columnType)) {
                        try {
                            values.put(columnName, ((Calendar) column.get(this)).getTimeInMillis());
                        } catch (NullPointerException e) {
                            values.put(columnName, (Long) null);
                        }
                    } else {
                        if (columnValue == null) {
                            values.putNull(columnName);
                        } else {
                            values.put(columnName, String.valueOf(columnValue));
                        }
                    }
                }

            } catch (IllegalAccessException e) {
                Log.e("Sugar", e.getMessage());
            }
        }

        id = db.insertWithOnConflict(StringUtil.toSQLName(getClass()), null, values, SQLiteDatabase.CONFLICT_REPLACE);

        Log.i("Sugar", getClass().getSimpleName() + " saved : " + id);
        return id;
    }

    @SuppressWarnings("unchecked")
    void inflate(Cursor cursor) {
        Map<Field, Long> entities = new HashMap<Field, Long>();
        List<Field> columns = SugarDb.getTableFields(getClass());
        for (Field field : columns) {
            field.setAccessible(true);
            try {
                Class fieldType = field.getType();
                String colName = StringUtil.toSQLName(field);

                int columnIndex = cursor.getColumnIndex(colName);

                if (cursor.isNull(columnIndex)) {
                    continue;
                }

                if(colName.equalsIgnoreCase("id")){
                    long cid = cursor.getLong(columnIndex);
                    field.set(this, Long.valueOf(cid));
                }else if (fieldType.equals(long.class) || fieldType.equals(Long.class)) {
                    field.set(this,
                            cursor.getLong(columnIndex));
                } else if (fieldType.equals(String.class)) {
                    String val = cursor.getString(columnIndex);
                    field.set(this, val != null && val.equals("null") ? null : val);
                } else if (fieldType.equals(double.class) || fieldType.equals(Double.class)) {
                    field.set(this,
                            cursor.getDouble(columnIndex));
                } else if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
                    field.set(this,
                            cursor.getString(columnIndex).equals("1"));
                } else if (field.getType().getName().equals("[B")) {
                    field.set(this,
                            cursor.getBlob(columnIndex));
                } else if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
                    field.set(this,
                            cursor.getInt(columnIndex));
                } else if (fieldType.equals(float.class) || fieldType.equals(Float.class)) {
                    field.set(this,
                            cursor.getFloat(columnIndex));
                } else if (fieldType.equals(short.class) || fieldType.equals(Short.class)) {
                    field.set(this,
                            cursor.getShort(columnIndex));
                } else if (fieldType.equals(Timestamp.class)) {
                    long l = cursor.getLong(columnIndex);
                    field.set(this, new Timestamp(l));
                } else if (fieldType.equals(Date.class)) {
                    long l = cursor.getLong(columnIndex);
                    field.set(this, new Date(l));
                } else if (fieldType.equals(Calendar.class)) {
                    long l = cursor.getLong(columnIndex);
                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(l);
                    field.set(this, c);
                } else if (Enum.class.isAssignableFrom(fieldType)) {
                    try {
                        Method valueOf = field.getType().getMethod("valueOf", String.class);
                        String strVal = cursor.getString(columnIndex);
                        Object enumVal = valueOf.invoke(field.getType(), strVal);
                        field.set(this, enumVal);
                    } catch (Exception e) {
                        Log.e("Sugar", "Enum cannot be read from Sqlite3 database. Please check the type of field " + field.getName());
                    }
                } else if (SugarRecord.class.isAssignableFrom(fieldType)) {
                    long id = cursor.getLong(columnIndex);
                    if (id > 0)
                        entities.put(field, id);
                    else
                        field.set(this, null);
                } else
                    Log.e("Sugar", "Class cannot be read from Sqlite3 database. Please check the type of field " + field.getName() + "(" + field.getType().getName() + ")");
            } catch (IllegalArgumentException e) {
                Log.e("field set error", e.getMessage());
            } catch (IllegalAccessException e) {
                Log.e("field set error", e.getMessage());
            }

        }

        for (Field f : entities.keySet()) {
            try {
                f.set(this, findById((Class<? extends SugarRecord<?>>) f.getType(), 
                        entities.get(f)));
            } catch (SQLiteException e) {
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    static class CursorIterator<E extends SugarRecord<?>> implements Iterator<E> {
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
                entity.inflate(cursor);
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
