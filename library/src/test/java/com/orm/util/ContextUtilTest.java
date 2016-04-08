package com.orm.util;

import android.content.Context;

import com.orm.ClientApp;
import com.orm.RobolectricGradleTestRunner;
import com.orm.SugarContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static com.orm.util.ContextUtil.*;

/**
 * @author jonatan.salas
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, application = ClientApp.class, manifest = Config.NONE)
public class ContextUtilTest {

    @Before
    public void setUp() {
        SugarContext.init(RuntimeEnvironment.application.getApplicationContext());
    }

    @Test
    public void testInitContext() {
        assertNotNull(getContext());
    }

    @Test
    public void testGetAssets() {
        assertNotNull(getAssets());
    }

    @Test
    public void testGetPackageManager() {
        assertNotNull(getPackageManager());
    }

    @Test
    public void testGetPackageName() {
        assertNotNull(getPackageName());
    }

    @Test
    public void testGetPreferences() {
        assertNotNull(getSharedPreferences("lala", Context.MODE_PRIVATE));
    }

    @Test
    public void testTerminateContext() {
        SugarContext.terminate();
        assertNull(getContext());
    }
}
