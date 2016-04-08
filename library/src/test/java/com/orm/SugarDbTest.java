package com.orm;

import android.database.sqlite.SQLiteDatabase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * @author jonatan.salas
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, application = ClientApp.class, manifest = Config.NONE)
public class SugarDbTest {
    private final SugarDb sugarDb = SugarDb.getInstance();

    @Before
    public void setUp() {
        SugarContext.init(RuntimeEnvironment.application);
    }

    @Test
    //TODO check this better!
    public void testGetReadableDatabase() {
        final SQLiteDatabase db = sugarDb.getReadableDatabase();
        Assert.assertEquals(false, db.isReadOnly());
    }

    @Test
    public void testGetWritableDatabase() {
        final SQLiteDatabase db = sugarDb.getWritableDatabase();
        Assert.assertEquals(false, db.isReadOnly());
    }

    @Test
    public void testGetDB() {
        final SQLiteDatabase db = sugarDb.getDB();
        Assert.assertEquals(false, db.isReadOnly());
    }
}
