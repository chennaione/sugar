package com.example.models;

import com.orm.dsl.Table;

@Table
public class RelationshipAnnotatedModel {
    private SimpleAnnotatedModel simple;
    private Long id;

    public RelationshipAnnotatedModel() {}

    public RelationshipAnnotatedModel(SimpleAnnotatedModel simple) {
        this.simple = simple;
    }

    public SimpleAnnotatedModel getSimple() {
        return simple;
    }

    public Long getId() {
        return id;
    }
}
