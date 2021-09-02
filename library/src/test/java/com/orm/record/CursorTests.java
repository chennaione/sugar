package com.orm.record;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.orm.SugarContext;
import com.orm.model.SimpleModel;
import com.orm.query.Select;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import androidx.test.core.app.ApplicationProvider;

import static com.orm.SugarRecord.save;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertSame;

@RunWith( RobolectricTestRunner.class )
public final class CursorTests
{
    @Before
    public void setUp( )
    {
        SugarContext.init( ApplicationProvider.getApplicationContext( ) );
    }

    @Test
    public void testColumnNames( )
    {
        save( new SimpleModel( ) );
        Cursor c = Select.from( SimpleModel.class ).getCursor( );
        for( String col : new String[]{"STR", "INTEGER", "BOOL", "ID"} )
        {
            assertNotSame( "Missing column for field: " + col, -1, c.getColumnIndex( col ) );
        }
    }

    @Test
    public void testSugarCursor( )
    {
        save( new SimpleModel( ) );
        Cursor cursor = Select.from( SimpleModel.class ).getCursor( );
        assertNotSame( "No _id", -1, cursor.getColumnIndex( "_id" ) );
        assertSame( "_id != ID", cursor.getColumnIndex( "_id" ), cursor.getColumnIndex( "ID" ) );
    }

    @Test
    public void testNoColumn( )
    {
        save( new SimpleModel( ) );
        Cursor cursor = Select.from( SimpleModel.class ).getCursor( );
        assertSame( -1, cursor.getColumnIndex( "nonexistent" ) );
    }

    @TargetApi( Build.VERSION_CODES.HONEYCOMB )
    @Test
    public void testMakeAdapter( )
    {
        save( new SimpleModel( ) );
        Cursor c = Select.from( SimpleModel.class ).getCursor( );
        CursorAdapter adapter = new CursorAdapter( RuntimeEnvironment.application, c, true )
        {
            @Override
            public View newView( Context context, Cursor cursor, ViewGroup parent )
            {
                TextView tv = new TextView( context );
                String s = cursor.getString( cursor.getColumnIndex( "STR" ) );
                tv.setText( s );
                return null;
            }

            @Override
            public void bindView( View view, Context context, Cursor cursor )
            {
                String s = cursor.getString( cursor.getColumnIndex( "STR" ) );
                ( (TextView) view ).setText( s );
            }
        };

        Assert.assertNotNull( adapter );
    }
}
