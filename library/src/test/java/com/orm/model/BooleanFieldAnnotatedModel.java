package com.orm.model;

import com.orm.annotation.Table;

@Table
public class BooleanFieldAnnotatedModel {
    private Boolean objectBoolean;
    private boolean rawBoolean;
    private Long id;

    public BooleanFieldAnnotatedModel() {}

    public BooleanFieldAnnotatedModel(Boolean objectBoolean) {
        this.objectBoolean = objectBoolean;
    }

    public BooleanFieldAnnotatedModel(boolean rawBoolean) {
        this.rawBoolean = rawBoolean;
    }

    public Boolean getBoolean() {
        return objectBoolean;
    }

    public boolean getRawBoolean() {
        return rawBoolean;
    }
}
