package com.example.models;

import com.orm.SugarRecord;

public class EnumModel extends SugarRecord {
    public static enum DefaultEnum {
        ONE, TWO
    }

    public static enum OverrideEnum {
        ONE, TWO;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    private OverrideEnum overrideEnum;
    private DefaultEnum defaultEnum;

    public EnumModel() {

    }

    public EnumModel(OverrideEnum e1, DefaultEnum d1) {
        overrideEnum = e1;
        defaultEnum = d1;
    }

    public DefaultEnum getDefaultEnum() {
        return defaultEnum;
    }

    public void setDefaultEnum(DefaultEnum defaultEnum) {
        this.defaultEnum = defaultEnum;
    }

    public void setOverrideEnum(OverrideEnum overrideEnum) {
        this.overrideEnum = overrideEnum;
    }

    public OverrideEnum getOverrideEnum() {
        return overrideEnum;
    }
}
