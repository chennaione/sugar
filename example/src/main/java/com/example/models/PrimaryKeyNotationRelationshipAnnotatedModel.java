package com.example.models;

import com.orm.dsl.PrimaryKey;
import com.orm.dsl.Table;

@Table
public class PrimaryKeyNotationRelationshipAnnotatedModel{
    private             SimpleAnnotatedModel simple;
    @PrimaryKey private Long                 myId;

    public PrimaryKeyNotationRelationshipAnnotatedModel(){}

    public PrimaryKeyNotationRelationshipAnnotatedModel(SimpleAnnotatedModel simple){
        this.simple = simple;
    }

    public SimpleAnnotatedModel getSimple(){
        return simple;
    }

    public Long getMyId(){
        return myId;
    }
}
