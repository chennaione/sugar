package com.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import com.orm.dsl.Ignore;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.*;

import static com.orm.SugarApp.getSugarContext;

public class SugarRecord<T> {

    @Ignore
    private Context context;
    @Ignore
    private SugarApp application;
    @Ignore
    private Database database;
    @Ignore
    String tableName = getSqlName();

    protected Long id = null;

    public SugarRecord(Context context) {
        this.context = context;
        // this.application = (SugarApp) context.getApplicationContext();
        this.database = ((SugarApp) context.getApplicationContext()).getDatabase();
    }

    public SugarRecord(){
        this.context = SugarApp.getSugarContext();
        this.database = SugarApp.getSugarContext().getDatabase();
    }

    public void delete() {
        SQLiteDatabase db = getSugarContext().getDatabase().getDB();
        db.delete(this.tableName, "Id=?", new String[]{getId().toString()});
    }

    public static <T extends SugarRecord<?>> void deleteAll(Class<T> type) {
        Database db = getSugarContext().getDatabase();
        SQLiteDatabase sqLiteDatabase = db.getDB();
        sqLiteDatabase.delete(getTableName(type), null, null);
    }

    public static <T extends SugarRecord<?>> void deleteAll(Class<T> type, String whereClause, String... whereArgs ) {
        Database db = getSugarContext().getDatabase();
        SQLiteDatabase sqLiteDatabase = db.getDB();
        sqLiteDatabase.delete(getTableName(type), whereClause, whereArgs);
    }

    public void save() {
        SQLiteDatabase sqLiteDatabase = getSugarContext().getDatabase().getDB();
        List<Field> columns = getTableFields();
        ContentValues values = new ContentValues(columns.size());
        for (Field column : columns) {
            column.setAccessible(true);
            Class<?> columnType = column.getType();
            try {
                String columnName = StringUtil.toSQLName(column.getName());
                Object columnValue = column.get(this);
                if (SugarRecord.class.isAssignableFrom(columnType)) {
                    values.put(columnName,
                            (columnValue != null)
                                    ? String.valueOf(((SugarRecord) columnValue).id)
                                    : "0");
                } else {
                    if (!"id".equalsIgnoreCase(column.getName())) {
                        if (columnType.equals(Short.class) || columnType.equals(short.class)) {
                            values.put(columnName, (Short) columnValue);
                        }
                        else if (columnType.equals(Integer.class) || columnType.equals(int.class)) {
                            values.put(columnName, (Integer) columnValue);
                        }
                        else if (columnType.equals(Long.class) || columnType.equals(long.class)) {
                            values.put(columnName, (Long) columnValue);
                        }
                        else if (columnType.equals(Float.class) || columnType.equals(float.class)) {
                            values.put(columnName, (Float) columnValue);
                        }
                        else if (columnType.equals(Double.class) || columnType.equals(double.class)) {
                            values.put(columnName, (Double) columnValue);
                        }
                        else if (columnType.equals(Boolean.class) || columnType.equals(boolean.class)) {
                            values.put(columnName, (Boolean) columnValue);
                        }
                        else{
                            values.put(columnName, String.valueOf(columnValue));
                        }

                    }
                }

            } catch (IllegalAccessException e) {
                Log.e("Sugar", e.getMessage());
            }
        }

        if (id == null)
                id = sqLiteDatabase.insert(getSqlName(), null, values);
        else
                sqLiteDatabase.update(getSqlName(), values, "ID = ?", new String[]{String.valueOf(id)});

        Log.i("Sugar", getClass().getSimpleName() + " saved : " + id);
    }

    @SuppressWarnings("deprecation")
    public static <T extends SugarRecord<?>> void saveInTx(T... objects ) {

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

    void save(SQLiteDatabase db) {

        List<Field> columns = getTableFields();
        ContentValues values = new ContentValues(columns.size());
        for (Field column : columns) {
            column.setAccessible(true);
            try {
                if (SugarRecord.class.isAssignableFrom(column.getType())) {
                    values.put(StringUtil.toSQLName(column.getName()),
                            (column.get(this) != null)
                                    ? String.valueOf(((SugarRecord) column.get(this)).id)
                                    : "0");
                } else {
                    if (!"id".equalsIgnoreCase(column.getName())) {
                        if (Date.class.equals(column.getType())) {
                            values.put(StringUtil.toSQLName(column.getName()), ((Date) column.get(this)).getTime());
                        }
                        else if (Calendar.class.equals(column.getType())) {
                            values.put(StringUtil.toSQLName(column.getName()), ((Calendar) column.get(this)).getTimeInMillis());
                        }
                        else {
                            values.put(StringUtil.toSQLName(column.getName()), String.valueOf(column.get(this)));
                        }
                    }
                }

            } catch (IllegalAccessException e) {
                Log.e("Sugar", e.getMessage());
            }
        }

        if (id == null)
            id = db.insert(getSqlName(), null, values);
        else
            db.update(getSqlName(), values, "ID = ?", new String[]{String.valueOf(id)});

        Log.i("Sugar", getClass().getSimpleName() + " saved : " + id);
    }

    public static <T extends SugarRecord<?>> List<T> listAll(Class<T> type) {
        return find(type, null, null, null, null, null);
    }

    public static <T extends SugarRecord<?>> T findById(Class<T> type, Long id) {
        List<T> list = find( type, "id=?", new String[]{String.valueOf(id)}, null, null, "1");
        if (list.isEmpty()) return null;
        return list.get(0);
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
                entity = type.getDeclaredConstructor(Context.class).newInstance(getSugarContext());
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
        Cursor c = sqLiteDatabase.query(getTableName(type), null,
                whereClause, whereArgs, groupBy, null, orderBy, limit);
        try {
            while (c.moveToNext()) {
                entity = type.getDeclaredConstructor(Context.class).newInstance(getSugarContext());
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

    @SuppressWarnings("unchecked")
    void inflate(Cursor cursor) {
        Map<Field, Long> entities = new HashMap<Field, Long>();
        List<Field> columns = getTableFields();
        for (Field field : columns) {
            field.setAccessible(true);
            try {
                Class fieldType = field.getType();
                String colName = StringUtil.toSQLName(field.getName());

                if(colName.equalsIgnoreCase("id")){
                    long cid = cursor.getLong(cursor.getColumnIndex(colName));
                    field.set(this, Long.valueOf(cid));
                }else if (fieldType.equals(long.class) || fieldType.equals(Long.class)) {
                    field.setLong(this,
                            cursor.getLong(cursor.getColumnIndex(colName)));
                } else if (fieldType.equals(String.class)) {
                    String val = cursor.getString(cursor
                            .getColumnIndex(colName));
                    field.set(this, val != null && val.equals("null") ? null : val);
                } else if (fieldType.equals(double.class) || fieldType.equals(Double.class)) {
                    field.setDouble(this,
                            cursor.getDouble(cursor.getColumnIndex(colName)));
                } else if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
                    field.setBoolean(this,
                            cursor.getString(cursor.getColumnIndex(colName))
                                    .equals("1"));
                } else if (field.getType().getName().equals("[B")) {
                    field.set(this,
                            cursor.getBlob(cursor.getColumnIndex(colName)));
                } else if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
                    field.setInt(this,
                            cursor.getInt(cursor.getColumnIndex(colName)));
                } else if (fieldType.equals(float.class) || fieldType.equals(Float.class)) {
                    field.setFloat(this,
                            cursor.getFloat(cursor.getColumnIndex(colName)));
                } else if (fieldType.equals(short.class) || fieldType.equals(Short.class)) {
                    field.setShort(this,
                            cursor.getShort(cursor.getColumnIndex(colName)));
                } else if (fieldType.equals(Timestamp.class)) {
                    long l = cursor.getLong(cursor.getColumnIndex(colName));
                    field.set(this, new Timestamp(l));
                } else if (fieldType.equals(Date.class)) {
                    long l = cursor.getLong(cursor.getColumnIndex(colName));
                    field.set(this, new Date(l));
                } else if (fieldType.equals(Calendar.class)) {
                    long l = cursor.getLong(cursor.getColumnIndex(colName));
                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(l);
                    field.set(this, c);
                } else if (SugarRecord.class.isAssignableFrom(field.getType())) {
                    long id = cursor.getLong(cursor.getColumnIndex(colName));
                    if (id > 0)
                        entities.put(field, id);
                    else
                        field.set(this, null);
                } else
                    Log.e("Sugar", "Class cannot be read from Sqlite3 database.");
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

    public List<Field> getTableFields() {
        List<Field> fieldList = SugarConfig.getFields(getClass());
        if(fieldList != null) return fieldList;

        Log.d("Sugar", "Fetching properties");
        List<Field> typeFields = new ArrayList<Field>();

        getAllFields(typeFields, getClass());

        List<Field> toStore = new ArrayList<Field>();
        for (Field field : typeFields) {
            if (!field.isAnnotationPresent(Ignore.class)) {
                toStore.add(field);
            }
        }

        SugarConfig.setFields(getClass(), toStore);
        return toStore;
    }

    private static List<Field> getAllFields(List<Field> fields, Class<?> type) {
        Collections.addAll(fields, type.getDeclaredFields());

        if (type.getSuperclass() != null) {
            fields = getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

    public String getSqlName() {
        return getTableName(getClass());
    }


    public static String getTableName(Class<?> type) {
        return StringUtil.toSQLName(type.getSimpleName());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
