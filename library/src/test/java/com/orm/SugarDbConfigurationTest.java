package com.orm;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Locale;

import androidx.test.core.app.ApplicationProvider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author jonatan.salas
 */
@RunWith( RobolectricTestRunner.class )
public final class SugarDbConfigurationTest
{
    @Before
    public void setUp( )
    {
        SugarContext.init( ApplicationProvider.getApplicationContext( ) );
    }

    @Test
    public void testNotNullConfiguration( )
    {
        SugarDbConfiguration configuration = new SugarDbConfiguration( )
                .setDatabaseLocale( Locale.getDefault( ) )
                .setMaxSize( 1024L )
                .setPageSize( 400L );

        SugarContext.init( RuntimeEnvironment.application, configuration );

        final SugarDbConfiguration config = SugarContext.getDbConfiguration( );

        assertEquals( configuration.getDatabaseLocale( ), config.getDatabaseLocale( ) );
        assertEquals( configuration.getMaxSize( ), config.getMaxSize( ) );
        assertEquals( configuration.getPageSize( ), config.getPageSize( ) );
    }

    @Test
    public void testNullConfiguration( )
    {
        SugarContext.init( RuntimeEnvironment.application );
        assertNull( SugarContext.getDbConfiguration( ) );
    }

//    @Test
//    public void testNotNullConfigurationWithSugarDb() {
//        SugarDbConfiguration configuration = new SugarDbConfiguration()
//                .setDatabaseLocale(Locale.getDefault())
//                .setMaxSize(100000L)
//                .setPageSize(100000L);
//
//        SugarContext.init(RuntimeEnvironment.application, configuration);
//
//        SQLiteDatabase database = SugarContext.getSugarContext().getSugarDb().getDB();
//        SQLiteDatabase sqLiteDatabase = SugarDb.getInstance().getDB();
//
//        assertEquals(database.getMaximumSize(), sqLiteDatabase.getMaximumSize());
//        assertEquals(database.getPageSize(), sqLiteDatabase.getPageSize());
//
//        if (sqLiteDatabase.isOpen()) {
//            sqLiteDatabase.close();
//        }
//    }
}
