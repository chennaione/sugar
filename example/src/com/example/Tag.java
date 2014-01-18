package com.example;

import android.content.Context;
import com.orm.SugarRecord;

public class Tag extends SugarRecord<Tag>{
    private String name;

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
