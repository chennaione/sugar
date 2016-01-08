package com.example.models;

import com.orm.dsl.PrimaryKey;
import com.orm.dsl.Table;

@Table
public class PrimaryKeyNotationSimpleAnnotatedModel{

    @PrimaryKey private Long myId;

    public PrimaryKeyNotationSimpleAnnotatedModel(){}

    public Long getMyId(){
        return myId;
    }
}
