package com.orm.record;

import com.orm.SugarContext;
import com.orm.model.ShortFieldAnnotatedModel;
import com.orm.model.ShortFieldExtendedModel;

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
public final class ShortFieldTests
{
    private Short aShort = Short.valueOf( (short) 25 );

    @Before
    public void setUp( )
    {
        SugarContext.init( ApplicationProvider.getApplicationContext( ) );
    }

    @Test
    public void nullShortExtendedTest( )
    {
        save( new ShortFieldExtendedModel( ) );
        ShortFieldExtendedModel model = findById( ShortFieldExtendedModel.class, 1 );
        assertNull( model.getShort( ) );
    }

    @Test
    public void nullRawShortExtendedTest( )
    {
        save( new ShortFieldExtendedModel( ) );
        ShortFieldExtendedModel model = findById( ShortFieldExtendedModel.class, 1 );
        assertEquals( (short) 0, model.getRawShort( ) );
    }

    @Test
    public void nullShortAnnotatedTest( )
    {
        save( new ShortFieldAnnotatedModel( ) );
        ShortFieldAnnotatedModel model = findById( ShortFieldAnnotatedModel.class, 1 );
        assertNull( model.getShort( ) );
    }

    @Test
    public void nullRawShortAnnotatedTest( )
    {
        save( new ShortFieldAnnotatedModel( ) );
        ShortFieldAnnotatedModel model = findById( ShortFieldAnnotatedModel.class, 1 );
        assertEquals( (short) 0, model.getRawShort( ) );
    }

    @Test
    public void objectShortExtendedTest( )
    {
        save( new ShortFieldExtendedModel( aShort ) );
        ShortFieldExtendedModel model = findById( ShortFieldExtendedModel.class, 1 );
        assertEquals( aShort, model.getShort( ) );
    }

    @Test
    public void rawShortExtendedTest( )
    {
        save( new ShortFieldExtendedModel( aShort.shortValue( ) ) );
        ShortFieldExtendedModel model = findById( ShortFieldExtendedModel.class, 1 );
        assertEquals( aShort.shortValue( ), model.getRawShort( ) );
    }

    @Test
    public void objectShortAnnotatedTest( )
    {
        save( new ShortFieldAnnotatedModel( aShort ) );
        ShortFieldAnnotatedModel model = findById( ShortFieldAnnotatedModel.class, 1 );
        assertEquals( aShort, model.getShort( ) );
    }

    @Test
    public void rawShortAnnotatedTest( )
    {
        save( new ShortFieldAnnotatedModel( aShort.shortValue( ) ) );
        ShortFieldAnnotatedModel model = findById( ShortFieldAnnotatedModel.class, 1 );
        assertEquals( aShort.shortValue( ), model.getRawShort( ) );
    }
}
