package com.example.models;

import com.orm.dsl.Table;

@Table(name = "new_note")
public class NewNote {
    public long id;
    public String name;
}
