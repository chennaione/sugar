package com.example;

import com.orm.dsl.Table;

@Table(name = "text_note")
public class TextNote extends Note {

    public String desc;

    public TextNote() {
    }


}
