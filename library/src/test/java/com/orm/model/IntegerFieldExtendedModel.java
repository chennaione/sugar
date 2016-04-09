package com.orm.model;

import com.orm.SugarRecord;

public class IntegerFieldExtendedModel extends SugarRecord {
    private Integer integer;
    private int rawInteger;

    public IntegerFieldExtendedModel() {}

    public IntegerFieldExtendedModel(Integer integer) {
        this.integer = integer;
    }

    public IntegerFieldExtendedModel(int rawInteger) {
        this.rawInteger = rawInteger;
    }

    public Integer getInteger() {
        return integer;
    }

    public int getInt() {
        return rawInteger;
    }
}
