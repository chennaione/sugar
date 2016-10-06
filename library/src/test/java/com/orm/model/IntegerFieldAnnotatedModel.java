package com.orm.model;

import com.orm.annotation.Table;

@Table
public class IntegerFieldAnnotatedModel {
    private Integer integer;
    private int rawInteger;
    public Long id;

    public IntegerFieldAnnotatedModel() {}

    public IntegerFieldAnnotatedModel(Integer integer) {
        this.integer = integer;
    }

    public IntegerFieldAnnotatedModel(int rawInteger) {
        this.rawInteger = rawInteger;
    }

    public Integer getInteger() {
        return integer;
    }

    public int getInt() {
        return rawInteger;
    }
}
