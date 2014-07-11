package com.orm;

import static com.orm.SugarConfig.getDatabaseVersion;
import static com.orm.SugarConfig.getDebugEnabled;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.orm.dsl.NotNull;
import com.orm.dsl.Unique;

import dalvik.system.DexFile;

public class SugarDb extends SQLiteOpenHelper {
    private Context context;

    public SugarDb(Context context) {
        super(context, SugarConfig.getDatabaseName(context), new SugarCursorFactory(getDebugEnabled(context)), getDatabaseVersion(context));
        this.context = context;

    }

    private <T extends SugarRecord<?>> List<T> getDomainClasses(Context context) {
        List<T> domainClasses = new ArrayList<T>();
        try {
            for (String className : getAllClasses(context)) {
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

    @SuppressWarnings("unchecked")
    private <T extends SugarRecord<?>> T getDomainClass(String className, Context context) {
        Log.i("Sugar", "domain class");
        Class<?> discoveredClass = null;
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
                return (T) discoveredClass.getDeclaredConstructor().newInstance();
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

    private List<String> getAllClasses(Context context) throws PackageManager.NameNotFoundException, IOException {
        String path = getSourcePath(context);
        List<String> classNames = new ArrayList<String>();
        try {
            DexFile dexfile = new DexFile(path);
            Enumeration<String> dexEntries = dexfile.entries();
            while (dexEntries.hasMoreElements()) {
                classNames.add(dexEntries.nextElement());
            }
        } catch (NullPointerException e) {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Enumeration<URL> urls = classLoader.getResources("");
            List<String> fileNames = new ArrayList<String>();
            while (urls.hasMoreElements()) {
                String classDirectoryName = urls.nextElement().getFile();
                if (classDirectoryName.contains("bin") || classDirectoryName.contains("classes")) {
                    File classDirectory = new File(classDirectoryName);
                    for (File filePath : classDirectory.listFiles()) {
                        populateFiles(filePath, fileNames, "");
                    }
                    classNames.addAll(fileNames);
                }
            }
        }
        return classNames;
    }
    
    private void populateFiles(File path, List<String> fileNames, String parent) {
        if (path.isDirectory()) {
            for (File newPath : path.listFiles()) {
                if ("".equals(parent)) {
                    populateFiles(newPath, fileNames, path.getName());
                } else {
                    populateFiles(newPath, fileNames, parent + "." + path.getName());
                }
            }
        } else {
            String pathName = path.getName();
            String classSuffix = ".class";
            pathName = pathName.endsWith(classSuffix) ?
                    pathName.substring(0, pathName.length() - classSuffix.length()) : pathName;
            if ("".equals(parent)) {
                fileNames.add(pathName);
            } else {
                fileNames.add(parent + "." + pathName);
            }
        }
    }

    private String getSourcePath(Context context) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("Sugar", "on create");
        createDatabase(sqLiteDatabase);
    }

    private <T extends SugarRecord<?>> void createDatabase(SQLiteDatabase sqLiteDatabase) {
        List<T> domainClasses = getDomainClasses(context);
        for (T domain : domainClasses) {
            createTable(domain, sqLiteDatabase);
        }
    }

    private <T extends SugarRecord<?>> void createTable(T table, SQLiteDatabase sqLiteDatabase) {
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
                if(column.isAnnotationPresent(NotNull.class)) {
                    if (columnType.endsWith(" NULL")) {
                        sb.delete(sb.length() - 5, sb.length());
                    }
                    sb.append(" NOT NULL");
                }
                if(column.isAnnotationPresent(Unique.class)) {
                    sb.append(" UNIQUE");
                }
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
        // check if some tables are to be created
        doUpgrade(sqLiteDatabase);

        if (!executeSugarUpgrade(sqLiteDatabase, oldVersion, newVersion)) {
            deleteTables(sqLiteDatabase);
            onCreate(sqLiteDatabase);
        }
    }

    /**
     * Create the tables that do not exist.
     */
    private <T extends SugarRecord<?>> void doUpgrade(SQLiteDatabase sqLiteDatabase) {
        List<T> domainClasses = getDomainClasses(context);
        for (T domain : domainClasses) {
            try {// we try to do a select, if fails then (?) there isn't the table
                sqLiteDatabase.query(domain.tableName, null, null, null, null, null, null);
            } catch (SQLiteException e) {
                Log.i("Sugar", String.format("creating table on update (error was '%s')", e.getMessage()));
                createTable(domain, sqLiteDatabase);
            }
        }
    }

    private <T extends SugarRecord<?>> void deleteTables(SQLiteDatabase sqLiteDatabase) {
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
        try {
            InputStream is = this.context.getAssets().open("sugar_upgrades/" + file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                Log.i("Sugar script", line);
                db.execSQL(line.toString());
            }
        } catch (IOException e) {
            Log.e("Sugar", e.getMessage());
        }

        Log.i("Sugar", "script executed");
    }
}
