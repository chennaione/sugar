package com.orm.model;

import com.orm.annotation.Table;

import java.math.BigDecimal;

@Table
public class BigDecimalFieldAnnotatedModel {
    private BigDecimal decimal;
    private Long id;

    public BigDecimalFieldAnnotatedModel() {}

    public BigDecimalFieldAnnotatedModel(BigDecimal decimal) {
        this.decimal = decimal;
    }

    public BigDecimal getBigDecimal() {
        return decimal;
    }

    public Long getId() {
        return id;
    }
}
