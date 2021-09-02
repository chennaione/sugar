package com.orm.record;

import com.orm.SugarContext;
import com.orm.model.RelationshipAnnotatedModel;
import com.orm.model.SimpleAnnotatedModel;

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
public final class RelationshipAnnotatedTests
{
    @Before
    public void setUp( )
    {
        SugarContext.init( ApplicationProvider.getApplicationContext( ) );
    }

    @Test
    public void emptyDatabaseTest( ) throws Exception
    {
        assertEquals( 0L, count( RelationshipAnnotatedModel.class ) );
        assertEquals( 0L, count( SimpleAnnotatedModel.class ) );
    }

    @Test
    public void oneSaveTest( ) throws Exception
    {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel( );

        save( simple );
        save( new RelationshipAnnotatedModel( simple ) );

        assertEquals( 1L, count( SimpleAnnotatedModel.class ) );
        assertEquals( 1L, count( RelationshipAnnotatedModel.class ) );
    }

    @Test
    public void twoSameSaveTest( ) throws Exception
    {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel( );

        save( simple );
        save( new RelationshipAnnotatedModel( simple ) );
        save( new RelationshipAnnotatedModel( simple ) );

        assertEquals( 1L, count( SimpleAnnotatedModel.class ) );
        assertEquals( 2L, count( RelationshipAnnotatedModel.class ) );
    }

    @Test
    public void twoDifferentSaveTest( ) throws Exception
    {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel( );
        save( simple );
        SimpleAnnotatedModel anotherSimple = new SimpleAnnotatedModel( );

        save( anotherSimple );
        save( new RelationshipAnnotatedModel( simple ) );
        save( new RelationshipAnnotatedModel( anotherSimple ) );

        assertEquals( 2L, count( SimpleAnnotatedModel.class ) );
        assertEquals( 2L, count( RelationshipAnnotatedModel.class ) );
    }

    @Test
    public void manySameSaveTest( ) throws Exception
    {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel( );
        save( simple );

        for( int i = 1; i <= 100; i++ )
        {
            save( new RelationshipAnnotatedModel( simple ) );
        }

        assertEquals( 1L, count( SimpleAnnotatedModel.class ) );
        assertEquals( 100L, count( RelationshipAnnotatedModel.class ) );
    }

    @Test
    public void manyDifferentSaveTest( ) throws Exception
    {
        for( int i = 1; i <= 100; i++ )
        {
            SimpleAnnotatedModel simple = new SimpleAnnotatedModel( );
            save( simple );
            save( new RelationshipAnnotatedModel( simple ) );
        }

        assertEquals( 100L, count( SimpleAnnotatedModel.class ) );
        assertEquals( 100L, count( RelationshipAnnotatedModel.class ) );
    }

    @Test
    public void listAllSameTest( ) throws Exception
    {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel( );
        save( simple );

        for( int i = 1; i <= 100; i++ )
        {
            save( new RelationshipAnnotatedModel( simple ) );
        }

        List<RelationshipAnnotatedModel> models = listAll( RelationshipAnnotatedModel.class );
        assertEquals( 100, models.size( ) );

        for( RelationshipAnnotatedModel model : models )
        {
            assertEquals( simple.getId( ), model.getSimple( ).getId( ) );
        }
    }

    @Test
    public void listAllDifferentTest( ) throws Exception
    {
        for( int i = 1; i <= 100; i++ )
        {
            SimpleAnnotatedModel simple = new SimpleAnnotatedModel( );
            save( simple );
            save( new RelationshipAnnotatedModel( simple ) );
        }

        List<RelationshipAnnotatedModel> models = listAll( RelationshipAnnotatedModel.class );
        assertEquals( 100, models.size( ) );

        for( RelationshipAnnotatedModel model : models )
        {
            assertEquals( model.getId( ), model.getSimple( ).getId( ) );
        }
    }
}
