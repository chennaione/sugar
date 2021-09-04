package com.orm.record;

import com.orm.SugarContext;
import com.orm.model.NestedMixedBAModel;
import com.orm.model.RelationshipMixedAModel;
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
public final class NestedMixedBATests
{
    @Before
    public void setUp( )
    {
        SugarContext.init( ApplicationProvider.getApplicationContext( ) );
    }

    @Test
    public void emptyDatabaseTest( ) throws Exception
    {
        assertEquals( 0L, count( NestedMixedBAModel.class ) );
        assertEquals( 0L, count( RelationshipMixedAModel.class ) );
        assertEquals( 0L, count( SimpleAnnotatedModel.class ) );
    }

    @Test
    public void oneSaveTest( ) throws Exception
    {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel( );
        save( simple );
        RelationshipMixedAModel nested = new RelationshipMixedAModel( simple );

        save( nested );
        save( new NestedMixedBAModel( nested ) );

        assertEquals( 1L, count( SimpleAnnotatedModel.class ) );
        assertEquals( 1L, count( RelationshipMixedAModel.class ) );
        assertEquals( 1L, count( NestedMixedBAModel.class ) );
    }

    @Test
    public void twoSameSaveTest( ) throws Exception
    {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel( );
        save( simple );
        RelationshipMixedAModel nested = new RelationshipMixedAModel( simple );

        save( nested );
        save( new NestedMixedBAModel( nested ) );
        save( new NestedMixedBAModel( nested ) );

        assertEquals( 1L, count( SimpleAnnotatedModel.class ) );
        assertEquals( 1L, count( RelationshipMixedAModel.class ) );
        assertEquals( 2L, count( NestedMixedBAModel.class ) );
    }

    @Test
    public void twoDifferentSaveTest( ) throws Exception
    {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel( );
        save( simple );
        SimpleAnnotatedModel anotherSimple = new SimpleAnnotatedModel( );
        save( anotherSimple );
        RelationshipMixedAModel nested = new RelationshipMixedAModel( simple );
        save( nested );
        RelationshipMixedAModel anotherNested = new RelationshipMixedAModel( anotherSimple );

        save( anotherNested );
        save( new NestedMixedBAModel( nested ) );
        save( new NestedMixedBAModel( anotherNested ) );

        assertEquals( 2L, count( SimpleAnnotatedModel.class ) );
        assertEquals( 2L, count( RelationshipMixedAModel.class ) );
        assertEquals( 2L, count( NestedMixedBAModel.class ) );
    }

    @Test
    public void manySameSaveTest( ) throws Exception
    {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel( );
        save( simple );
        RelationshipMixedAModel nested = new RelationshipMixedAModel( simple );
        save( nested );

        for( int i = 1; i <= 100; i++ )
        {
            save( new NestedMixedBAModel( nested ) );
        }

        assertEquals( 1L, count( SimpleAnnotatedModel.class ) );
        assertEquals( 1L, count( RelationshipMixedAModel.class ) );
        assertEquals( 100L, count( NestedMixedBAModel.class ) );
    }

    @Test
    public void manyDifferentSaveTest( ) throws Exception
    {
        for( int i = 1; i <= 100; i++ )
        {
            SimpleAnnotatedModel simple = new SimpleAnnotatedModel( );
            save( simple );
            RelationshipMixedAModel nested = new RelationshipMixedAModel( simple );
            save( nested );
            save( new NestedMixedBAModel( nested ) );
        }

        assertEquals( 100L, count( SimpleAnnotatedModel.class ) );
        assertEquals( 100L, count( RelationshipMixedAModel.class ) );
        assertEquals( 100L, count( NestedMixedBAModel.class ) );
    }

    @Test
    public void listAllSameTest( ) throws Exception
    {
        SimpleAnnotatedModel simple = new SimpleAnnotatedModel( );
        save( simple );
        RelationshipMixedAModel nested = new RelationshipMixedAModel( simple );
        save( nested );

        for( int i = 1; i <= 100; i++ )
        {
            save( new NestedMixedBAModel( nested ) );
        }

        List<NestedMixedBAModel> models = listAll( NestedMixedBAModel.class );
        assertEquals( 100, models.size( ) );

        for( NestedMixedBAModel model : models )
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
            RelationshipMixedAModel nested = new RelationshipMixedAModel( simple );
            save( nested );
            save( new NestedMixedBAModel( nested ) );
        }

        List<NestedMixedBAModel> models = listAll( NestedMixedBAModel.class );
        assertEquals( 100, models.size( ) );

        for( NestedMixedBAModel model : models )
        {
            assertEquals( model.getId( ), model.getNested( ).getId( ) );
            assertEquals( model.getId( ), model.getNested( ).getSimple( ).getId( ) );
        }
    }
}
