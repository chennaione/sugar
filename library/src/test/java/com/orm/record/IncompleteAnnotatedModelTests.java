package com.orm.record;

import android.database.sqlite.SQLiteException;

import com.orm.app.ClientApp;
import com.orm.dsl.BuildConfig;
import com.orm.model.IncompleteAnnotatedModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.orm.SugarRecord.delete;
import static com.orm.SugarRecord.save;

import static org.junit.Assert.assertFalse;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, constants = BuildConfig.class, application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class IncompleteAnnotatedModelTests {

    @Test(expected = SQLiteException.class)
    public void saveNoIdFieldTest() {
        save(new IncompleteAnnotatedModel());
    }

    @Test
    public void deleteNoIdFieldTest() {
        assertFalse(delete(new IncompleteAnnotatedModel()));
    }
}
