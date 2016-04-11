package com.orm.model;

import com.orm.annotation.Column;
import com.orm.annotation.Ignore;
import com.orm.annotation.NotNull;
import com.orm.annotation.Table;
import com.orm.annotation.Unique;

/**
 * @author jonatan.salas
 */
@Table
public class AllAnotatedModel {

    @NotNull @Unique
    private Long id;

    @Column(notNull = true, name = "name", unique = true)
    private String name;

    @Ignore
    private String surname;

    public AllAnotatedModel() { }
}
