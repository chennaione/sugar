package com.orm.helper;

import com.orm.app.*;

import org.junit.*;
import org.junit.runner.*;
import org.robolectric.*;
import org.robolectric.annotation.*;

import static com.orm.helper.ManifestHelper.*;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * @author jonatan.salas
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 18, /*constants = BuildConfig.class,*/ application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
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
		assertFalse(isDebugEnabled());
	}
}
