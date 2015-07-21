package com.example.models;

import com.orm.dsl.Table;

@Table
public class ByteArrayAnnotatedModel {
    private byte[] byteArray;
    private Long id;

    public ByteArrayAnnotatedModel() {}

    public ByteArrayAnnotatedModel(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public Long getId() {
        return id;
    }
}
