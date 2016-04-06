package com.orm.util;

import com.orm.query.DummyContext;

import org.junit.Test;

import static org.junit.Assert.*;
import static com.orm.util.ManifestHelper.*;
import static com.orm.util.ContextUtil.init;

/**
 * @author jonatan.salas
 */
public class ManifestHelperTest {

    public void initContext() {
        init(new DummyContext());
    }

    @Test
    public void testGetDbName() {
        initContext();
        assertEquals(DATABASE_DEFAULT_NAME, getDatabaseName());
    }

    @Test
    public void testGetDatabaseName() {
        initContext();
        assertEquals(DATABASE_DEFAULT_NAME, getDatabaseName());
    }

    @Test
    public void testGetDatabaseVersion() {
        initContext();
        assertEquals(1, getDatabaseVersion());
    }

    @Test
    public void testGetDomainPackageName() {
        initContext();
        assertNotNull(getDomainPackageName());
    }

    @Test
    public void testGetDebugEnabled() {
        initContext();
        assertEquals(false, isDebugEnabled());
    }
}
