package com.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.orm.dsl.Ignore;

import java.lang.reflect.Field;
import java.util.List;

import static com.orm.dsl.Collection.list;

public class SugarRecord<T> {

    private Context context;
  private Long id = null;
  private SugarApp application;
  private Database database;
    String tableName = getSqlName();

    public SugarRecord(Context context) {
        this.context = context;
        this.application = (SugarApp) context.getApplicationContext();
        this.database = application.database;
    }



  public void delete()
  {
    SQLiteDatabase db = this.database.openDB();
    db.delete(this.tableName, "Id=?", new String[] { getId().toString() });
    this.database.closeDB();

  }

    public void save(){
        List<Field> columns = getTableFields();
        ContentValues values = new ContentValues(columns.size());
        for (Field column : columns) {
                try {
                        if (column.getType().getSuperclass() == SugarRecord.class)
                                values.put(StringUtil.toSQLName(column.getName()),
                                                (column.get(this) != null)
                                                        ? String.valueOf(((SugarRecord) column.get(this)).id)
                                                        : "0");
                        else
                                values.put(StringUtil.toSQLName(column.getName()),
                                                String.valueOf(column.get(this)));
                } catch (IllegalAccessException e) {
                        Log.e("Sugar", e.getMessage());
                }
        }
        SQLiteDatabase sqLiteDatabase = database.openDB();
        id = (id == null)
                ? sqLiteDatabase.insert(getSqlName(), null, values)
                : sqLiteDatabase.update(getSqlName(), values, "ID = ?", new String[]{String.valueOf(id)});

        database.closeDB();
    }

    public static <T extends SugarRecord> T findById(Context context, Class<T> type, Long id){
        T entity = null;
        try {
            entity = type.newInstance();
        } catch (InstantiationException e) {
            Log.e("Sugar", e.getMessage());
        } catch (IllegalAccessException e) {
            Log.e("Sugar", e.getMessage());
        }

        return null;
    }

    public List<Field> getTableFields() {
        List<Field> typeFields = list();
        try
        {
          typeFields.add(getClass().getSuperclass().getDeclaredField("mId"));
        }
        catch (SecurityException e) {
          Log.e("Sugar", e.getMessage());
        }
        catch (NoSuchFieldException e) {
          Log.e("Sugar", e.getMessage());
        }

        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
          if (!field.isAnnotationPresent(Ignore.class)) {
            typeFields.add(field);
          }
        }

        return typeFields;
      }

    public String getSqlName(){
        return StringUtil.toSQLName(getClass().getSimpleName());
    }

    public Long getId() {
        return id;
    }
}
