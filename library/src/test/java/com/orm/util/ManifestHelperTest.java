package com.orm.util;

import com.orm.ClientApp;
import com.orm.RobolectricGradleTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;
import static com.orm.helper.ManifestHelper.*;
import static com.orm.SugarContext.init;

/**
 * @author jonatan.salas
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, application = ClientApp.class, manifest = Config.NONE)
public class ManifestHelperTest {

    @Before
    public void setUp() {
        init(RuntimeEnvironment.application);
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
