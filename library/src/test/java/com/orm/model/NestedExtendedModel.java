package com.orm.model;


import com.orm.SugarRecord;

public class NestedExtendedModel extends SugarRecord {
    private RelationshipExtendedModel nested;

    public NestedExtendedModel() {}

    public NestedExtendedModel(RelationshipExtendedModel nested) {
        this.nested = nested;
    }

    public RelationshipExtendedModel getNested() {
        return nested;
    }
}
