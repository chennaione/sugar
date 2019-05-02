package com.orm.record;

import com.orm.app.*;
import com.orm.model.*;

import org.junit.*;
import org.junit.runner.*;
import org.robolectric.*;
import org.robolectric.annotation.*;

import static com.orm.SugarRecord.*;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 18, /*constants = BuildConfig.class,*/ application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class NoSugarModelTests {

	@Test
	public void deleteTest() throws Exception {
		NoSugarModel model = new NoSugarModel();
		assertFalse(delete(model));
	}

	@Test
	public void saveInTransactionTest() throws Exception {
		saveInTx(new NoSugarModel(), new NoSugarModel());
		assertEquals(-1L, count(NoSugarModel.class));
	}
}
