package com.orm.model;

import com.orm.annotation.Table;

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
