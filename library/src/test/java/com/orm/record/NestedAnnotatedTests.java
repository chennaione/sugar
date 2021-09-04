package com.orm.record;

import com.orm.SugarContext;
import com.orm.model.NestedAnnotatedModel;
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
public final class NestedAnnotatedTests
{
    @Before
    public void setUp( )
    {
        SugarContext.init( ApplicationProvider.getApplicationContext( ) );
    }

    @Test
    public void emptyDatabaseTest( ) throws Exception
    {
        assertEquals( 0L, count( NestedAnnotatedModel.class ) );
        assertEquals( 0L, count( RelationshipAnnotatedModel.class ) );
        assertEquals( 0L, count( SimpleAnnotatedModel.class ) );
    }

    @Test
    public void oneSaveTest( ) throws Exception
    {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel( );
        save( simple );
        RelationshipAnnotatedModel nested = new RelationshipAnnotatedModel( simple );
        save( nested );
        save( new NestedAnnotatedModel( nested ) );
        assertEquals( 1L, count( SimpleAnnotatedModel.class ) );
        assertEquals( 1L, count( RelationshipAnnotatedModel.class ) );
        assertEquals( 1L, count( NestedAnnotatedModel.class ) );
    }

    @Test
    public void twoSameSaveTest( ) throws Exception
    {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel( );
        save( simple );
        RelationshipAnnotatedModel nested = new RelationshipAnnotatedModel( simple );
        save( nested );
        save( new NestedAnnotatedModel( nested ) );
        save( new NestedAnnotatedModel( nested ) );

        assertEquals( 1L, count( SimpleAnnotatedModel.class ) );
        assertEquals( 1L, count( RelationshipAnnotatedModel.class ) );
        assertEquals( 2L, count( NestedAnnotatedModel.class ) );
    }

    @Test
    public void twoDifferentSaveTest( ) throws Exception
    {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel( );
        save( simple );
        SimpleAnnotatedModel anotherSimple = new SimpleAnnotatedModel( );
        save( anotherSimple );
        RelationshipAnnotatedModel nested = new RelationshipAnnotatedModel( simple );
        save( nested );
        RelationshipAnnotatedModel anotherNested = new RelationshipAnnotatedModel( anotherSimple );
        save( anotherNested );
        save( new NestedAnnotatedModel( nested ) );
        save( new NestedAnnotatedModel( anotherNested ) );
        assertEquals( 2L, count( SimpleAnnotatedModel.class ) );
        assertEquals( 2L, count( RelationshipAnnotatedModel.class ) );
        assertEquals( 2L, count( NestedAnnotatedModel.class ) );
    }

    @Test
    public void manySameSaveTest( ) throws Exception
    {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel( );
        save( simple );
        RelationshipAnnotatedModel nested = new RelationshipAnnotatedModel( simple );
        save( nested );
        for( int i = 1; i <= 100; i++ )
        {
            save( new NestedAnnotatedModel( nested ) );
        }
        assertEquals( 1L, count( SimpleAnnotatedModel.class ) );
        assertEquals( 1L, count( RelationshipAnnotatedModel.class ) );
        assertEquals( 100L, count( NestedAnnotatedModel.class ) );
    }

    @Test
    public void manyDifferentSaveTest( ) throws Exception
    {
        for( int i = 1; i <= 100; i++ )
        {
            SimpleAnnotatedModel simple = new SimpleAnnotatedModel( );
            save( simple );
            RelationshipAnnotatedModel nested = new RelationshipAnnotatedModel( simple );
            save( nested );
            save( new NestedAnnotatedModel( nested ) );
        }
        assertEquals( 100L, count( SimpleAnnotatedModel.class ) );
        assertEquals( 100L, count( RelationshipAnnotatedModel.class ) );
        assertEquals( 100L, count( NestedAnnotatedModel.class ) );
    }

    @Test
    public void listAllSameTest( ) throws Exception
    {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel( );
        save( simple );
        RelationshipAnnotatedModel nested = new RelationshipAnnotatedModel( simple );
        save( nested );

        for( int i = 1; i <= 100; i++ )
        {
            save( new NestedAnnotatedModel( nested ) );
        }

        List<NestedAnnotatedModel> models = listAll( NestedAnnotatedModel.class );
        assertEquals( 100, models.size( ) );

        for( NestedAnnotatedModel model : models )
        {
            assertEquals( nested.getId( ), model.getNested( ).getId( ) );
            assertEquals( simple.getId( ), model.getNested( ).getSimple( ).getId( ) );
        }
    }

    @Test
    public void listAllDifferentTest( ) throws Exception
    {
        for( int i = 1; i <= 100; i++ )
        {
            SimpleAnnotatedModel simple = new SimpleAnnotatedModel( );
            save( simple );
            RelationshipAnnotatedModel nested = new RelationshipAnnotatedModel( simple );
            save( nested );
            save( new NestedAnnotatedModel( nested ) );
        }

        List<NestedAnnotatedModel> models = listAll( NestedAnnotatedModel.class );
        assertEquals( 100, models.size( ) );

        for( NestedAnnotatedModel model : models )
        {
            assertEquals( model.getId( ), model.getNested( ).getId( ) );
            assertEquals( model.getId( ), model.getNested( ).getSimple( ).getId( ) );
        }
    }
}
