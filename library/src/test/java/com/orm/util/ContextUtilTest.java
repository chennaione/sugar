package com.orm.util;

import android.content.Context;

import com.orm.query.DummyContext;

import org.junit.Test;


import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static com.orm.util.ContextUtil.*;

/**
 * @author jonatan.salas
 */
public class ContextUtilTest {

    public void initContextUtil() {
        init(new DummyContext());
    }

    @Test
    public void testInitContext() {
        initContextUtil();
        assertNotNull(getContext());
    }

    @Test
    public void testGetAssets() {
        initContextUtil();
        assertNull(getAssets());
    }

    @Test
    public void testGetPackageManager() {
        initContextUtil();
        assertNull(getPackageManager());
    }

    @Test
    public void testGetPackageName() {
        initContextUtil();
        assertNull(getPackageName());
    }

    @Test
    public void testGetPreferences() {
        initContextUtil();
        assertNull(getSharedPreferences("lala", Context.MODE_PRIVATE));
    }

    @Test
    public void testTerminateContext() {
        initContextUtil();
        terminate();
        assertNull(getContext());
    }
}
