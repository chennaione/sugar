package com.example.models;

import com.orm.dsl.PrimaryKey;
import com.orm.dsl.Table;

import java.math.BigDecimal;

@Table
public class PrimaryKeyNotationBigDecimalFieldAnnotatedModel{

    @PrimaryKey
    private Long myId;
    private BigDecimal decimal;

    public PrimaryKeyNotationBigDecimalFieldAnnotatedModel() {}

    public PrimaryKeyNotationBigDecimalFieldAnnotatedModel(BigDecimal decimal) {
        this.decimal = decimal;
    }

    public BigDecimal getBigDecimal() {
        return decimal;
    }

    public Long getMyId() {
        return myId;
    }
}
