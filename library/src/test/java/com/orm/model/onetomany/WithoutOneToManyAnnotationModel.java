package com.orm.model.onetomany;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by Łukasz Wesołowski on 27.08.2016.
 */
public class WithoutOneToManyAnnotationModel extends SugarRecord {
    private List<OneToManyRelationModel> models;

    public WithoutOneToManyAnnotationModel() {
    }

    public WithoutOneToManyAnnotationModel(Long id) {
        setId(id);
    }

    public List<OneToManyRelationModel> getModels() {
        return models;
    }

    public void setModels(List<OneToManyRelationModel> models) {
        this.models = models;
    }
}
