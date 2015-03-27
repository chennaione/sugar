package com.orm.query;

import com.orm.SugarRecord;

public class Condition {

    private String property;
    private Object value;
    private Check check;

    enum Check {
        EQUALS(" = "),
        GREATER_THAN(" > "),
        LESSER_THAN(" < "),
        NOT_EQUALS (" != "),
        LIKE(" LIKE "),
        NOT_LIKE(" NOT LIKE ");

        private String symbol;

        Check(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    enum Type {
        AND,
        OR,
        NOT
    }

    public Condition(String property) {
        this.property = property;
    }

    public static Condition prop(String property) {
        return new Condition(property);
    }

    public Condition eq(Object value) {
        setValue(value);
        check = Check.EQUALS;
        return this;
    }

    public Condition like(Object value) {
        setValue(value);
        check = Check.LIKE;
        return this;
    }

    public Condition notLike(Object value) {
        setValue(value);
        check = Check.NOT_LIKE;
        return this;
    }

    public Condition notEq(Object value) {
        setValue(value);
        check = Check.NOT_EQUALS;
        return this;
    }

    public Condition gt(Object value) {
        setValue(value);
        check = Check.GREATER_THAN;
        return this;
    }

    public Condition lt(Object value) {
        setValue(value);
        check = Check.LESSER_THAN;
        return this;
    }

    public String getProperty() {
        return property;
    }

    public Object getValue() {
        return value;
    }

    public Check getCheck() {
        return check;
    }

    public String getCheckSymbol() {
        return check.getSymbol();
    }

    private void setValue(Object value) {
        if (value instanceof SugarRecord) {
            this.value = ((SugarRecord)value).getId();
        } else {
            this.value = value;
        }
    }

}
