package com.orm;

import static com.orm.util.ManifestHelper.getDatabaseVersion;
import static com.orm.util.ManifestHelper.getDebugEnabled;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.orm.util.ManifestHelper;
import com.orm.util.SugarCursorFactory;

public class SugarDb extends SQLiteOpenHelper {

    private final SchemaGenerator schemaGenerator;
    private SQLiteDatabase sqLiteDatabase;
    private ObjectCache objectCache;

    public SugarDb(Context context) {
        super(context, ManifestHelper.getDatabaseName(context),
                new SugarCursorFactory(getDebugEnabled(context)), getDatabaseVersion(context));
        schemaGenerator = new SchemaGenerator(context);
        enableObjectCache(ManifestHelper.getObjectCacheEnabled(context));
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        schemaGenerator.createDatabase(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        schemaGenerator.doUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }

    public synchronized SQLiteDatabase getDB() {
        if (this.sqLiteDatabase == null) {
            this.sqLiteDatabase = getWritableDatabase();
        }

        return this.sqLiteDatabase;
    }

    /**
     * Enable or disable the object cache for this database.
     * 
     * Upon disabling, the cache is cleared
     * 
     * @param enable true to enable the cache
     */
    public void enableObjectCache(boolean enable) {
    	if (enable) {
			if (objectCache == null) {
				objectCache = ObjectCache.makeWeakCache();
			}
		} else {
			if (objectCache != null) {
				objectCache.clearAll();
				objectCache = null;
			}
		}
    }
    
    /**
     * Read the object with the specified class and id from the cache
     * 
     * @param clazz
     * @param id
     * @return the object if it exists or null otherwise
     */
    public <T,ID> T getFromCache(Class<T> clazz, ID id) {
    	if (objectCache != null && id != null) return objectCache.get(clazz, id);
    	return null;
    }
    
    /**
     * Put the record in cache if it has been stored in  the database and the cache is
     * enabled
     * 
     * @param clazz
     * @param id record id
     * @param record sugar record
     */
    public <T extends Object,ID> void putInCache(Class<T> clazz, ID id, T record) {
    	if (objectCache == null) return;
    	if (record == null || id == null) return;
    	if (!objectCache.isClassRegistered(clazz)) objectCache.registerClass(clazz);
    	objectCache.put(clazz, id, record);
    }
    
    /**
     * Remove the record with the specified id and class from the cache
     * 
     * @param clazz
     * @param id
     */
    public <T,ID> void removeFromCache(Class<T> clazz, ID id) {
    	if (objectCache == null || id == null) return;
    	objectCache.remove(clazz, id);
    }
    
    /**
     * Remove all records from the specified class from the cache
     * @param clazz
     */
    public <T> void removeFromCache(Class<T> clazz) {
    	if (objectCache == null) return;
    	objectCache.clear(clazz);
    }
}
