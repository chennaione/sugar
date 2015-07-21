package com.orm;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;
import android.util.Log;

import com.orm.dsl.Relationship;
import com.orm.dsl.Table;
import com.orm.util.NamingHelper;
import com.orm.util.ReflectionUtil;
import com.orm.util.QueryBuilder;

import java.lang.String;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.orm.SugarContext.getSugarContext;

public class SugarRecord {

    private Long id = null;

    public static <T> int deleteAll(Class<T> type) {
        return deleteAll(type, null);
    }

    public static <T> int deleteAll(Class<T> type, String whereClause, String... whereArgs) {
        SugarDb db = getSugarContext().getSugarDb();
        SQLiteDatabase sqLiteDatabase = db.getDB();
        return sqLiteDatabase.delete(NamingHelper.toSQLName(type), whereClause, whereArgs);
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
                save(object);
            }
            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            Log.i("Sugar", "Error in saving in transaction " + e.getMessage());
        } finally {
            sqLiteDatabase.endTransaction();
            sqLiteDatabase.setLockingEnabled(true);
        }
    }

    @SuppressWarnings("deprecation")
    public static <T> int deleteInTx(T... objects) {
        return deleteInTx(Arrays.asList(objects));
    }

    @SuppressWarnings("deprecation")
    public static <T> int deleteInTx(Collection<T> objects) {
        SQLiteDatabase sqLiteDatabase = getSugarContext().getSugarDb().getDB();
        int deletedRows = 0;
        try {
            sqLiteDatabase.beginTransaction();
            sqLiteDatabase.setLockingEnabled(false);
            for (T object : objects) {
                if (delete(object)) {
                    ++deletedRows;
                }
            }
            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            deletedRows = 0;
            Log.i("Sugar", "Error in deleting in transaction " + e.getMessage());
        } finally {
            sqLiteDatabase.endTransaction();
            sqLiteDatabase.setLockingEnabled(true);
        }
        return deletedRows;
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

    public static <T> List<T> findById(Class<T> type, String[] ids) {
        String whereClause = "id IN (" + QueryBuilder.generatePlaceholders(ids.length) + ")";
        return find(type, whereClause, ids);
    }

    public static <T> T first(Class<T>type) {
        List<T> list = findWithQuery(type,
                "SELECT * FROM " + NamingHelper.toSQLName(type) + " ORDER BY ID ASC LIMIT 1");
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public static <T> T last(Class<T>type) {
        List<T> list = findWithQuery(type,
                "SELECT * FROM " + NamingHelper.toSQLName(type) + " ORDER BY ID DESC LIMIT 1");
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
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
                inflate(c, entity, getSugarContext().getEntitiesMap());
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
                inflate(c, entity, getSugarContext().getEntitiesMap());
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

    static void saveJoinTable(SQLiteDatabase db, List<ContentValues> relationshipList, String joinTableName) {

        for(ContentValues values: relationshipList) {

            //If record already exists then ignore
            long id = db.insertWithOnConflict(joinTableName, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE);

            Log.i("Sugar", "Inserted Join table record for " + joinTableName + ".");

        }
    }

    static long save(SQLiteDatabase db, Object object) {
        Map<Object, Long> entitiesMap = getSugarContext().getEntitiesMap();
        List<Field> columns = ReflectionUtil.getTableFields(object.getClass());
        ContentValues values = new ContentValues(columns.size());
        Field idField = null;
        for (Field column : columns) {
            List<ContentValues> relationshipList = ReflectionUtil.addFieldValueToColumn(values, column, object, entitiesMap);
            if (column.getName().equals("id")) {
                idField = column;
            }

            if(relationshipList != null && !relationshipList.isEmpty()) {
                saveJoinTable(db, relationshipList, column.getAnnotation(Relationship.class).joinTable());
            }
        }

        boolean isSugarEntity = isSugarEntity(object.getClass());
        if (isSugarEntity && entitiesMap.containsKey(object)) {
                values.put("id", entitiesMap.get(object));
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
            } else {
                entitiesMap.put(object, id);
            }
        } else if (SugarRecord.class.isAssignableFrom(object.getClass())) {
            ((SugarRecord) object).setId(id);
        }

        Log.i("Sugar", object.getClass().getSimpleName() + " saved : " + id);

        return id;
    }

    public static boolean isSugarEntity(Class<?> objectClass) {
        return objectClass.isAnnotationPresent(Table.class) || SugarRecord.class.isAssignableFrom(objectClass);
    }

    private static void inflate(Cursor cursor, Object object, Map<Object, Long> entitiesMap) {
        List<Field> columns = ReflectionUtil.getTableFields(object.getClass());
        if (!entitiesMap.containsKey(object)) {
            entitiesMap.put(object, cursor.getLong(cursor.getColumnIndex(("ID"))));
        }

        for (Field field : columns) {
        	field.setAccessible(true);
            Class<?> fieldType = field.getType();
            if (isSugarEntity(fieldType)) {
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

    public boolean delete() {
        Long id = getId();
        Class<?> type = getClass();
        if (id != null && id > 0L) {
            SQLiteDatabase db = getSugarContext().getSugarDb().getDB();
            Log.i("Sugar", type.getSimpleName() + " deleted : " + id);
            return db.delete(NamingHelper.toSQLName(type), "Id=?", new String[]{id.toString()}) == 1;
        } else {
            Log.i("Sugar", "Cannot delete object: " + type.getSimpleName() + " - object has not been saved");
            return false;
        }
    }
    
    public static boolean delete(Object object) {
        Class<?> type = object.getClass();
        if (type.isAnnotationPresent(Table.class)) {
            try {
                Field field = type.getDeclaredField("id");
                field.setAccessible(true);
                Long id = (Long) field.get(object);
                if (id != null && id > 0L) {
                    SQLiteDatabase db = getSugarContext().getSugarDb().getDB();
                    boolean deleted = db.delete(NamingHelper.toSQLName(type), "Id=?", new String[]{id.toString()}) == 1;
                    Log.i("Sugar", type.getSimpleName() + " deleted : " + id);
                    return deleted;
                } else {
                    Log.i("Sugar", "Cannot delete object: " + object.getClass().getSimpleName() + " - object has not been saved");
                    return false;
                }
            } catch (NoSuchFieldException e) {
                Log.i("Sugar", "Cannot delete object: " + object.getClass().getSimpleName() + " - annotated object has no id");
                return false;
            } catch (IllegalAccessException e) {
                Log.i("Sugar", "Cannot delete object: " + object.getClass().getSimpleName() + " - can't access id");
                return false;
            }
        } else if (SugarRecord.class.isAssignableFrom(type)) {
            return ((SugarRecord) object).delete();
        } else {
            Log.i("Sugar", "Cannot delete object: " + object.getClass().getSimpleName() + " - not persisted");
            return false;
        }
    }

    public long save() {
        return save(getSugarContext().getSugarDb().getDB(), this);
    }

    @SuppressWarnings("unchecked")
    void inflate(Cursor cursor) {
        inflate(cursor, this, getSugarContext().getEntitiesMap());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static class CursorIterator<E> implements Iterator<E> {
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
                inflate(cursor, entity, getSugarContext().getEntitiesMap());
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

        public E getItemAtPosition(int position) {
            if(cursor.moveToPosition(position)) {
                return this.next();
            } else {
                return null;
            }
        }
        
        public Cursor getCursor() {
            return cursor;
        }
    }
    
}
