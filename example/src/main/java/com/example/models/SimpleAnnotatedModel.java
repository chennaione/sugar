package com.example.models;

import com.orm.dsl.Table;


@Table
public class SimpleAnnotatedModel {
    private Long id;

    public SimpleAnnotatedModel() {}

    public Long getId() {
        return id;
    }
}
