package com.example.models;


import com.orm.dsl.Table;

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
