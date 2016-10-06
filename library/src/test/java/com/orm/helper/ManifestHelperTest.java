package com.orm.helper;

import com.orm.app.ClientApp;
import com.orm.dsl.BuildConfig;
import com.orm.util.KeyWordUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static com.orm.helper.ManifestHelper.getDatabaseName;
import static com.orm.helper.ManifestHelper.getDatabaseVersion;
import static com.orm.helper.ManifestHelper.getDomainPackageName;
import static com.orm.helper.ManifestHelper.isDebugEnabled;
import static com.orm.helper.ManifestHelper.DATABASE_DEFAULT_NAME;

/**
 * @author jonatan.salas
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, constants = BuildConfig.class, application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class ManifestHelperTest {

    @Test(expected = IllegalAccessException.class)
    public void testPrivateConstructor() throws Exception {
        ManifestHelper helper = ManifestHelper.class.getDeclaredConstructor().newInstance();
        assertNull(helper);
    }


    @Test
    public void testGetDbName() {
        assertEquals(DATABASE_DEFAULT_NAME, getDatabaseName());
    }

    @Test
    public void testGetDatabaseName() {
        assertEquals(DATABASE_DEFAULT_NAME, getDatabaseName());
    }

    @Test
    public void testGetDatabaseVersion() {
        assertEquals(1, getDatabaseVersion());
    }

    @Test
    public void testGetDomainPackageName() {
        assertNotNull(getDomainPackageName());
    }

    @Test
    public void testGetDebugEnabled() {
        assertEquals(false, isDebugEnabled());
    }
}
