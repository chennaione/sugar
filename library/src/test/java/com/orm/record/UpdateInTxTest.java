package com.orm.record;

import com.orm.*;
import com.orm.app.*;
import com.orm.model.*;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.*;
import org.robolectric.*;
import org.robolectric.annotation.*;

import java.util.*;

/**
 * @author jonatan.salas
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 18, /*constants = BuildConfig.class,*/ application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class UpdateInTxTest {

	@Test
	public void testUpdateInTx() {
		final TestRecord record = new TestRecord();
		record.setName("lalala");

		Long id = SugarRecord.save(record);
		record.setId(id);

		final TestRecord record1 = new TestRecord();
		record1.setName("lalala");

		Long id1 = SugarRecord.save(record1);
		record1.setId(id1);

		final TestRecord record2 = new TestRecord();
		record2.setName("lalala");

		Long id2 = SugarRecord.save(record2);
		record2.setId(id2);

		final TestRecord record3 = new TestRecord();
		record3.setName("lalala");

		Long id3 = SugarRecord.save(record3);
		record3.setId(id3);

		final TestRecord record4 = new TestRecord();
		record4.setName("lalala");

		Long id4 = SugarRecord.save(record4);
		record.setId(id4);

		record.setName("fulano");
		record1.setName("fulano");
		record2.setName("fulano");
		record3.setName("fulano");
		record4.setName("fulano");

		SugarRecord.updateInTx(record, record1, record2, record3, record4);

		List<TestRecord> list = SugarRecord.listAll(TestRecord.class);

		for (TestRecord r : list) {
			Assert.assertEquals(record.getName(), r.getName());
		}
	}
}
