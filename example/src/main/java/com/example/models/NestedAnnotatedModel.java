package com.example.models;

import com.orm.dsl.Table;

@Table
public class NestedAnnotatedModel {
    private RelationshipAnnotatedModel nested;
    private Long id;

    public NestedAnnotatedModel() {}

    public NestedAnnotatedModel(RelationshipAnnotatedModel nested) {
        this.nested = nested;
    }

    public RelationshipAnnotatedModel getNested() {
        return nested;
    }

    public Long getId() {
        return id;
    }
}
