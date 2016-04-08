package com.orm.models;

import com.orm.SugarRecord;

public class BooleanFieldExtendedModel extends SugarRecord {
    private Boolean objectBoolean;
    private boolean rawBoolean;

    public BooleanFieldExtendedModel() {}

    public BooleanFieldExtendedModel(Boolean objectBoolean) {
        this.objectBoolean = objectBoolean;
    }

    public BooleanFieldExtendedModel(boolean rawBoolean) {
        this.rawBoolean = rawBoolean;
    }

    public Boolean getBoolean() {
        return objectBoolean;
    }

    public boolean getRawBoolean() {
        return rawBoolean;
    }
}
