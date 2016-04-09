package com.orm;

import android.database.sqlite.SQLiteDatabase;

import com.orm.app.ClientApp;
import com.orm.dsl.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

/**
 * @author jonatan.salas
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, constants = BuildConfig.class, application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class SugarDbTest {
    private final SugarDb sugarDb = SugarDb.getInstance();

    @Test
    //TODO check this better!
    public void testGetReadableDatabase() {
        final SQLiteDatabase db = sugarDb.getReadableDatabase();
        assertEquals(false, db.isReadOnly());
    }

    @Test
    public void testGetWritableDatabase() {
        final SQLiteDatabase db = sugarDb.getWritableDatabase();
        assertEquals(false, db.isReadOnly());
    }

    @Test
    public void testGetDB() {
        final SQLiteDatabase db = sugarDb.getDB();
        assertEquals(false, db.isReadOnly());
    }
}
