package com.example.models;

import com.orm.dsl.PrimaryKey;
import com.orm.dsl.Table;

@Table
public class PrimaryKeyNotationBooleanFieldAnnotatedModel{
    private             Boolean objectBoolean;
    private             boolean rawBoolean;
    @PrimaryKey private Long    myId;

    public PrimaryKeyNotationBooleanFieldAnnotatedModel(){}

    public PrimaryKeyNotationBooleanFieldAnnotatedModel(Boolean objectBoolean){
        this.objectBoolean = objectBoolean;
    }

    public PrimaryKeyNotationBooleanFieldAnnotatedModel(boolean rawBoolean) {
        this.rawBoolean = rawBoolean;
    }

    public Boolean getBoolean() {
        return objectBoolean;
    }

    public boolean getRawBoolean() {
        return rawBoolean;
    }
}
