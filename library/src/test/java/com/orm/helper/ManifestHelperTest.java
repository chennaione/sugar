package com.orm.helper;

import com.orm.SugarContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import androidx.test.core.app.ApplicationProvider;

import static com.orm.helper.ManifestHelper.DATABASE_DEFAULT_NAME;
import static com.orm.helper.ManifestHelper.getDatabaseName;
import static com.orm.helper.ManifestHelper.getDatabaseVersion;
import static com.orm.helper.ManifestHelper.getDomainPackageName;
import static com.orm.helper.ManifestHelper.isDebugEnabled;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author jonatan.salas
 */
@RunWith( RobolectricTestRunner.class )
public final class ManifestHelperTest
{
    @Before
    public void setUp( )
    {
        SugarContext.init( ApplicationProvider.getApplicationContext( ) );
    }

    @Test( expected = IllegalAccessException.class )
    public void testPrivateConstructor( ) throws Exception
    {
        ManifestHelper helper = ManifestHelper.class.getDeclaredConstructor( ).newInstance( );
        assertNull( helper );
    }

    @Test
    public void testGetDbName( )
    {
        assertEquals( DATABASE_DEFAULT_NAME, getDatabaseName( ) );
    }

    @Test
    public void testGetDatabaseName( )
    {
        assertEquals( DATABASE_DEFAULT_NAME, getDatabaseName( ) );
    }

    @Test
    public void testGetDatabaseVersion( )
    {
        assertEquals( 1, getDatabaseVersion( ) );
    }

    @Test
    public void testGetDomainPackageName( )
    {
        assertNotNull( getDomainPackageName( ) );
    }

    @Test
    public void testGetDebugEnabled( )
    {
        assertEquals( false, isDebugEnabled( ) );
    }
}
