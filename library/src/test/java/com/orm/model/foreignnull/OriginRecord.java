package com.orm.model.foreignnull;

import com.orm.annotation.Table;

@Table
public class OriginRecord {

	private Long id;
	private OriginRecord origin;

	public OriginRecord() {
	}

	public OriginRecord(Long id, OriginRecord origin) {
		this.id = id;
		this.origin = origin;
	}


}
