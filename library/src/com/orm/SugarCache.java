package com.orm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.LruCache;

@SuppressLint("NewApi")
@SuppressWarnings("unused")
public final class SugarCache {
	public static final int DEFAULT_CACHE_SIZE = 1024;
	private static Context sContext;
	private static SugarDb sSugarDb;
	private static LruCache<String, SugarRecord<?>> sEntities;
	private static boolean sIsInitialized = false;
	
	public static synchronized void initialize(SugarDb sugarDb) {
		if (sIsInitialized) {
			return;
		}
		sSugarDb = sugarDb;
		sContext = sugarDb.getContext();
		sEntities = new LruCache<String, SugarRecord<?>>(DEFAULT_CACHE_SIZE);
		sIsInitialized = true;
	}
	
	public static synchronized void clear() {
		sEntities.evictAll();
	}

	public static synchronized void dispose() {
		sEntities = null;
		sIsInitialized = false;
	}

	public static String getIdentifier(Class<? extends SugarRecord<?>> type, Long id) {
		return SugarRecord.getTableName(type) + "@" + id;
	}

	@SuppressWarnings("unchecked")
	public static String getIdentifier(SugarRecord<?> entity) {
		return getIdentifier((Class<? extends SugarRecord<?>>) entity.getClass(), entity.getId());
	}

	public static synchronized void addEntity(SugarRecord<?> entity) {
		sEntities.put(getIdentifier(entity), entity);
	}

	public static synchronized SugarRecord<?> getEntity(Class<? extends SugarRecord<?>> type, long id) {
		return sEntities.get(getIdentifier(type, id));
	}

	public static synchronized void removeEntity(SugarRecord<?> entity) {
		sEntities.remove(getIdentifier(entity));
	}
}
