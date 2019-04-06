package com.orm.model;


import com.orm.SugarRecord;

public class RelationshipMixedAModel extends SugarRecord {
    private SimpleAnnotatedModel simple;

    public RelationshipMixedAModel() {}

    public RelationshipMixedAModel(SimpleAnnotatedModel simple) {
        this.simple = simple;
    }

    public SimpleAnnotatedModel getSimple() {
        return simple;
    }
}
