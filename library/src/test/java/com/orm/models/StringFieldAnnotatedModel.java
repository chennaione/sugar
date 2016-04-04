package com.orm.models;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

/**
 * Created by sibelius on 02/12/15.
 */
@Table
public class StringFieldAnnotatedModel extends SugarRecord {
    public String name;

    public StringFieldAnnotatedModel() { }

    public StringFieldAnnotatedModel(String name) {
        this.name = name;
    }
}
