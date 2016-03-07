package com.orm.serializer;

import android.content.Context;
import android.util.Log;

import com.google.common.collect.MapMaker;
import com.orm.util.ReflectionUtil;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class EntitySerializerManager {

    private Context context;
    private ConcurrentMap<Class<?>, EntitySerializer> serializers;

    public EntitySerializerManager(Context context) {
        this.context = context;
        this.serializers = new MapMaker().weakKeys().makeMap();
        init();
    }

    public <K, V> void addSerializer(Class<? extends K> cls, EntitySerializer<K, V> serializer) {
        serializers.put(cls, serializer);
    }

    @SuppressWarnings("unchecked")
    public <K, V> EntitySerializer<K, V> get(Class<? extends K> cls) {
        return serializers.get(cls);
    }

    private void init() {
        List<Class<? extends EntitySerializer>> serializerClasses = ReflectionUtil.getSerializerClasses(context);

        for (Class<? extends EntitySerializer> className : serializerClasses) {
            try {
                EntitySerializer entitySerializer = className.newInstance();
                serializers.put(entitySerializer.getDeserializedType(), entitySerializer);
            } catch (InstantiationException e) {
                Log.e("Sugar", e.getMessage());
            } catch (IllegalAccessException e) {
                Log.e("Sugar", e.getMessage());
            }
        }
    }
}
