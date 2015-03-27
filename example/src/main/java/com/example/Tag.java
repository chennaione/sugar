package com.example;

import com.orm.dsl.Table;

@Table(name = "tag")
public class Tag {
    private String name;

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
