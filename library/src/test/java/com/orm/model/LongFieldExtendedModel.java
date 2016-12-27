package com.orm.model;

import com.orm.SugarRecord;

public class LongFieldExtendedModel extends SugarRecord {
    private Long objectLong;
    private long rawLong;

    public LongFieldExtendedModel() {}

    public LongFieldExtendedModel(Long objectLong) {
        this.objectLong = objectLong;
    }

    public LongFieldExtendedModel(long rawLong) {
        this.rawLong = rawLong;
    }

    public Long getLong() {
        return objectLong;
    }

    public long getRawLong() {
        return rawLong;
    }
}
