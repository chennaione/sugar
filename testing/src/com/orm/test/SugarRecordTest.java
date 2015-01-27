package com.orm.test;

import junit.framework.TestCase;

public class SugarRecordTest extends TestCase {
	public void testBasic() throws Exception {
		int val = 12312321;
		String str = "This is a string";
		
		Foo foo = new Foo();
		foo.number = val;
		foo.string = str;
		foo.blob = str.getBytes();
		

		long id = foo.save();
		
		assertTrue("Record was inserted correctly", id > 0);

		Foo record = Foo.findById(Foo.class, id);
		assertEquals(val, record.getNumber());
		assertEquals(str, record.getString());
		assertEquals(str, new String(record.getBlob()));
		
	}

	@Override
	public void tearDown() {
		Foo.deleteAll(Foo.class);
	}
}
