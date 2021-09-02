package com.orm.record;

import com.orm.SugarContext;
import com.orm.model.StringFieldAnnotatedModel;
import com.orm.model.StringFieldAnnotatedNoIdModel;
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
public final class MultipleSaveTests
{
    private String testString = "Test String";
    private String anotherString = "Another test";

    @Before
    public void setUp( )
    {
        SugarContext.init( ApplicationProvider.getApplicationContext( ) );
    }

    @Test
    public void stringMultipleSaveOriginalExtendedTest( )
    {
        StringFieldExtendedModel model = new StringFieldExtendedModel( testString );
        long id = save( model );
        StringFieldExtendedModel query = findById( StringFieldExtendedModel.class, id );

        if ( null != query )
        {
            assertEquals( testString, query.getString( ) );
        }

        model.setString( anotherString );

        assertEquals( id, save( model ) );
        assertNull( findById( StringFieldExtendedModel.class, 2 ) );
    }

    @Test
    public void stringMultipleSaveQueriedExtendedTest( )
    {
        StringFieldExtendedModel model = new StringFieldExtendedModel( testString );
        long id = save( model );
        StringFieldExtendedModel query = findById( StringFieldExtendedModel.class, id );

        if ( null != query )
        {
            assertEquals( testString, query.getString( ) );
            query.setString( anotherString );
            assertEquals( id, save( query ) );
            assertNull( findById( StringFieldExtendedModel.class, 2 ) );
        }
    }

    @Test
    public void stringMultipleSaveOriginalAnnotatedTest( )
    {
        StringFieldAnnotatedModel model = new StringFieldAnnotatedModel( testString );
        long id = save( model );
        StringFieldAnnotatedModel query = findById( StringFieldAnnotatedModel.class, id );

        if ( null != query )
        {
            assertEquals( testString, query.getString( ) );
            model.setString( anotherString );
            assertEquals( id, save( model ) );
            assertNull( findById( StringFieldAnnotatedModel.class, 2 ) );
        }
    }

    @Test
    public void stringMultipleSaveQueriedAnnotatedTest( )
    {
        StringFieldAnnotatedModel model = new StringFieldAnnotatedModel( testString );
        long id = save( model );
        StringFieldAnnotatedModel query = findById( StringFieldAnnotatedModel.class, id );

        if ( null != query )
        {
            assertEquals( testString, query.getString( ) );
            query.setString( anotherString );
            assertEquals( id, save( query ) );
            assertNull( findById( StringFieldAnnotatedModel.class, 2 ) );
        }
    }

    @Test
    public void stringMultipleSaveOriginalAnnotatedNoIdTest( )
    {
        StringFieldAnnotatedNoIdModel model = new StringFieldAnnotatedNoIdModel( testString );
        long id = save( model );
        StringFieldAnnotatedNoIdModel query = findById( StringFieldAnnotatedNoIdModel.class, id );

        if ( null != query )
        {
            assertEquals( testString, query.getString( ) );
            model.setString( anotherString );
            assertEquals( id, save( model ) );
            assertNull( findById( StringFieldAnnotatedNoIdModel.class, 2 ) );
        }
    }

    @Test
    public void stringMultipleSaveQueriedAnnotatedNoIdTest( )
    {
        StringFieldAnnotatedNoIdModel model = new StringFieldAnnotatedNoIdModel( testString );
        long id = save( model );
        StringFieldAnnotatedNoIdModel query = findById( StringFieldAnnotatedNoIdModel.class, id );

        if ( null != query )
        {
            assertEquals( testString, query.getString( ) );
            query.setString( anotherString );
            assertEquals( id, save( query ) );
            assertNull( findById( StringFieldAnnotatedNoIdModel.class, 2 ) );
        }
    }
}
