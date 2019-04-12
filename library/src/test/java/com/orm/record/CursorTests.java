package com.orm.record;

import android.annotation.*;
import android.content.*;
import android.database.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import com.orm.app.*;
import com.orm.model.*;
import com.orm.query.*;

import org.junit.*;
import org.junit.runner.*;
import org.robolectric.*;
import org.robolectric.annotation.*;

import static com.orm.SugarRecord.*;
import static junit.framework.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 18, /*constants = BuildConfig.class,*/ application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class CursorTests {

	@Test
	public void testColumnNames() {
		save(new SimpleModel());
		Cursor c = Select.from(SimpleModel.class).getCursor();
		for (String col : new String[]{"STR", "INTEGER", "BOOL", "ID"}) {
			assertNotSame("Missing column for field: " + col, -1, c.getColumnIndex(col));
		}
	}
	@Test
	public void testSugarCursor() {
		save(new SimpleModel());
		Cursor cursor = Select.from(SimpleModel.class).getCursor();
		assertNotSame("No _id", -1, cursor.getColumnIndex("_id"));
		assertSame("_id != ID", cursor.getColumnIndex("_id"), cursor.getColumnIndex("ID"));
	}

	@Test
	public void testNoColumn() {
		save(new SimpleModel());
		Cursor cursor = Select.from(SimpleModel.class).getCursor();
		assertSame(-1, cursor.getColumnIndex("nonexistent"));
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Test
	public void testMakeAdapter() {
		save(new SimpleModel());
		Cursor c = Select.from(SimpleModel.class).getCursor();
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

		Assert.assertNotNull(adapter);
	}
}
