package com.orm;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import dalvik.system.DexFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.List;

import static com.orm.dsl.Collection.list;

public class SugarDb extends SQLiteOpenHelper {
    private Context context;

    public SugarDb(Context context) {
        super(context, "", null, 1);
        this.context = context;
    }

    private static <T extends SugarRecord> List<T> getDomainClasses(Context context) {
        List<T> domainClasses = list();
        try {
            Enumeration allClasses = getAllClasses(context);

            while (allClasses.hasMoreElements()) {
                String className = (String) allClasses.nextElement();
                T domainClass = getDomainClass(className, context);
                if (domainClass != null) domainClasses.add(domainClass);
            }

        } catch (IOException e) {
            Log.e("Sugar", e.getMessage());
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Sugar", e.getMessage());
        }

        return domainClasses;
    }

    private static <T extends SugarRecord> T getDomainClass(String className, Context context) {
        Class discoveredClass = null;
        Class superClass = null;
        try {
            discoveredClass = Class.forName(className, true, context.getClass().getClassLoader());
            superClass = discoveredClass.getSuperclass();
        } catch (ClassNotFoundException e) {
            Log.e("Sugar", e.getMessage());
        }

        if ((discoveredClass == null) || (superClass == null) ||
                (!discoveredClass.getSuperclass().equals(SugarRecord.class))) {
            return null;
        } else {
            try {
                return (T) discoveredClass.newInstance();
            } catch (InstantiationException e) {
                Log.e("Sugar", e.getMessage());
            } catch (IllegalAccessException e) {
                Log.e("Sugar", e.getMessage());
            }
        }

        return null;

    }

    private static Enumeration getAllClasses(Context context) throws PackageManager.NameNotFoundException, IOException {
        String path = getSourcePath(context);
        DexFile dexfile = new DexFile(path);
        return dexfile.entries();
    }

    private static String getSourcePath(Context context) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createDatabase(sqLiteDatabase);
    }

    private <T extends SugarRecord> void createDatabase(SQLiteDatabase sqLiteDatabase) {
        List<T> domainClasses = getDomainClasses(context);
        for (T domain : domainClasses) {
            createTable(domain, sqLiteDatabase);
        }
    }

    private <T extends SugarRecord> void createTable(T table, SQLiteDatabase sqLiteDatabase) {
        List<Field> fields = table.getTableFields();
        List definitions = list();
        StringBuilder sb = new StringBuilder("CREATE TABLE ").append(table.getSqlName()).append(
                " (_id integer primary key");

        for (Field column : fields) {
            String columnName = StringUtil.toSQLName(column.getName());
            String columnType = QueryBuilder.getColumnType(column.getType());
            String definition = null;

            if (columnType != null) definition = columnName + columnType;

            if (definition != null) {

                if (columnName.equals("Id")) {
                    definition = definition + " PRIMARY KEY AUTOINCREMENT";
                }
                sb.append(", ").append(columnName).append(" ").append(columnType);

                definitions.add(definition);
            }
        }

        Log.i("Sugar", "creating table" + table.getSqlName());

        if (!"".equals(sb.toString()))
            sqLiteDatabase.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        deleteTables(sqLiteDatabase);
        onCreate(sqLiteDatabase);
    }

    private <T extends SugarRecord> void deleteTables(SQLiteDatabase sqLiteDatabase) {
        List<T> tables = getDomainClasses(this.context);
    for (T table : tables) {
      sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + table.getSqlName());
    }
    }
}
