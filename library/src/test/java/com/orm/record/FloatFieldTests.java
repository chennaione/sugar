package com.orm.record;

import com.orm.SugarContext;
import com.orm.model.FloatFieldAnnotatedModel;
import com.orm.model.FloatFieldExtendedModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import androidx.test.core.app.ApplicationProvider;

import static com.orm.SugarRecord.findById;
import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SuppressWarnings( "all" )
@RunWith( RobolectricTestRunner.class )
public final class FloatFieldTests
{
    Float aFloat = Float.valueOf( 25F );

    @Before
    public void setUp( )
    {
        SugarContext.init( ApplicationProvider.getApplicationContext( ) );
    }

    @Test
    public void nullFloatExtendedTest( )
    {
        save( new FloatFieldExtendedModel( ) );
        FloatFieldExtendedModel model = findById( FloatFieldExtendedModel.class, 1 );
        assertNull( model.getFloat( ) );
    }

    @Test
    public void nullRawFloatExtendedTest( )
    {
        save( new FloatFieldExtendedModel( ) );
        FloatFieldExtendedModel model = findById( FloatFieldExtendedModel.class, 1 );
        assertEquals( 0F, model.getRawFloat( ), 0.0000000001F );
    }

    @Test
    public void nullFloatAnnotatedTest( )
    {
        save( new FloatFieldAnnotatedModel( ) );
        FloatFieldAnnotatedModel model = findById( FloatFieldAnnotatedModel.class, 1 );
        assertNull( model.getFloat( ) );
    }

    @Test
    public void nullRawFloatAnnotatedTest( )
    {
        save( new FloatFieldAnnotatedModel( ) );
        FloatFieldAnnotatedModel model = findById( FloatFieldAnnotatedModel.class, 1 );
        assertEquals( 0F, model.getRawFloat( ), 0.0000000001F );
    }

    @Test
    public void objectFloatExtendedTest( )
    {
        save( new FloatFieldExtendedModel( aFloat ) );
        FloatFieldExtendedModel model = findById( FloatFieldExtendedModel.class, 1 );
        assertEquals( aFloat, model.getFloat( ) );
    }

    @Test
    public void rawFloatExtendedTest( )
    {
        save( new FloatFieldExtendedModel( aFloat.floatValue( ) ) );
        FloatFieldExtendedModel model = findById( FloatFieldExtendedModel.class, 1 );
        assertEquals( aFloat.floatValue( ), model.getRawFloat( ), 0.0000000001F );
    }

    @Test
    public void objectFloatAnnotatedTest( )
    {
        save( new FloatFieldAnnotatedModel( aFloat ) );
        FloatFieldAnnotatedModel model = findById( FloatFieldAnnotatedModel.class, 1 );
        assertEquals( aFloat, model.getFloat( ) );
    }

    @Test
    public void rawFloatAnnotatedTest( )
    {
        save( new FloatFieldAnnotatedModel( aFloat.floatValue( ) ) );
        FloatFieldAnnotatedModel model = findById( FloatFieldAnnotatedModel.class, 1 );
        assertEquals( aFloat.floatValue( ), model.getRawFloat( ), 0.0000000001F );
    }
}
