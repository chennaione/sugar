package com.orm.model;

import com.orm.SugarRecord;

public class ByteArrayExtendedModel extends SugarRecord {
    private byte[] byteArray;

    public ByteArrayExtendedModel() {}

    public ByteArrayExtendedModel(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    public byte[] getByteArray() {
        return byteArray;
    }
}
