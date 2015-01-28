package com.orm.test;

import com.orm.SugarRecord;

public class Foo extends SugarRecord {
	public String string;
	public int number;
	public byte[] blob;
	
	public String getString() {
		return string;
	}
	
	public int getNumber() {
		return number;
	}
	
	public byte[] getBlob() {
		return blob;
	}
}
