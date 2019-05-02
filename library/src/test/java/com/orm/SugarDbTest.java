package com.orm;

import android.database.sqlite.*;

import com.orm.app.*;

import org.junit.*;
import org.junit.runner.*;
import org.robolectric.*;
import org.robolectric.annotation.*;

import static org.junit.Assert.*;

/**
 * @author jonatan.salas
 */

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 18, /*constants = BuildConfig.class,*/ application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class SugarDbTest {
	private final SugarDb sugarDb = SugarDb.getInstance();

	@Test
	//TODO check this better!
	public void testGetReadableDatabase() {
		final SQLiteDatabase db = sugarDb.getReadableDatabase();
		assertFalse(db.isReadOnly());
	}

	@Test
	public void testGetWritableDatabase() {
		final SQLiteDatabase db = sugarDb.getWritableDatabase();
		assertFalse(db.isReadOnly());
	}

	@Test
	public void testGetDB() {
		final SQLiteDatabase db = sugarDb.getDB();
		assertFalse(db.isReadOnly());
	}
}
