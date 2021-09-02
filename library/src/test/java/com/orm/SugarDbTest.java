package com.orm;

import android.database.sqlite.SQLiteDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import androidx.test.core.app.ApplicationProvider;

import static org.junit.Assert.assertEquals;

/**
 * @author jonatan.salas
 */
@RunWith( RobolectricTestRunner.class )
public final class SugarDbTest
{
    private SugarDb sugarDb;

    @Before
    public void setUp( )
    {
        SugarContext.init( ApplicationProvider.getApplicationContext( ) );
        sugarDb = SugarDb.getInstance( );
    }

    @Test
    //TODO check this better!
    public void testGetReadableDatabase( )
    {
        final SQLiteDatabase db = sugarDb.getReadableDatabase( );
        assertEquals( false, db.isReadOnly( ) );
    }

    @Test
    public void testGetWritableDatabase( )
    {
        final SQLiteDatabase db = sugarDb.getWritableDatabase( );
        assertEquals( false, db.isReadOnly( ) );
    }

    @Test
    public void testGetDB( )
    {
        final SQLiteDatabase db = sugarDb.getDB( );
        assertEquals( false, db.isReadOnly( ) );
    }
}
