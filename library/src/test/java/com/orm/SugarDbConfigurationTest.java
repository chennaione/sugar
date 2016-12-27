package com.orm;

import com.orm.dsl.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author jonatan.salas
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 16, constants = BuildConfig.class)
public final class SugarDbConfigurationTest {

    @Test
    public void testNotNullConfiguration() {
        SugarDbConfiguration configuration = new SugarDbConfiguration()
                .setDatabaseLocale(Locale.getDefault())
                .setMaxSize(1024L)
                .setPageSize(400L);

        SugarContext.init(RuntimeEnvironment.application, configuration);

        final SugarDbConfiguration config = SugarContext.getDbConfiguration();

        assertEquals(configuration.getDatabaseLocale(), config.getDatabaseLocale());
        assertEquals(configuration.getMaxSize(), config.getMaxSize());
        assertEquals(configuration.getPageSize(), config.getPageSize());
    }

    @Test
    public void testNullConfiguration() {
        SugarContext.init(RuntimeEnvironment.application);
        assertNull(SugarContext.getDbConfiguration());
    }

//    @Test
//    public void testNotNullConfigurationWithSugarDb() {
//        SugarDbConfiguration configuration = new SugarDbConfiguration()
//                .setDatabaseLocale(Locale.getDefault())
//                .setMaxSize(100000L)
//                .setPageSize(100000L);
//
//        SugarContext.init(RuntimeEnvironment.application, configuration);
//
//        SQLiteDatabase database = SugarContext.getSugarContext().getSugarDb().getDB();
//        SQLiteDatabase sqLiteDatabase = SugarDb.getInstance().getDB();
//
//        assertEquals(database.getMaximumSize(), sqLiteDatabase.getMaximumSize());
//        assertEquals(database.getPageSize(), sqLiteDatabase.getPageSize());
//
//        if (sqLiteDatabase.isOpen()) {
//            sqLiteDatabase.close();
//        }
//    }
}
