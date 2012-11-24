package com.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import com.orm.dsl.Ignore;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.orm.SugarApp.getSugarContext;

public class SugarRecord<T> {

    private Context context;
    protected Long id = null;
    private SugarApp application;
    private Database database;
    String tableName = getSqlName();

    public SugarRecord(Context context) {
        this.context = context;
        this.application = (SugarApp) context.getApplicationContext();
        this.database = application.database;
    }

    public void delete() {
        SQLiteDatabase db = this.database.openDB();
        db.delete(this.tableName, "Id=?", new String[]{getId().toString()});
        this.database.closeDB();

    }

    public static <T extends SugarRecord> void deleteAll(Class<T> type) {
        Database db = getSugarContext().database;
        SQLiteDatabase sqLiteDatabase = db.openDB();
        sqLiteDatabase.delete(getTableName(type), null, null);
    }

    public void save() {
        SQLiteDatabase sqLiteDatabase = database.openDB();
        List<Field> columns = getTableFields();
        ContentValues values = new ContentValues(columns.size());
        for (Field column : columns) {
            column.setAccessible(true);
            try {
                if (column.getType().getSuperclass() == SugarRecord.class) {
                    values.put(StringUtil.toSQLName(column.getName()),
                            (column.get(this) != null)
                                    ? String.valueOf(((SugarRecord) column.get(this)).id)
                                    : "0");
                } else {
                    if (!"id".equalsIgnoreCase(column.getName())) {
                        values.put(StringUtil.toSQLName(column.getName()),
                                String.valueOf(column.get(this)));
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
        database.closeDB();
    }

    public static <T extends SugarRecord> List<T> listAll(Class<T> type) {
        return find(type, null, null, null, null, null);
    }

    public static <T extends SugarRecord> T findById(Class<T> type, Long id) {
        List<T> list = find( type, "id=?", new String[]{String.valueOf(id)}, null, null, "1");
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    public static <T extends SugarRecord> List<T> find(Class<T> type,
                                                       String whereClause, String... whereArgs) {
        return find(type, whereClause, whereArgs, null, null, null);
    }

    public static <T extends SugarRecord> List<T> findWithQuery(Class<T> type, String query, String... arguments){

        Database db = getSugarContext().database;
        SQLiteDatabase sqLiteDatabase = db.openDB();
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
        getSugarContext().database.openDB().execSQL(query, arguments);
    }

    public static <T extends SugarRecord> List<T> find(Class<T> type,
                                                       String whereClause, String[] whereArgs,
                                                       String groupBy, String orderBy, String limit) {
        Database db = getSugarContext().database;
        SQLiteDatabase sqLiteDatabase = db.openDB();
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

    void inflate(Cursor cursor) {
        Map<Field, Long> entities = new HashMap<Field, Long>();
        List<Field> columns = getTableFields();
        for (Field field : columns) {
            field.setAccessible(true);
            try {
                String typeString = field.getType().getName();
                String colName = StringUtil.toSQLName(field.getName());

                if(colName.equalsIgnoreCase("id")){
                    long cid = cursor.getLong(cursor.getColumnIndex(colName));
                    field.set(this, Long.valueOf(cid));
                }else if (typeString.equals("long")) {
                    field.setLong(this,
                            cursor.getLong(cursor.getColumnIndex(colName)));
                } else if (typeString.equals("java.lang.String")) {
                    String val = cursor.getString(cursor
                            .getColumnIndex(colName));
                    field.set(this, val.equals("null") ? null : val);
                } else if (typeString.equals("double")) {
                    field.setDouble(this,
                            cursor.getDouble(cursor.getColumnIndex(colName)));
                } else if (typeString.equals("boolean")) {
                    field.setBoolean(this,
                            cursor.getString(cursor.getColumnIndex(colName))
                                    .equals("true"));
                } else if (typeString.equals("[B")) {
                    field.set(this,
                            cursor.getBlob(cursor.getColumnIndex(colName)));
                } else if (typeString.equals("int")) {
                    field.setInt(this,
                            cursor.getInt(cursor.getColumnIndex(colName)));
                } else if (typeString.equals("float")) {
                    field.setFloat(this,
                            cursor.getFloat(cursor.getColumnIndex(colName)));
                } else if (typeString.equals("short")) {
                    field.setShort(this,
                            cursor.getShort(cursor.getColumnIndex(colName)));
                } else if (typeString.equals("java.sql.Timestamp")) {
                    long l = cursor.getLong(cursor.getColumnIndex(colName));
                    field.set(this, new Timestamp(l));
                } else if (field.getType().getSuperclass() == SugarRecord.class) {
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
                f.set(this, findById((Class<? extends SugarRecord>) f.getType(),
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
        try {
            typeFields.add(getClass().getSuperclass().getDeclaredField("id"));
        } catch (SecurityException e) {
            Log.e("Sugar", e.getMessage());
        } catch (NoSuchFieldException e) {
            Log.e("Sugar", e.getMessage());
        }

        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Ignore.class)) {
                typeFields.add(field);
            }
        }

        SugarConfig.setFields(getClass(), typeFields);
        return typeFields;
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

}
