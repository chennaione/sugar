package com.orm.record;

import com.orm.SugarContext;
import com.orm.model.NoSugarModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import androidx.test.core.app.ApplicationProvider;

import static com.orm.SugarRecord.count;
import static com.orm.SugarRecord.delete;
import static com.orm.SugarRecord.saveInTx;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith( RobolectricTestRunner.class )
public final class NoSugarModelTests
{
    @Before
    public void setUp( )
    {
        SugarContext.init( ApplicationProvider.getApplicationContext( ) );
    }

    @Test
    public void deleteTest( ) throws Exception
    {
        NoSugarModel model = new NoSugarModel( );
        assertFalse( delete( model ) );
    }

    @Test
    public void saveInTransactionTest( ) throws Exception
    {
        saveInTx( new NoSugarModel( ), new NoSugarModel( ) );
        assertEquals( -1L, count( NoSugarModel.class ) );
    }
}
