package com.orm.model;

import com.orm.SugarRecord;

public class SimpleModel extends SugarRecord {
    private String str;
    private int integer;
    private boolean bool;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getInteger() {
        return integer;
    }

    public void setInteger(int integer) {
        this.integer = integer;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }
}
