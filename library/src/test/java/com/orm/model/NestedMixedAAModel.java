package com.orm.model;


import com.orm.SugarRecord;

public class NestedMixedAAModel extends SugarRecord {
    private RelationshipMixedAModel nested;

    public NestedMixedAAModel() {}

    public NestedMixedAAModel(RelationshipMixedAModel nested) {
        this.nested = nested;
    }

    public RelationshipMixedAModel getNested() {
        return nested;
    }
}
