package com.example.sugartest;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.models.SimpleModel;
import com.orm.query.Select;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import static com.orm.SugarRecord.save;
import static junit.framework.Assert.assertNotSame;

@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk = 18)
public class CursorTests {


    @Test
    public void testColumnNames() {
        save(new SimpleModel());
        Cursor c = Select.from(SimpleModel.class).getCursor();
        for (String col : new String[]{"STR", "INTEGER", "BOOL", "ID"}) {
            assertNotSame("Missing column for field: " + col, -1, c.getColumnIndex(col));
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Test
    public void testMakeAdapter() {
        save(new SimpleModel());
        Cursor c = Select.from(SimpleModel.class).getCursor();
        CursorAdapter adapter = new CursorAdapter(Robolectric.application, c, true) {
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
