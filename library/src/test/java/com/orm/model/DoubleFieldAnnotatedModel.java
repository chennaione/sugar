package com.orm.model;

import com.orm.annotation.Table;

@Table
public class DoubleFieldAnnotatedModel {
    private Double objectDouble;
    private double rawDouble;
    private Long id;

    public DoubleFieldAnnotatedModel() {}

    public DoubleFieldAnnotatedModel(Double objectDouble) {
        this.objectDouble = objectDouble;
    }

    public DoubleFieldAnnotatedModel(double rawDouble) {
        this.rawDouble = rawDouble;
    }

    public Double getDouble() {
        return objectDouble;
    }

    public double getRawDouble() {
        return rawDouble;
    }
}
