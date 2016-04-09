package com.orm.model;

import com.orm.SugarRecord;

public class ShortFieldExtendedModel extends SugarRecord {
    private Short objectShort;
    private short rawShort;

    public ShortFieldExtendedModel() {}

    public ShortFieldExtendedModel(Short objectShort) {
        this.objectShort = objectShort;
    }

    public ShortFieldExtendedModel(short rawShort) {
        this.rawShort = rawShort;
    }

    public Short getShort() {
        return objectShort;
    }

    public short getRawShort() {
        return rawShort;
    }
}
