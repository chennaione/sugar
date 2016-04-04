package com.orm.models;

import com.orm.SugarRecord;

/**
 * Created by sibelius on 02/12/15.
 */
public class StringFieldExtendedModel extends SugarRecord {
    public String name;

    public StringFieldExtendedModel() { }

    public StringFieldExtendedModel(String name) {
        this.name = name;
    }
}
