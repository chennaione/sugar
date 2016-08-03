package com.orm.model;

import com.orm.SugarRecord;

/**
 * Created by Łukasz Wesołowski on 28.07.2016.
 */
public class ManyToOneModel extends SugarRecord {
    private String name;
    private OneToManyModel model;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OneToManyModel getModel() {
        return model;
    }

    public void setModel(OneToManyModel model) {
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ManyToOneModel that = (ManyToOneModel) o;

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
        return "ManyToOneModel{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", model=" + model +
                '}';
    }
}
