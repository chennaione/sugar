package com.orm;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import dalvik.system.DexFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

import static com.orm.SugarConfig.getDatabaseVersion;
import static com.orm.SugarConfig.getDebugEnabled;

public class SugarDb extends SQLiteOpenHelper {
    private Context context;

    public SugarDb(Context context) {
        super(context, SugarConfig.getDatabaseName(context), new SugarCursorFactory(getDebugEnabled(context)), getDatabaseVersion(context));
        this.context = context;

    }

    private <T extends SugarRecord> List<T> getDomainClasses(Context context) {
        List<T> domainClasses = new ArrayList<T>();
        try {
            Enumeration allClasses = getAllClasses(context);

            while (allClasses.hasMoreElements()) {
                String className = (String) allClasses.nextElement();

                if (className.startsWith(SugarConfig.getDomainPackageName(context))) {
                    T domainClass = getDomainClass(className, context);
                    if (domainClass != null) domainClasses.add(domainClass);
                }
            }

        } catch (IOException e) {
            Log.e("Sugar", e.getMessage());
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Sugar", e.getMessage());
        }

        return domainClasses;
    }

    private <T extends SugarRecord> T getDomainClass(String className, Context context) {
        Log.i("Sugar", "domain class");
        Class discoveredClass = null;
        try {
            discoveredClass = Class.forName(className, true, context.getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            Log.e("Sugar", e.getMessage());
        }

        if ((discoveredClass == null) ||
                (!SugarRecord.class.isAssignableFrom(discoveredClass)) ||
                Modifier.isAbstract(discoveredClass.getModifiers())) {
            return null;
        } else {
            try {
                return (T) discoveredClass.getDeclaredConstructor(Context.class).newInstance(context);
            } catch (InstantiationException e) {
                Log.e("Sugar", e.getMessage());
            } catch (IllegalAccessException e) {
                Log.e("Sugar", e.getMessage());
            } catch (NoSuchMethodException e) {
                Log.e("Sugar", e.getMessage());
            } catch (InvocationTargetException e) {
                Log.e("Sugar", e.getMessage());
            }
        }

        return null;

    }

    private Enumeration getAllClasses(Context context) throws PackageManager.NameNotFoundException, IOException {
        String path = getSourcePath(context);
        DexFile dexfile = new DexFile(path);
        return dexfile.entries();
    }

    private String getSourcePath(Context context) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("Sugar", "on create");
        createDatabase(sqLiteDatabase);
    }

    private <T extends SugarRecord> void createDatabase(SQLiteDatabase sqLiteDatabase) {
        List<T> domainClasses = getDomainClasses(context);
        for (T domain : domainClasses) {
            createTable(domain, sqLiteDatabase);
        }
    }

    private <T extends SugarRecord> void createTable(T table, SQLiteDatabase sqLiteDatabase) {
        Log.i("Sugar", "create table");
        List<Field> fields = table.getTableFields();
        StringBuilder sb = new StringBuilder("CREATE TABLE ").append(table.getSqlName()).append(
                " ( ID INTEGER PRIMARY KEY AUTOINCREMENT ");

        for (Field column : fields) {
            String columnName = StringUtil.toSQLName(column.getName());
            String columnType = QueryBuilder.getColumnType(column.getType());

            if (columnType != null) {

                if (columnName.equalsIgnoreCase("Id")) {
                    continue;
                }
                sb.append(", ").append(columnName).append(" ").append(columnType);
            }
        }
        sb.append(" ) ");

        Log.i("Sugar", "creating table " + table.getSqlName());

        if (!"".equals(sb.toString()))
            sqLiteDatabase.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.i("Sugar", "upgrading sugar");
        if (!executeSugarUpgrade(sqLiteDatabase, oldVersion, newVersion)) {
            deleteTables(sqLiteDatabase);
            onCreate(sqLiteDatabase);
        }
    }

    private <T extends SugarRecord> void deleteTables(SQLiteDatabase sqLiteDatabase) {
        List<T> tables = getDomainClasses(this.context);
        for (T table : tables) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + table.getSqlName());
        }
    }

    private boolean executeSugarUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        boolean isSuccess = false;
        try {
            List<String> files = Arrays.asList(this.context.getAssets().list("sugar_upgrades"));
            Collections.sort(files, new NumberComparator());

            for (String file : files){
                Log.i("Sugar", "filename : " + file);
                try {
                    int version = Integer.valueOf(file.replace(".sql", ""));

                    if ((version > oldVersion) && (version <= newVersion)) {
                        executeScript(db, file);
                        isSuccess = true;
                    }
                } catch (NumberFormatException e) {
                    Log.i("Sugar", "not a sugar script. ignored." + file);
                }
            }
        } catch (IOException e) {
            Log.e("Sugar", e.getMessage());
        }

        return isSuccess;
    }

    private void executeScript(SQLiteDatabase db, String file) {
        StringBuilder text = new StringBuilder();
        try {
            InputStream is = this.context.getAssets().open("sugar_upgrades/" + file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line);
                text.append("\n");
            }
        } catch (IOException e) {
            Log.e("Sugar", e.getMessage());
        }

        Log.i("Sugar", "script : " + text.toString());
        db.execSQL(text.toString());
    }
}
