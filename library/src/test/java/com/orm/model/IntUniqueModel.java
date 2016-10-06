package com.orm.model;

import com.orm.SugarRecord;
import com.orm.annotation.Unique;

/**
 * Created by sibelius on 02/12/15.
 */
public class IntUniqueModel extends SugarRecord {

    @Unique
    private int value;

    public IntUniqueModel() { }

    public IntUniqueModel(int value) {
        this.value = value;
    }
}
