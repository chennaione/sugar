package com.orm;

import android.database.sqlite.SQLiteDatabase;

import com.orm.dsl.BuildConfig;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Locale;

/**
 * @author jonatan.salas
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 16, constants = BuildConfig.class)
public class SugarDbConfigurationTest {

    @Test
    public void testNotNullConfiguration() {
        SugarDbConfiguration configuration = new SugarDbConfiguration()
                .setDatabaseLocale(Locale.getDefault())
                .setMaxSize(1024L)
                .setPageSize(400L);

        SugarContext.init(RuntimeEnvironment.application, configuration);

        final SugarDbConfiguration config = SugarContext.getDbConfiguration();

        Assert.assertEquals(configuration.getDatabaseLocale(), config.getDatabaseLocale());
        Assert.assertEquals(configuration.getMaxSize(), config.getMaxSize());
        Assert.assertEquals(configuration.getPageSize(), config.getPageSize());
    }

    @Test
    public void testNullConfiguration() {
        SugarContext.init(RuntimeEnvironment.application);

        Assert.assertNull(SugarContext.getDbConfiguration());
    }

    @Test
    public void testNotNullConfigurationWithSugarDb() {
        SugarDbConfiguration configuration = new SugarDbConfiguration()
                .setDatabaseLocale(Locale.getDefault())
                .setMaxSize(100000L)
                .setPageSize(100000L);

        SugarContext.init(RuntimeEnvironment.application, configuration);

        SQLiteDatabase database = SugarContext.getSugarContext().getSugarDb().getDB();
        SQLiteDatabase sqLiteDatabase = SugarDb.getInstance().getDB();

        Assert.assertEquals(database.getMaximumSize(), sqLiteDatabase.getMaximumSize());
        Assert.assertEquals(database.getPageSize(), sqLiteDatabase.getPageSize());

        if (sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }
}
