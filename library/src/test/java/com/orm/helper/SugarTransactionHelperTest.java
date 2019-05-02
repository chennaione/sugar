package com.orm.helper;

import com.orm.*;
import com.orm.app.*;
import com.orm.model.*;

import org.junit.*;
import org.junit.runner.*;
import org.robolectric.*;
import org.robolectric.annotation.*;

import java.util.*;

import static junit.framework.Assert.*;

/**
 * @author jonatan.salas
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 18, /*constants = BuildConfig.class,*/ application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class SugarTransactionHelperTest {
	private List<TestRecord> recordList = new ArrayList<>();
	private TestRecord record1 = new TestRecord();
	private TestRecord record2 = new TestRecord();
	private TestRecord record3 = new TestRecord();

	@Before
	public void setUp() {
		SugarContext.init(RuntimeEnvironment.application);

		record1.setId(1L);
		record1.setName("lala");

		record2.setId(2L);
		record2.setName("fefe");

		record3.setId(3L);
		record3.setName("jaja");

		recordList.add(record1);
		recordList.add(record2);
		recordList.add(record3);
	}

	@Test(expected = IllegalAccessException.class)
	public void testPrivateConstructor() throws Exception {
		SugarTransactionHelper helper = SugarTransactionHelper.class.getDeclaredConstructor().newInstance();
		assertNull(helper);
	}

	@Test
	public void testDoInTransaction() {
		SugarTransactionHelper.doInTransaction(new SugarTransactionHelper.Callback() {
			@Override
			public void manipulateInTransaction() {
				for (TestRecord record : recordList) {
					TestRecord.save(record);
				}
			}
		});

		final List<TestRecord> results = TestRecord.listAll(TestRecord.class);

		assertTrue(inList(results, record1));
		assertTrue(inList(results, record2));
		assertTrue(inList(results, record3));
	}

	private boolean inList(List<TestRecord> list, TestRecord testRecord) {
		for (TestRecord record : list) {
			if (record.getId().equals(testRecord.getId()) && record.getName().equals(testRecord.getName())) {
				return true;
			}
		}
		return false;
	}
}
