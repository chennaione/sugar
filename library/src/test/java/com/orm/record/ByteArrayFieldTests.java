package com.orm.record;

import com.orm.SugarContext;
import com.orm.model.ByteArrayAnnotatedModel;
import com.orm.model.ByteArrayExtendedModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import androidx.test.core.app.ApplicationProvider;

import static com.orm.SugarRecord.findById;
import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith( RobolectricTestRunner.class )
public final class ByteArrayFieldTests
{
    @Before
    public void setUp( )
    {
        SugarContext.init( ApplicationProvider.getApplicationContext( ) );
    }

    @Test
    public void nullByteArrayExtendedTest( )
    {
        byte[] array = "".getBytes( );
        save( new ByteArrayExtendedModel( ) );
        ByteArrayExtendedModel model = findById( ByteArrayExtendedModel.class, 1 );
        assertEquals( new String( array ), new String( model.getByteArray( ) ) );
        assertArrayEquals( array, model.getByteArray( ) );
    }

    @Test
    public void nullByteArrayAnnotatedTest( )
    {
        byte[] array = "".getBytes( );
        save( new ByteArrayAnnotatedModel( ) );
        ByteArrayAnnotatedModel model = findById( ByteArrayAnnotatedModel.class, 1 );
        assertEquals( new String( array ), new String( model.getByteArray( ) ) );
        assertArrayEquals( array, model.getByteArray( ) );
    }

    @Test
    public void byteArrayExtendedTest( )
    {
        byte[] array = "hello".getBytes( );
        save( new ByteArrayExtendedModel( array ) );
        ByteArrayExtendedModel model = findById( ByteArrayExtendedModel.class, 1 );
        assertEquals( new String( array ), new String( model.getByteArray( ) ) );
        assertArrayEquals( array, model.getByteArray( ) );
    }

    @Test
    public void byteArrayAnnotatedTest( )
    {
        byte[] array = "hello".getBytes( );
        save( new ByteArrayAnnotatedModel( array ) );
        ByteArrayAnnotatedModel model = findById( ByteArrayAnnotatedModel.class, 1 );
        assertEquals( new String( array ), new String( model.getByteArray( ) ) );
        assertArrayEquals( array, model.getByteArray( ) );
    }
}
