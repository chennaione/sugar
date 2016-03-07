package com.orm.serializer;

public interface EntitySerializer<K, V> {

    Class getDeserializedType();

    Class getSerializedType();

    V serialize(K object);

    K deserialize(V object);
}
