package com.orm.record;

import android.database.sqlite.SQLiteException;

import com.orm.SugarContext;
import com.orm.model.IncompleteAnnotatedModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import androidx.test.core.app.ApplicationProvider;

import static com.orm.SugarRecord.delete;
import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertFalse;

@RunWith( RobolectricTestRunner.class )
public final class IncompleteAnnotatedModelTests
{
    @Before
    public void setUp( )
    {
        SugarContext.init( ApplicationProvider.getApplicationContext( ) );
    }

    @Test( expected = SQLiteException.class )
    public void saveNoIdFieldTest( )
    {
        save( new IncompleteAnnotatedModel( ) );
    }

    @Test
    public void deleteNoIdFieldTest( )
    {
        assertFalse( delete( new IncompleteAnnotatedModel( ) ) );
    }
}
