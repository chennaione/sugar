package com.orm;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.orm.dsl.Column;
import com.orm.dsl.MultiUnique;
import com.orm.dsl.NotNull;
import com.orm.dsl.Unique;
import com.orm.util.KeyWordUtil;
import com.orm.util.MigrationFileParser;
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
import static com.orm.util.ContextUtil.getAssets;

public class SchemaGenerator {
    public static final String NULL = " NULL";
    public static final String NOT_NULL = " NOT NULL";
    public static final String UNIQUE = " UNIQUE";
    public static final String SUGAR = "Sugar";

    //Prevent instantiation
    private SchemaGenerator() { }

    public static SchemaGenerator getInstance() {
        return new SchemaGenerator();
    }

    public void createDatabase(SQLiteDatabase sqLiteDatabase) {
        List<Class> domainClasses = getDomainClasses();
        for (Class domain : domainClasses) {
            createTable(domain, sqLiteDatabase);
        }
    }

    public void doUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        List<Class> domainClasses = getDomainClasses();
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
        List<Class> tables = getDomainClasses();
        for (Class table : tables) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NamingHelper.toSQLName(table));
        }
    }

    private boolean executeSugarUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        boolean isSuccess = false;

        try {
            List<String> files = Arrays.asList(getAssets().list("sugar_upgrades"));
            Collections.sort(files, new NumberComparator());
            for (String file : files) {
                Log.i(SUGAR, "filename : " + file);

                try {
                    int version = Integer.valueOf(file.replace(".sql", ""));

                    if ((version > oldVersion) && (version <= newVersion)) {
                        executeScript(db, file);
                        isSuccess = true;
                    }
                } catch (NumberFormatException e) {
                    Log.i(SUGAR, "not a sugar script. ignored." + file);
                }

            }
        } catch (IOException e) {
            Log.e(SUGAR, e.getMessage());
        }

        return isSuccess;
    }

    private void executeScript(SQLiteDatabase db, String file) {
        try {
            InputStream is = getAssets().open("sugar_upgrades/" + file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            MigrationFileParser migrationFileParser = new MigrationFileParser(sb.toString());
            for(String statement: migrationFileParser.getStatements()){
                Log.i("Sugar script", statement);
                if (!statement.isEmpty()) {
                    db.execSQL(statement);
                }
            }

        } catch (IOException e) {
            Log.e(SUGAR, e.getMessage());
        }

        Log.i(SUGAR, "Script executed");
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

                // Unique is not working on ALTER TABLE
//                if (column.isAnnotationPresent(Unique.class)) {
//                    sb.append(" UNIQUE");
//                }
                alterCommands.add(sb.toString());
            }
        }

        for (String command : alterCommands) {
            Log.i("Sugar", command);
            sqLiteDatabase.execSQL(command);
        }
    }

    protected String createTableSQL(Class<?> table) {
        Log.i(SUGAR, "Create table if not exists");
        List<Field> fields = ReflectionUtil.getTableFields(table);
        String tableName = NamingHelper.toSQLName(table);

        if(KeyWordUtil.isKeyword(tableName)) {
            Log.i(SUGAR,"ERROR, SQLITE RESERVED WORD USED IN " + tableName);
        }

        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        sb.append(tableName).append(" ( ID INTEGER PRIMARY KEY AUTOINCREMENT ");

        for (Field column : fields) {
            String columnName = NamingHelper.toSQLName(column);
            String columnType = QueryBuilder.getColumnType(column.getType());

            if (columnType == null || columnName.equalsIgnoreCase("Id")) {
                    continue;
            }

            if (column.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = column.getAnnotation(Column.class);
                columnName = columnAnnotation.name();

                sb.append(", ").append(columnName).append(" ").append(columnType);

                if (columnAnnotation.notNull()) {
                    if (columnType.endsWith(NULL)) {
                        sb.delete(sb.length() - 5, sb.length());
                    }
                    sb.append(NOT_NULL);
                }

                if (columnAnnotation.unique()) {
                    sb.append(UNIQUE);
                }

            } else {
                sb.append(", ").append(columnName).append(" ").append(columnType);

                if (column.isAnnotationPresent(NotNull.class)) {
                    if (columnType.endsWith(NULL)) {
                        sb.delete(sb.length() - 5, sb.length());
                    }
                    sb.append(NOT_NULL);
                }

                if (column.isAnnotationPresent(Unique.class)) {
                    sb.append(UNIQUE);
                }
            }
        }

        if (table.isAnnotationPresent(MultiUnique.class)) {
            String constraint = table.getAnnotation(MultiUnique.class).value();

            sb.append(", UNIQUE(");

            String[] constraintFields = constraint.split(",");
            for(int i = 0; i < constraintFields.length; i++) {
                String columnName = NamingHelper.toSQLNameDefault(constraintFields[i]);
                sb.append(columnName);

                if(i < (constraintFields.length -1)) {
                    sb.append(",");
                }
            }

            sb.append(") ON CONFLICT REPLACE");
        }

        sb.append(" ) ");
        Log.i(SUGAR, "Creating table " + tableName);

        return sb.toString();
    }

    private void createTable(Class<?> table, SQLiteDatabase sqLiteDatabase) {
        String createSQL = createTableSQL(table);

        if (!createSQL.isEmpty()) {
            try {
                sqLiteDatabase.execSQL(createSQL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
