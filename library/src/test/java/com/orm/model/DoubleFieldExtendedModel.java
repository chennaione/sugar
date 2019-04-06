package com.orm.model;

import com.orm.SugarRecord;

public class DoubleFieldExtendedModel extends SugarRecord {
    private Double objectDouble;
    private double rawDouble;

    public DoubleFieldExtendedModel() {}

    public DoubleFieldExtendedModel(Double objectDouble) {
        this.objectDouble = objectDouble;
    }

    public DoubleFieldExtendedModel(double rawDouble) {
        this.rawDouble = rawDouble;
    }

    public Double getDouble() {
        return objectDouble;
    }

    public double getRawDouble() {
        return rawDouble;
    }
}
