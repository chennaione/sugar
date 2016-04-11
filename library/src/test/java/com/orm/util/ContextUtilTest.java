package com.orm.util;

import android.content.Context;

import com.orm.app.ClientApp;
import com.orm.SugarContext;
import com.orm.dsl.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static com.orm.util.ContextUtil.*;

/**
 * @author jonatan.salas
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, constants = BuildConfig.class, application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class ContextUtilTest {

    @Test(expected = IllegalAccessException.class)
    public void testPrivateConstructor() throws Exception {
        ContextUtil contextUtil = ContextUtil.class.getDeclaredConstructor().newInstance();
        assertNull(contextUtil);
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
