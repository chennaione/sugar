package com.example.sugartest;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.models.PrimaryKeyNotationSimpleModel;
import com.orm.query.Select;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;


import static com.orm.SugarRecord.save;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertSame;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk=18)
public class PrimaryKeyNotationCursorTests{
    @Test
    public void testColumnNames() {
        save(new PrimaryKeyNotationSimpleModel());
        Cursor c = Select.from(PrimaryKeyNotationSimpleModel.class).getCursor();
        for (String col : new String[]{"STR", "INTEGER", "BOOL", "MY_ID"}) {
            assertNotSame("Missing column for field: " + col, -1, c.getColumnIndex(col));
        }
    }
    @Test
    public void testSugarCursor(){
        save(new PrimaryKeyNotationSimpleModel());
        Cursor cursor = Select.from(PrimaryKeyNotationSimpleModel.class).getCursor();
        assertNotSame("No _MY_ID", -1, cursor.getColumnIndex("MY_ID"));
        assertNotSame("_MY_ID != MY_ID", cursor.getColumnIndex("_MY_ID"), cursor.getColumnIndex("MY_ID"));
    }

    @Test
    public void testNoColumn(){
        save(new PrimaryKeyNotationSimpleModel());
        Cursor cursor = Select.from(PrimaryKeyNotationSimpleModel.class).getCursor();
        assertSame(-1, cursor.getColumnIndex("nonexistent"));
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Test
    public void testMakeAdapter() {
        save(new PrimaryKeyNotationSimpleModel());
        Cursor c = Select.from(PrimaryKeyNotationSimpleModel.class).getCursor();
        CursorAdapter adapter = new CursorAdapter(RuntimeEnvironment.application, c, true) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                TextView tv = new TextView(context);
                String s = cursor.getString(cursor.getColumnIndex("STR"));
                tv.setText(s);
                return null;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                String s = cursor.getString(cursor.getColumnIndex("STR"));
                ((TextView) view).setText(s);
            }
        };
    }
}
