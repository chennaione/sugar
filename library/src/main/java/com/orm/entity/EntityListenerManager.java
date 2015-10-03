package com.orm.entity;

import android.content.Context;
import android.util.Log;

import com.google.common.collect.Lists;
import com.orm.entity.annotation.EntityListeners;
import com.orm.entity.annotation.PostPersist;
import com.orm.entity.annotation.PostRemove;
import com.orm.entity.annotation.PrePersist;
import com.orm.entity.annotation.PreRemove;
import com.orm.util.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityListenerManager {

    private Map<Class, List<EntityListenerMeta>> entityListenerMetaMap;

    private Context context;

    public EntityListenerManager(Context context) {
        this.context = context;
        load();
    }

    public void notify(Object entity, Class listenerType) {
        List<EntityListenerMeta> entityListenerMetas = entityListenerMetaMap.get(entity.getClass());

        if (entityListenerMetas != null && !entityListenerMetas.isEmpty()) {
            for (EntityListenerMeta meta : entityListenerMetas) {
                notify(entity, listenerType, meta);
            }
        }
    }

    private void notify(Object entity, Class listenerType, EntityListenerMeta meta) {
        if (listenerType == PrePersist.class && meta.getPrePersist() != null) {
            execute(meta.getListener(), meta.getPrePersist(), entity);
        } else if (listenerType == PreRemove.class && meta.getPreRemove() != null) {
            execute(meta.getListener(), meta.getPreRemove(), entity);
        } else if (listenerType == PostPersist.class && meta.getPostPersist() != null) {
            execute(meta.getListener(), meta.getPostPersist(), entity);
        } else if (listenerType == PostRemove.class && meta.getPostRemove() != null) {
            execute(meta.getListener(), meta.getPostRemove(), entity);
        }
    }

    private void execute(Object obj, Method method, Object entity) {
        try {
            method.invoke(obj, entity);
        } catch (IllegalAccessException e) {
            Log.w("Sugar", "Cannot invoke method '" + method.getName() + "' with entity '" + entity.getClass() + "'");
        } catch (InvocationTargetException e) {
            Log.w("Sugar", "Cannot invoke method '" + method.getName() + "' with entity '" + entity.getClass() + "'");
        }
    }

    private void load() {
        entityListenerMetaMap = new HashMap<>();

        List<Class> domainClasses = ReflectionUtil.getDomainClasses(context);

        for (Class domainClass : domainClasses) {
            processDomainClass(domainClass);
        }
    }

    private void processDomainClass(Class domainClass) {
        EntityListeners annotation = (EntityListeners) domainClass.getAnnotation(EntityListeners.class);

        if (annotation != null) {
            Class[] listenerClasses = annotation.value();

            if (listenerClasses != null && listenerClasses.length > 0) {
                List<EntityListenerMeta> metaList = processListenerClasses(domainClass, listenerClasses);

                if (metaList != null && !metaList.isEmpty()) {
                    entityListenerMetaMap.put(domainClass, metaList);
                }
            }
        }
    }

    private List<EntityListenerMeta> processListenerClasses(Class domainClass, Class[] listenerClasses) {
        List<EntityListenerMeta> metaList = Lists.newArrayList();

        for (Class listenerClass : listenerClasses) {
            Log.i("Sugar", "Found EntityListener for domain class '" + domainClass + "': " + listenerClass);

            EntityListenerMeta meta = processListenerClass(domainClass, listenerClass);
            if (meta != null) {
                metaList.add(meta);
            }
        }

        return metaList;
    }

    private EntityListenerMeta processListenerClass(Class domainClass, Class listenerClass) {
        try {
            EntityListenerMeta meta = new EntityListenerMeta(context, domainClass, listenerClass);
            meta.setPrePersist(findMethod(listenerClass, PrePersist.class));
            meta.setPreRemove(findMethod(listenerClass, PreRemove.class));
            meta.setPostPersist(findMethod(listenerClass, PostPersist.class));
            meta.setPostRemove(findMethod(listenerClass, PostRemove.class));

            return meta;
        } catch (IllegalAccessException e) {
            Log.e("Sugar", "Unable to instantiate EntityListener of class: " + listenerClass, e);
        } catch (InstantiationException e) {
            Log.e("Sugar", "Unable to instantiate EntityListener of class: " + listenerClass, e);
        } catch (NoSuchMethodException e) {
            Log.e("Sugar", "Unable to instantiate EntityListener of class: " + listenerClass, e);
        } catch (InvocationTargetException e) {
            Log.e("Sugar", "Unable to instantiate EntityListener of class: " + listenerClass, e);
        }

        return null;
    }

    private Method findMethod(Class source, Class annotation) {
        for (Method method : source.getMethods()) {
            if (method.getAnnotation(annotation) != null) {
                return method;
            }
        }

        return null;
    }
}
