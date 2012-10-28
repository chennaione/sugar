package com.example;

import android.content.Context;
import com.orm.SugarRecord;

public class Tag extends SugarRecord<Tag>{
    private String name;

    public Tag(Context context, String name) {
        super(context);
        this.name = name;
    }

    public Tag(Context context) {
        super(context);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
