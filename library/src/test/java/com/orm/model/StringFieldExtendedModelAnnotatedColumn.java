package com.orm.model;

import com.orm.SugarRecord;
import com.orm.annotation.Column;

/**
 * Created by sibelius on 02/12/15.
 */
public class StringFieldExtendedModelAnnotatedColumn extends SugarRecord {
    @Column(name="anyName")
    public String name;

    public StringFieldExtendedModelAnnotatedColumn() { }

    public StringFieldExtendedModelAnnotatedColumn(String name) {
        this.name = name;
    }
}
