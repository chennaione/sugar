package com.orm.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SugarConfig {

    static Map<Class<?>, List<Field>> fields = new HashMap<Class<?>, List<Field>>();

    public static void setFields(Class<?> clazz, List<Field> fieldz) {
         fields.put(clazz, fieldz);
    }

    public static List<Field> getFields(Class<?> clazz) {
        if (fields.containsKey(clazz)) {
            List<Field> list = fields.get(clazz);
            return Collections.synchronizedList(list);
        }

        return null;
    }

    public static void clearCache() {
        fields.clear();
        fields = new HashMap<Class<?>, List<Field>>();
    }

}
