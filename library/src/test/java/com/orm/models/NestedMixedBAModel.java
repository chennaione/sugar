package com.orm.models;


import com.orm.annotation.Table;

@Table
public class NestedMixedBAModel {
    private RelationshipMixedAModel nested;
    private Long id;

    public NestedMixedBAModel() {}

    public NestedMixedBAModel(RelationshipMixedAModel nested) {
        this.nested = nested;
    }

    public RelationshipMixedAModel getNested() {
        return nested;
    }

    public Long getId() {
        return id;
    }
}
