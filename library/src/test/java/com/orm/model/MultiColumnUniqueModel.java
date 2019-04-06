package com.orm.model;

import com.orm.SugarRecord;
import com.orm.annotation.MultiUnique;

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