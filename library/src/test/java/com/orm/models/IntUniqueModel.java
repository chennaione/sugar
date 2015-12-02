package com.orm.models;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by sibelius on 02/12/15.
 */
public class IntUniqueModel extends SugarRecord {
    @Unique
    private int value;

    public IntUniqueModel() {

    }

    public IntUniqueModel(int value) {
        this.value = value;
    }
}
