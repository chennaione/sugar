package com.orm.record;

import com.orm.SugarContext;
import com.orm.model.RelationshipExtendedModel;
import com.orm.model.SimpleExtendedModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import androidx.test.core.app.ApplicationProvider;

import static com.orm.SugarRecord.count;
import static com.orm.SugarRecord.listAll;
import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;

@RunWith( RobolectricTestRunner.class )
public class RelationshipExtendedTests
{
    @Before
    public void setUp( )
    {
        SugarContext.init( ApplicationProvider.getApplicationContext( ) );
    }

    @Test
    public void emptyDatabaseTest( ) throws Exception
    {
        assertEquals( 0L, count( RelationshipExtendedModel.class ) );
        assertEquals( 0L, count( SimpleExtendedModel.class ) );
    }

    @Test
    public void oneSaveTest( ) throws Exception
    {
        SimpleExtendedModel simple = new SimpleExtendedModel( );

        save( simple );
        save( new RelationshipExtendedModel( simple ) );

        assertEquals( 1L, count( SimpleExtendedModel.class ) );
        assertEquals( 1L, count( RelationshipExtendedModel.class ) );
    }

    @Test
    public void twoSameSaveTest( ) throws Exception
    {
        SimpleExtendedModel simple = new SimpleExtendedModel( );

        save( simple );
        save( new RelationshipExtendedModel( simple ) );
        save( new RelationshipExtendedModel( simple ) );

        assertEquals( 1L, count( SimpleExtendedModel.class ) );
        assertEquals( 2L, count( RelationshipExtendedModel.class ) );
    }

    @Test
    public void twoDifferentSaveTest( ) throws Exception
    {
        SimpleExtendedModel simple = new SimpleExtendedModel( );
        save( simple );
        SimpleExtendedModel anotherSimple = new SimpleExtendedModel( );

        save( anotherSimple );
        save( new RelationshipExtendedModel( simple ) );
        save( new RelationshipExtendedModel( anotherSimple ) );

        assertEquals( 2L, count( SimpleExtendedModel.class ) );
        assertEquals( 2L, count( RelationshipExtendedModel.class ) );
    }

    @Test
    public void manySameSaveTest( ) throws Exception
    {
        SimpleExtendedModel simple = new SimpleExtendedModel( );
        save( simple );

        for( int i = 1; i <= 100; i++ )
        {
            save( new RelationshipExtendedModel( simple ) );
        }

        assertEquals( 1L, count( SimpleExtendedModel.class ) );
        assertEquals( 100L, count( RelationshipExtendedModel.class ) );
    }

    @Test
    public void manyDifferentSaveTest( ) throws Exception
    {
        for( int i = 1; i <= 100; i++ )
        {
            SimpleExtendedModel simple = new SimpleExtendedModel( );
            save( simple );
            save( new RelationshipExtendedModel( simple ) );
        }

        assertEquals( 100L, count( SimpleExtendedModel.class ) );
        assertEquals( 100L, count( RelationshipExtendedModel.class ) );
    }

    @Test
    public void listAllSameTest( ) throws Exception
    {
        SimpleExtendedModel simple = new SimpleExtendedModel( );
        save( simple );

        for( int i = 1; i <= 100; i++ )
        {
            save( new RelationshipExtendedModel( simple ) );
        }

        List<RelationshipExtendedModel> models = listAll( RelationshipExtendedModel.class );
        assertEquals( 100, models.size( ) );

        for( RelationshipExtendedModel model : models )
        {
            assertEquals( simple.getId( ), model.getSimple( ).getId( ) );
        }
    }

    @Test
    public void listAllDifferentTest( ) throws Exception
    {
        for( int i = 1; i <= 100; i++ )
        {
            SimpleExtendedModel simple = new SimpleExtendedModel( );
            save( simple );
            save( new RelationshipExtendedModel( simple ) );
        }

        List<RelationshipExtendedModel> models = listAll( RelationshipExtendedModel.class );
        assertEquals( 100, models.size( ) );

        for( RelationshipExtendedModel model : models )
        {
            assertEquals( model.getId( ), model.getSimple( ).getId( ) );
        }
    }
}
