package com.example.models;

import com.orm.dsl.Table;

@Table
public class ShortFieldAnnotatedModel {
    private Short objectShort;
    private short rawShort;
    private Long id;

    public ShortFieldAnnotatedModel() {}

    public ShortFieldAnnotatedModel(Short objectShort) {
        this.objectShort = objectShort;
    }

    public ShortFieldAnnotatedModel(short rawShort) {
        this.rawShort = rawShort;
    }

    public Short getShort() {
        return objectShort;
    }

    public short getRawShort() {
        return rawShort;
    }
}
