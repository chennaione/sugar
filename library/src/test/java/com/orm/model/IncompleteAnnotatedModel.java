package com.orm.model;

import com.orm.annotation.Table;

@Table
public class IncompleteAnnotatedModel {
    // An annotated model must provide a Long id field. A setter or getter is optional
    public IncompleteAnnotatedModel() {}
}
