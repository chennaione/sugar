package com.orm.model.onetomany;

import com.orm.SugarRecord;

/**
 * Created by Łukasz Wesołowski on 27.08.2016.
 */
public class WithoutOneToManyAnnotationRelationModel extends SugarRecord {
    private WithoutOneToManyAnnotationModel model;

    public WithoutOneToManyAnnotationRelationModel() {
    }

    public WithoutOneToManyAnnotationRelationModel(Long id, WithoutOneToManyAnnotationModel model) {
        setId(id);
        this.model = model;
    }

    public WithoutOneToManyAnnotationModel getModel() {
        return model;
    }

    public void setModel(WithoutOneToManyAnnotationModel model) {
        this.model = model;
    }
}
