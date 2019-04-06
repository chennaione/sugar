package com.orm.model;

import com.orm.SugarRecord;

public class TestRecord extends SugarRecord {

    private String name;

    public TestRecord() {
        super();
    }

    public TestRecord setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }
}
