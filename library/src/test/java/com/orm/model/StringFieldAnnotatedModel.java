package com.orm.model;

import com.orm.annotation.Table;

@Table
public class StringFieldAnnotatedModel {
    private String string;
    private Long id;

    public StringFieldAnnotatedModel() {}

    public StringFieldAnnotatedModel(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
