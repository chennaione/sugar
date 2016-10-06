package com.orm;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author jonatan.salas
 */
public final class SugarAppTest {

    @Test
    public void testOnCreate() {
        SugarApp app = new SugarApp();
        app.onCreate();

        SugarContext context = SugarContext.getSugarContext();
        Assert.assertNotNull(context);
    }


    @Test(expected = NullPointerException.class)
    public void testOnTerminate() {
        SugarApp app = new SugarApp();
        app.onCreate();
        app.onTerminate();

        SugarContext context = SugarContext.getSugarContext();
        Assert.assertNull(context);
    }
}
