package com.orm.model;


import com.orm.SugarRecord;

public class NestedMixedABModel extends SugarRecord {
    private RelationshipMixedBModel nested;

    public NestedMixedABModel() {}

    public NestedMixedABModel(RelationshipMixedBModel nested) {
        this.nested = nested;
    }

    public RelationshipMixedBModel getNested() {
        return nested;
    }
}
