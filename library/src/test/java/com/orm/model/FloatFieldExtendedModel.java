package com.orm.model;

import com.orm.SugarRecord;

public class FloatFieldExtendedModel extends SugarRecord {
    private Float objectFloat;
    private float rawFloat;

    public FloatFieldExtendedModel() {}

    public FloatFieldExtendedModel(Float objectFloat) {
        this.objectFloat = objectFloat;
    }

    public FloatFieldExtendedModel(float rawFloat) {
        this.rawFloat = rawFloat;
    }

    public Float getFloat() {
        return objectFloat;
    }

    public float getRawFloat() {
        return rawFloat;
    }
}
