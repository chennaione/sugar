package com.example.models;


import com.orm.SugarRecord;
import com.orm.dsl.PrimaryKey;

public class PrimaryKeyNotationRelationshipExtendedModel extends SugarRecord{

    @PrimaryKey private Long myId;

    private SimpleExtendedModel simple;

    public PrimaryKeyNotationRelationshipExtendedModel(){}

    public PrimaryKeyNotationRelationshipExtendedModel(SimpleExtendedModel simple){
        this.simple = simple;
    }

    public SimpleExtendedModel getSimple(){
        return simple;
    }

    public Long getMyId(){
        return myId;
    }
}
