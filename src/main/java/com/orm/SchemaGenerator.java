package com.orm;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.orm.dsl.Column;
import com.orm.dsl.ManyToMany;
import com.orm.dsl.ManyToOne;
import com.orm.dsl.NotNull;
import com.orm.dsl.OneToOne;
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
            Cursor c = sqLiteDatabase.rawQuery(String.format(sql, NamingHelper.toSQLName(domain)), null);
            if (c.moveToFirst() && c.getInt(0) == 0) {
                createTable(domain, sqLiteDatabase);
            }
        }
        executeSugarUpgrade(sqLiteDatabase, oldVersion, newVersion);
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

                if(column.isAnnotationPresent(OneToOne.class)) {
                    OneToOne oneToOne =  column.getAnnotation(OneToOne.class);
                    sb.append(", ").append(oneToOne.name()).append(" INTEGER");
                } else if(column.isAnnotationPresent(ManyToOne.class)) {
                    ManyToOne manyToOne =  column.getAnnotation(ManyToOne.class);
                    sb.append(", ").append(manyToOne.name()).append(" INTEGER");
                } else if(column.isAnnotationPresent(ManyToMany.class)) {
                    ManyToMany manyToMany =  column.getAnnotation(ManyToMany.class);
                    createJoinTable(manyToMany, sqLiteDatabase);
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

    private void createJoinTable(ManyToMany manyToMany, SQLiteDatabase sqLiteDatabase) {

        if(manyToMany.joinTable() == null) {
            return;
        }

        Log.i("Sugar", "Create join table");
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append(manyToMany.joinTable()).append(" ( ")
                .append(manyToMany.columnOneName()).append(" INTEGER NOT NULL,")
                .append(manyToMany.columnTwoName()).append(" INTEGER NOT NULL,")
                .append(" PRIMARY KEY(").append(manyToMany.columnOneName()).append(", ").append(manyToMany.columnTwoName()).append(" )")
        .append(" );");

        if (!"".equals(sb.toString())) {
            try {
                sqLiteDatabase.execSQL(sb.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}