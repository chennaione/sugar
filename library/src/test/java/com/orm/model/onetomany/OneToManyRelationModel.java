package com.orm.model.onetomany;

import com.orm.SugarRecord;

/**
 * Created by Łukasz Wesołowski on 28.07.2016.
 */
public class OneToManyRelationModel extends SugarRecord {
    private OneToManyModel model;

    public OneToManyRelationModel() {
    }

    public OneToManyRelationModel(Long id, OneToManyModel model) {
        setId(id);
        this.model = model;
    }

    public OneToManyModel getModel() {
        return model;
    }

    public void setModel(OneToManyModel model) {
        this.model = model;
    }
}
