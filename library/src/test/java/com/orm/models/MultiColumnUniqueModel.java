package com.orm.models;

import com.orm.SugarRecord;
import com.orm.dsl.MultiUnique;
import com.orm.dsl.Unique;

/**
 * Created by sibelius on 02/12/15.
 */
@MultiUnique("a, b")
public class MultiColumnUniqueModel extends SugarRecord {

    private int a;
    private int b;

    public MultiColumnUniqueModel() { }

    public MultiColumnUniqueModel(int a, int b) {
        this.a = a;
        this.b = b;
    }
}