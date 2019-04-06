package com.orm.model;

import com.orm.SugarRecord;

import java.math.BigDecimal;

public class BigDecimalFieldExtendedModel extends SugarRecord {
    private BigDecimal decimal;

    public BigDecimalFieldExtendedModel() {}

    public BigDecimalFieldExtendedModel(BigDecimal decimal) {
        this.decimal = decimal;
    }

    public BigDecimal getBigDecimal() {
        return decimal;
    }
}
