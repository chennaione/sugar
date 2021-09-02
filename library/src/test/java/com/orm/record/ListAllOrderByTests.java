package com.orm.record;

import com.orm.SugarContext;
import com.orm.model.IntegerFieldExtendedModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import androidx.test.core.app.ApplicationProvider;

import static com.orm.SugarRecord.listAll;
import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith( RobolectricTestRunner.class )
public final class ListAllOrderByTests
{
    @Before
    public void setUp( )
    {
        SugarContext.init( ApplicationProvider.getApplicationContext( ) );
    }

    @Test
    public void listAllOrderByEmptyTest( )
    {
        final List<IntegerFieldExtendedModel> list = listAll( IntegerFieldExtendedModel.class, "id" );
        assertEquals( 0L, list.size( ) );
    }

    @Test
    public void listAllOrderByIdTest( )
    {
        for( int i = 1; i <= 100; i++ )
        {
            save( new IntegerFieldExtendedModel( i ) );
        }

        List<IntegerFieldExtendedModel> models = listAll( IntegerFieldExtendedModel.class, "id" );
        assertEquals( 100L, models.size( ) );

        Long id = models.get( 0 ).getId( );

        for( int i = 1; i < 100; i++ )
        {
            assertTrue( id < models.get( i ).getId( ) );
        }
    }

    @Test
    public void listAllOrderByFieldTest( )
    {
        for( int i = 1; i <= 100; i++ )
        {
            save( new IntegerFieldExtendedModel( i ) );
        }

        List<IntegerFieldExtendedModel> models = listAll( IntegerFieldExtendedModel.class, "raw_integer" );

        assertEquals( 100L, models.size( ) );

        int raw = models.get( 0 ).getInt( );

        for( int i = 1; i < 100; i++ )
        {
            assertTrue( raw < models.get( i ).getInt( ) );
        }
    }
}
