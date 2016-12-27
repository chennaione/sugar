package com.orm.model;


import com.orm.annotation.Table;

@Table
public class NestedMixedBBModel {
    private RelationshipMixedBModel nested;
    private Long id;

    public NestedMixedBBModel() {}

    public NestedMixedBBModel(RelationshipMixedBModel nested) {
        this.nested = nested;
    }

    public RelationshipMixedBModel getNested() {
        return nested;
    }

    public Long getId() {
        return id;
    }
}
