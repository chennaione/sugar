package com.example.models;

import com.orm.SugarRecord;
import com.orm.dsl.PrimaryKey;

public class PrimaryKeyNotationSimpleModel extends SugarRecord{
    @PrimaryKey
    private Long    myId;
    private String  str;
    private int     integer;
    private boolean bool;

    public String getStr(){
        return str;
    }

    public void setStr(String str){
        this.str = str;
    }

    public int getInteger(){
        return integer;
    }

    public void setInteger(int integer){
        this.integer = integer;
    }

    public boolean isBool(){
        return bool;
    }

    public void setBool(boolean bool){
        this.bool = bool;
    }

    public Long getMyId(){ return myId; }

    public void setMyId(Long myId){ this.myId = myId; }
}
