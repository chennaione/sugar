package com.example.models;


import com.orm.dsl.Table;

@Table
public class RelationshipMixedBModel {
    private SimpleExtendedModel simple;
    private Long id;

    public RelationshipMixedBModel() {}

    public RelationshipMixedBModel(SimpleExtendedModel simple) {
        this.simple = simple;
    }

    public SimpleExtendedModel getSimple() {
        return simple;
    }

    public Long getId() {
        return id;
    }
}
