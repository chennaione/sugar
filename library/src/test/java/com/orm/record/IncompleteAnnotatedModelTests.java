package com.orm.record;

import android.database.sqlite.SQLiteException;

import com.orm.ClientApp;
import com.orm.RobolectricGradleTestRunner;
import com.orm.SugarContext;
import com.orm.models.IncompleteAnnotatedModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.orm.SugarRecord.delete;
import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertFalse;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk=18, application = ClientApp.class)
public class IncompleteAnnotatedModelTests {

    @Before
    public void setUp() {
        SugarContext.init(RuntimeEnvironment.application);
    }

    @Test(expected=SQLiteException.class)
    public void saveNoIdFieldTest() {
        save(new IncompleteAnnotatedModel());
    }

    @Test
    public void deleteNoIdFieldTest() {
        assertFalse(delete(new IncompleteAnnotatedModel()));
    }
}
