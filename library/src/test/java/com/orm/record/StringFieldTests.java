package com.orm.record;

import com.orm.SugarContext;
import com.orm.model.StringFieldAnnotatedModel;
import com.orm.model.StringFieldExtendedModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import androidx.test.core.app.ApplicationProvider;

import static com.orm.SugarRecord.findById;
import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith( RobolectricTestRunner.class )
public final class StringFieldTests
{
    private String string = "Test String";

    @Before
    public void setUp( )
    {
        SugarContext.init( ApplicationProvider.getApplicationContext( ) );
    }

    @Test
    public void nullStringExtendedTest( )
    {
        save( new StringFieldExtendedModel( ) );
        StringFieldExtendedModel model = findById( StringFieldExtendedModel.class, 1 );
        assertNull( model.getString( ) );
    }

    @Test
    public void nullStringAnnotatedTest( )
    {
        save( new StringFieldAnnotatedModel( ) );
        StringFieldAnnotatedModel model = findById( StringFieldAnnotatedModel.class, 1 );
        assertNull( model.getString( ) );
    }

    @Test
    public void stringExtendedTest( )
    {
        save( new StringFieldExtendedModel( string ) );
        StringFieldExtendedModel model = findById( StringFieldExtendedModel.class, 1 );
        assertEquals( string, model.getString( ) );
    }

    @Test
    public void stringAnnotatedTest( )
    {
        save( new StringFieldAnnotatedModel( string ) );
        StringFieldAnnotatedModel model = findById( StringFieldAnnotatedModel.class, 1 );
        assertEquals( string, model.getString( ) );
    }
}
