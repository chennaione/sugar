package com.orm.record;

import android.database.sqlite.*;

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
public final class IncompleteAnnotatedModelTests {

	@Test(expected = SQLiteException.class)
	public void saveNoIdFieldTest() {
		save(new IncompleteAnnotatedModel());
	}

	@Test
	public void deleteNoIdFieldTest() {
		assertFalse(delete(new IncompleteAnnotatedModel()));
	}
}
