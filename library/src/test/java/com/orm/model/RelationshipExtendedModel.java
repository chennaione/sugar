package com.orm.model;


import com.orm.SugarRecord;

public class RelationshipExtendedModel extends SugarRecord {
    private SimpleExtendedModel simple;

    public RelationshipExtendedModel() {}

    public RelationshipExtendedModel(SimpleExtendedModel simple) {
        this.simple = simple;
    }

    public SimpleExtendedModel getSimple() {
        return simple;
    }
}
