package com.example.sugartest;


import android.database.sqlite.SQLiteException;

import com.example.models.IncompleteAnnotatedModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import static com.orm.SugarRecord.delete;
import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertFalse;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk=18)
public class IncompleteAnnotatedModelTests {
    @Test(expected=SQLiteException.class)
    public void saveNoIdFieldTest() {
        save(new IncompleteAnnotatedModel());
    }

    @Test
    public void deleteNoIdFieldTest() {
        assertFalse(delete(new IncompleteAnnotatedModel()));
    }
}
