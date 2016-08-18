package com.orm.model;

import com.orm.SugarRecord;
import com.orm.annotation.OneToMany;

import java.util.List;

/**
 * Created by Łukasz Wesołowski on 28.07.2016.
 */
public class OneToManyModel extends SugarRecord {
    Long id;
    @OneToMany(targetField = "model")
    private List<ManyToOneModel> models;

    public OneToManyModel() {
    }

    public OneToManyModel(Long id) {
        setId(id);
    }

    public List<ManyToOneModel> getModels() {
        return models;
    }

    public void setModels(List<ManyToOneModel> models) {
        this.models = models;
    }
}
