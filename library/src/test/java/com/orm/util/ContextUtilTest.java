package com.orm.util;

import android.content.Context;

import com.orm.SugarContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import androidx.test.core.app.ApplicationProvider;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static com.orm.util.ContextUtil.*;

/**
 * @author jonatan.salas
 */
@RunWith( RobolectricTestRunner.class )
public final class ContextUtilTest
{
    @Before
    public void setUp( )
    {
        SugarContext.init( ApplicationProvider.getApplicationContext( ) );
    }

    @Test( expected = IllegalAccessException.class )
    public void testPrivateConstructor( ) throws Exception
    {
        ContextUtil contextUtil = ContextUtil.class.getDeclaredConstructor( ).newInstance( );
        assertNull( contextUtil );
    }


    @Test
    public void testInitContext( )
    {
        assertNotNull( getContext( ) );
    }

    @Test
    public void testGetAssets( )
    {
        assertNotNull( getAssets( ) );
    }

    @Test
    public void testGetPackageManager( )
    {
        assertNotNull( getPackageManager( ) );
    }

    @Test
    public void testGetPackageName( )
    {
        assertNotNull( getPackageName( ) );
    }

    @Test
    public void testGetPreferences( )
    {
        assertNotNull( getSharedPreferences( "lala", Context.MODE_PRIVATE ) );
    }

    @Test
    public void testTerminateContext( )
    {
        SugarContext.terminate( );
        assertNull( getContext( ) );
    }
}
