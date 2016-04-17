package com.orm;

import android.database.Cursor;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import static com.orm.util.ThreadUtil.*;

/**
 * SugarDataSource provides basic crud operations and simplifies SugarRecord by using callbacks and
 * performing Asynchronous execution to run queries.
 *
 * @author jonatan.salas
 */
@SuppressWarnings("all")
public final class SugarDataSource<T> {
    private final Class<T> sClass;

    /**
     *
     * @param tClass
     */
    private SugarDataSource(Class<T> tClass) {
        if (null == tClass) {
            throw new IllegalArgumentException("sClass shouldn't be null!");
        }

        this.sClass = tClass;
    }

    /**
     *
     * @param sClass
     * @param <T>
     * @return
     */
    public static <T> SugarDataSource<T> getInstance(Class<T> sClass) {
        return new SugarDataSource<>(sClass);
    }

    /**
     *
     * @param object
     * @param successCallback
     * @param errorCallback
     */
    public void insert(final T object, final SuccessCallback<Long> successCallback, final ErrorCallback errorCallback) {
        checkNotNull(successCallback);
        checkNotNull(errorCallback);
        checkNotNull(object);

        final Callable<Long> call = () -> { return SugarRecord.save(object); };
        final Future<Long> future = doInBackground(call);

        Long id = null;

        try {
            id = future.get();

            if (null == id) {
                errorCallback.onError(new Exception("Error when performing insert of " + object.toString()));
            } else {
                successCallback.onSuccess(id);
            }

        } catch (Exception e) {
            errorCallback.onError(e);
        }
    }

    /**
     *
     * @param id
     * @param successCallback
     * @param errorCallback
     */
    public void findById(final Long id, final SuccessCallback<T> successCallback, final ErrorCallback errorCallback) {
        checkNotNull(successCallback);
        checkNotNull(errorCallback);
        checkNotNull(id);

        final Callable<T> call = () -> { return SugarRecord.findById(getSugarClass(), id); };
        final Future<T> future = doInBackground(call);

        T object = null;

        try {
            object = future.get();

            if (null == object) {
                errorCallback.onError(new Exception("The object with " + id.toString() + "doesn't exist in database"));
            } else {
                successCallback.onSuccess(object);
            }

        } catch (Exception e) {
            errorCallback.onError(e);
        }
    }

    /**
     *
     * @param whereClause
     * @param whereArgs
     * @param groupBy
     * @param orderBy
     * @param limit
     * @param successCallback
     * @param errorCallback
     */
    public void query(String whereClause, String[] whereArgs, String groupBy, String orderBy, String limit, final SuccessCallback<Cursor> successCallback, final ErrorCallback errorCallback) {
        checkNotNull(successCallback);
        checkNotNull(errorCallback);

        final Callable<Cursor> call = () -> { return SugarRecord.getCursor(getSugarClass(), whereClause, whereArgs, groupBy, orderBy, limit); };
        final Future<Cursor> future = doInBackground(call);

        Cursor cursor = null;

        try {
            cursor = future.get();

            if (null == cursor) {
                errorCallback.onError(new Exception("Problem when trying to get the cursor"));
            } else {
                successCallback.onSuccess(cursor);
            }

        } catch (Exception e) {
            errorCallback.onError(e);
        }
    }

    /**
     *
     * @param orderBy
     * @param successCallback
     * @param errorCallback
     */
    public void listAll(final String orderBy, final SuccessCallback<List<T>> successCallback, final ErrorCallback errorCallback) {
        checkNotNull(successCallback);
        checkNotNull(errorCallback);

        final Callable<List<T>> call = () -> { return SugarRecord.listAll(getSugarClass(), orderBy); };
        final Future<List<T>> future = doInBackground(call);

        List<T> objects = null;

        try {
            objects = future.get();

            if (null == objects || objects.isEmpty()) {
                errorCallback.onError(new Exception("There are no objects in the database"));
            } else {
                successCallback.onSuccess(objects);
            }

        } catch (Exception e) {
            errorCallback.onError(e);
        }
    }


    /**
     *
     * @param object
     * @param successCallback
     * @param errorCallback
     */
    public void update(final T object, final SuccessCallback<Long> successCallback, final ErrorCallback errorCallback) {
        checkNotNull(successCallback);
        checkNotNull(errorCallback);
        checkNotNull(object);

        final Callable<Long> call = () -> { return SugarRecord.update(object); };
        final Future<Long> future = doInBackground(call);

        Long id = null;

        try {
            id = future.get();

            if (null == id) {
                errorCallback.onError(new Exception("Error when performing update of " + object.toString()));
            } else {
                successCallback.onSuccess(id);
            }

        } catch (Exception e) {
            errorCallback.onError(e);
        }
    }

    /**
     *
     * @param object
     * @param successCallback
     * @param errorCallback
     */
    public void delete(final T object, final SuccessCallback<Boolean> successCallback, final ErrorCallback errorCallback) {
        checkNotNull(successCallback);
        checkNotNull(errorCallback);
        checkNotNull(object);

        final Callable<Boolean> call = () -> { return SugarRecord.delete(object); };
        final Future<Boolean> future = doInBackground(call);

        Boolean isDeleted = null;

        try {
            isDeleted = future.get();

            if (null == isDeleted || !isDeleted) {
                errorCallback.onError(new Exception("Error when performing delete of " + object.toString()));
            } else {
                successCallback.onSuccess(isDeleted);
            }

        } catch (Exception e) {
            errorCallback.onError(e);
        }
    }

    /**
     *
     * @param whereClause
     * @param whereArgs
     * @param successCallback
     * @param errorCallback
     */
    public void delete(String whereClause, String[] whereArgs, final SuccessCallback<Integer> successCallback, final ErrorCallback errorCallback) {
        checkNotNull(successCallback);
        checkNotNull(errorCallback);

        final Callable<Integer> call = () -> { return SugarRecord.deleteAll(getSugarClass(), whereClause, whereArgs); };
        final Future<Integer> future = doInBackground(call);

        Integer count = null;

        try {
            count = future.get();

            if (null == count || count == 0) {
                errorCallback.onError(new Exception("Error when performing deleete of all elements"));
            } else {
                successCallback.onSuccess(count);
            }

        } catch (Exception e) {
            errorCallback.onError(e);
        }
    }

    /**
     *
     * @param successCallback
     * @param errorCallback
     */
    public void deleteAll(final SuccessCallback<Integer> successCallback, final ErrorCallback errorCallback) {
        delete(null, null, successCallback, errorCallback);
    }

    /**
     *
     * @param successCallback
     * @param errorCallback
     */
    public void count(final SuccessCallback<Long> successCallback, final ErrorCallback errorCallback) {
        checkNotNull(successCallback);
        checkNotNull(errorCallback);

        final Callable<Long> call = () -> { return SugarRecord.count(getSugarClass()); };
        final Future<Long> future = doInBackground(call);

        Long count = null;

        try {
            count = future.get();

            if (null == count) {
                errorCallback.onError(new Exception("Error when trying to get count"));
            } else {
                successCallback.onSuccess(count);
            }

        } catch (Exception e) {
            errorCallback.onError(e);
        }
    }

    /**
     *
     * @param object
     */
    private void checkNotNull(Object object) {
        if (null == object) {
            throw new IllegalArgumentException("object shouldn't be null");
        }
    }

    public Class<T> getSugarClass() {
        return sClass;
    }

    /**
     * @author jonatan.salas
     * @param <S>
     */
    public interface SuccessCallback<S> {

        /**
         *
         * @param object
         */
        void onSuccess(final S object);
    }

    /**
     * @author jonatan.salas
     */
    public interface ErrorCallback {

        /**
         *
         * @param e
         */
        void onError(final Exception e);
    }
}
