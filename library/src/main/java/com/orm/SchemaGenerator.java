package com.orm;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.orm.dsl.Column;
import com.orm.dsl.NotNull;
import com.orm.dsl.Unique;
import com.orm.util.NamingHelper;
import com.orm.util.NumberComparator;
import com.orm.util.QueryBuilder;
import com.orm.util.ReflectionUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.orm.util.ReflectionUtil.getDomainClasses;

public class SchemaGenerator {

    private Context context;

    public SchemaGenerator(Context context) {
        this.context = context;
    }

    public void createDatabase(SQLiteDatabase sqLiteDatabase) {
        List<Class> domainClasses = getDomainClasses(context);
        for (Class domain : domainClasses) {
            createTable(domain, sqLiteDatabase);
        }
    }

    public void doUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        List<Class> domainClasses = getDomainClasses(context);
        String sql = "select count(*) from sqlite_master where type='table' and name='%s';";

        for (Class domain : domainClasses) {
            String tableName = NamingHelper.toSQLName(domain);
            Cursor c = sqLiteDatabase.rawQuery(String.format(sql, tableName), null);
            if (c.moveToFirst() && c.getInt(0) == 0) {
                createTable(domain, sqLiteDatabase);
            } else {
                addColumns(domain, sqLiteDatabase);
            }
        }
        executeSugarUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }

    private ArrayList<String> getColumnNames(SQLiteDatabase sqLiteDatabase, String tableName) {
        Cursor resultsQuery = sqLiteDatabase.query(tableName, null, null, null, null, null, null);
        //Check if columns match vs the one on the domain class
        ArrayList<String> columnNames = new ArrayList<>();
        for (int i = 0; i < resultsQuery.getColumnCount(); i++) {
            String columnName = resultsQuery.getColumnName(i);
            columnNames.add(columnName);
        }
        resultsQuery.close();
        return columnNames;
    }


    public void deleteTables(SQLiteDatabase sqLiteDatabase) {
        List<Class> tables = getDomainClasses(context);
        for (Class table : tables) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NamingHelper.toSQLName(table));
        }
    }

    private boolean executeSugarUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        boolean isSuccess = false;

        try {
            List<String> files = Arrays.asList(this.context.getAssets().list("sugar_upgrades"));
            Collections.sort(files, new NumberComparator());
            for (String file : files) {
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

        Log.i("Sugar", "Script executed");
    }

    private void addColumns(Class<?> table, SQLiteDatabase sqLiteDatabase) {

        List<Field> fields = ReflectionUtil.getTableFields(table);
        String tableName = NamingHelper.toSQLName(table);
        ArrayList<String> presentColumns = getColumnNames(sqLiteDatabase, tableName);
        ArrayList<String> alterCommands = new ArrayList<>();

        for (Field column : fields) {
            String columnName = NamingHelper.toSQLName(column);
            String columnType = QueryBuilder.getColumnType(column.getType());

            if (column.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = column.getAnnotation(Column.class);
                columnName = columnAnnotation.name();
            }

            if (!presentColumns.contains(columnName)) {
                StringBuilder sb = new StringBuilder("ALTER TABLE ");
                sb.append(tableName).append(" ADD COLUMN ").append(columnName).append(" ").append(columnType);
                if (column.isAnnotationPresent(NotNull.class)) {
                    if (columnType.endsWith(" NULL")) {
                        sb.delete(sb.length() - 5, sb.length());
                    }
                    sb.append(" NOT NULL");
                }

                if (column.isAnnotationPresent(Unique.class)) {
                    sb.append(" UNIQUE");
                }
                alterCommands.add(sb.toString());
            }
        }

        for (String command : alterCommands) {
            Log.i("Sugar", command);
            sqLiteDatabase.execSQL(command);
        }
    }

    private void createTable(Class<?> table, SQLiteDatabase sqLiteDatabase) {
        Log.i("Sugar", "Create table");
        List<Field> fields = ReflectionUtil.getTableFields(table);
        String tableName = NamingHelper.toSQLName(table);
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append(tableName).append(" ( ID INTEGER PRIMARY KEY AUTOINCREMENT ");

        for (Field column : fields) {
            String columnName = NamingHelper.toSQLName(column);
            String columnType = QueryBuilder.getColumnType(column.getType());

            if (columnType != null) {
                if (columnName.equalsIgnoreCase("Id")) {
                    continue;
                }

                if (column.isAnnotationPresent(Column.class)) {
                    Column columnAnnotation = column.getAnnotation(Column.class);
                    columnName = columnAnnotation.name();

                    sb.append(", ").append(columnName).append(" ").append(columnType);

                    if (columnAnnotation.notNull()) {
                        if (columnType.endsWith(" NULL")) {
                            sb.delete(sb.length() - 5, sb.length());
                        }
                        sb.append(" NOT NULL");
                    }

                    if (columnAnnotation.unique()) {
                        sb.append(" UNIQUE");
                    }

                } else {
                    sb.append(", ").append(columnName).append(" ").append(columnType);

                    if (column.isAnnotationPresent(NotNull.class)) {
                        if (columnType.endsWith(" NULL")) {
                            sb.delete(sb.length() - 5, sb.length());
                        }
                        sb.append(" NOT NULL");
                    }

                    if (column.isAnnotationPresent(Unique.class)) {
                        sb.append(" UNIQUE");
                    }
                }
            }
        }

        sb.append(" ) ");
        Log.i("Sugar", "Creating table " + tableName);

        if (!"".equals(sb.toString())) {
            try {
                sqLiteDatabase.execSQL(sb.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
