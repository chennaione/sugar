package com.orm.util;

import android.content.*;

import com.orm.*;
import com.orm.app.*;

import org.junit.*;
import org.junit.runner.*;
import org.robolectric.*;
import org.robolectric.annotation.*;

import static com.orm.util.ContextUtil.*;
import static junit.framework.Assert.*;

/**
 * @author jonatan.salas
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 18, /*constants = BuildConfig.class,*/ application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
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
