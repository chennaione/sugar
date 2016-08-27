package com.orm.model.onetomany;

import com.orm.SugarRecord;
import com.orm.annotation.OneToMany;

import java.util.List;

/**
 * Created by Łukasz Wesołowski on 28.07.2016.
 */
public class OneToManyModel extends SugarRecord {
    @OneToMany(targetField = "model")
    private List<OneToManyRelationModel> models;

    public OneToManyModel() {
    }

    public OneToManyModel(Long id) {
        setId(id);
    }

    public List<OneToManyRelationModel> getModels() {
        return models;
    }

    public void setModels(List<OneToManyRelationModel> models) {
        this.models = models;
    }
}
