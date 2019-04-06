package com.orm.model;

import com.orm.annotation.Table;

@Table
public class FloatFieldAnnotatedModel {
    private Float objectFloat;
    private float rawFloat;
    private Long id;

    public FloatFieldAnnotatedModel() {}

    public FloatFieldAnnotatedModel(Float objectFloat) {
        this.objectFloat = objectFloat;
    }

    public FloatFieldAnnotatedModel(float rawFloat) {
        this.rawFloat = rawFloat;
    }

    public Float getFloat() {
        return objectFloat;
    }

    public float getRawFloat() {
        return rawFloat;
    }
}
