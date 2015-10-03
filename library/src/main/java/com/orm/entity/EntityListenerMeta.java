package com.orm.entity;

import java.lang.reflect.Method;

public class EntityListenerMeta {

    private final Class entityClass;

    private final Object listener;

    private Method prePersist;
    private Method preRemove;
    private Method postPersist;
    private Method postRemove;

    public EntityListenerMeta(Class entityClass, Class listenerClass) throws IllegalAccessException, InstantiationException {
        this.entityClass = entityClass;
        this.listener = listenerClass.newInstance();
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public Object getListener() {
        return listener;
    }

    public Method getPrePersist() {
        return prePersist;
    }

    public void setPrePersist(Method prePersist) {
        this.prePersist = prePersist;
    }

    public Method getPreRemove() {
        return preRemove;
    }

    public void setPreRemove(Method preRemove) {
        this.preRemove = preRemove;
    }

    public Method getPostPersist() {
        return postPersist;
    }

    public void setPostPersist(Method postPersist) {
        this.postPersist = postPersist;
    }

    public Method getPostRemove() {
        return postRemove;
    }

    public void setPostRemove(Method postRemove) {
        this.postRemove = postRemove;
    }
}
