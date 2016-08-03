package com.orm.model;

import com.orm.SugarRecord;
import com.orm.annotation.OneToMany;

import java.util.List;

/**
 * Created by Łukasz Wesołowski on 28.07.2016.
 */
public class OneToManyModel extends SugarRecord {
    private String name;
    @OneToMany(targetField = "model")
    private List<ManyToOneModel> models;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ManyToOneModel> getModels() {
        return models;
    }

    public void setModels(List<ManyToOneModel> models) {
        this.models = models;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OneToManyModel that = (OneToManyModel) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OneToManyModel{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", models=" + models +
                '}';
    }
}
