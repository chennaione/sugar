package com.orm;

import android.database.Cursor;

import java.util.ArrayList;
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
     * SugarDataSource constructor with params
     *
     * @param tClass class argument used then to run SugarRecord class queries
     */
    private SugarDataSource(Class<T> tClass) {
        if (null == tClass) {
            throw new IllegalArgumentException("sClass shouldn't be null!");
        }

        this.sClass = tClass;
    }

    /**
     * SugarDataSource static method to construct an Instance of this class.
     *
     * @param sClass class argument used then to run SugarRecord class queries
     * @param <T> generic argument that must be a SugarRecord extended class or @Table annotated class
     * @return an instance of SugarDataSource
     */
    public static <T> SugarDataSource<T> getInstance(Class<T> sClass) {
        return new SugarDataSource<>(sClass);
    }

    /**
     * Method used to perform an Asynchronous insert. It works on top of SugarRecord class, executes the
     * insert query using Futures.
     *
     * @param object the object you want to insert. It must be a SugarRecord extended class or @Table annotated class
     * @param successCallback the callback for a successful insert operation
     * @param errorCallback the callback for an error in insert operation
     */
    public void insert(final T object, final SuccessCallback<Long> successCallback, final ErrorCallback errorCallback) {
        checkNotNull(successCallback);
        checkNotNull(errorCallback);
        checkNotNull(object);

        final Callable<Long> call = new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return SugarRecord.save(object);
            }
        };

        final Future<Long> future = doInBackground(call);
        Long id;

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
     * Method that performs a bulk insert. It works on top of SugarRecord class, and executes the query
     * asynchronously using Futures.
     *
     * @param objects the list of objects that you want to insert. They must be SugarRecord extended objects or @Table annotatd objects.
     * @param successCallback the callback for successful bulk insert operation
     * @param errorCallback the callback for an error in bulk insert operation
     */
    public void bulkInsert(final List<T> objects, final SuccessCallback<List<Long>> successCallback, final ErrorCallback errorCallback) {
        checkNotNull(successCallback);
        checkNotNull(errorCallback);
        checkNotNull(objects);

        final Callable<List<Long>> call = new Callable<List<Long>>() {
            @Override
            public List<Long> call() throws Exception {
                List<Long> ids = new ArrayList<>(objects.size());

                for (int i = 0; i < objects.size(); i++) {
                    Long id = SugarRecord.save(objects.get(i));
                    ids.add(i, id);
                }

                return ids;
            }
        };

        final Future<List<Long>> future = doInBackground(call);
        List<Long> ids;

        try {
            ids = future.get();

            if (null == ids || ids.isEmpty()) {
                errorCallback.onError(new Exception("Error when performing bulk insert"));
            } else {
                successCallback.onSuccess(ids);
            }

        } catch (Exception e) {
            errorCallback.onError(e);
        }
    }

    /**
     * Method that performs a findById, It works on top of SugarRecord class providing asynchronous
     * execution with the use of Futures.
     *
     * @param id the id of the object you want to retrieve
     * @param successCallback the callback to execute when the operation is successful
     * @param errorCallback the callback to execute when the operation has a trouble
     */
    public void findById(final Long id, final SuccessCallback<T> successCallback, final ErrorCallback errorCallback) {
        checkNotNull(successCallback);
        checkNotNull(errorCallback);
        checkNotNull(id);

        final Callable<T> call = new Callable<T>() {
            @Override
            public T call() throws Exception {
                return SugarRecord.findById(getSugarClass(), id);
            }
        };

        final Future<T> future = doInBackground(call);
        T object;

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
     * Method that provides you the ability of perform a custom query and retrieve a cursor. It works on top of SugarRecord class,
     * All the code is executed asynchronously with the usage of Futures and callbacks.
     *
     * @param whereClause the clause of the search
     * @param whereArgs the arguments for the search
     * @param groupBy the form that you want to group them
     * @param orderBy the form that you want to order
     * @param limit the limit of objects to want
     * @param successCallback the callback to be executed if the operation is successful
     * @param errorCallback the callback to be executed if the operation has an error
     */
    public void query(final String whereClause, final String[] whereArgs, final String groupBy, final String orderBy, final String limit, final SuccessCallback<Cursor> successCallback, final ErrorCallback errorCallback) {
        checkNotNull(successCallback);
        checkNotNull(errorCallback);

        final Callable<Cursor> call = new Callable<Cursor>() {
            @Override
            public Cursor call() throws Exception {
                return SugarRecord.getCursor(getSugarClass(), whereClause, whereArgs, groupBy, orderBy, limit);
            }
        };

        final Future<Cursor> future = doInBackground(call);
        Cursor cursor;

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
     * Method that list all elements. It run a SugarRecord.listAll but it's code is performed asynchronously
     * with the usage of Futures and callbacks.
     *
     * @param orderBy the way you want to order the objects you get
     * @param successCallback the callback that is performed if the operation is successful
     * @param errorCallback the callback that is performed if your code has an error
     */
    public void listAll(final String orderBy, final SuccessCallback<List<T>> successCallback, final ErrorCallback errorCallback) {
        checkNotNull(successCallback);
        checkNotNull(errorCallback);

        final Callable<List<T>> call = new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return SugarRecord.listAll(getSugarClass(), orderBy);
            }
        };

        final Future<List<T>> future = doInBackground(call);
        List<T> objects;

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
     * Method that works on top of SugarRecord.update and runs the code asynchronously via Futures
     * and callbacks.
     *
     * @param object the object you want to update
     * @param successCallback the callback that will be performed if the update is successful
     * @param errorCallback the callback that will be performed if the update has an error
     */
    public void update(final T object, final SuccessCallback<Long> successCallback, final ErrorCallback errorCallback) {
        checkNotNull(successCallback);
        checkNotNull(errorCallback);
        checkNotNull(object);

        final Callable<Long> call = new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return SugarRecord.update(object);
            }
        };

        final Future<Long> future = doInBackground(call);
        Long id;

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
     * This method works on top of SugarRecord and provides asynchronous code execution via the usage of
     * Futures and callbacks to handle success result and error.
     *
     * @param object the object you want to delete
     * @param successCallback the callback to be performed when the operation is successful
     * @param errorCallback the callback to be performed when the operation has an error
     */
    public void delete(final T object, final SuccessCallback<Boolean> successCallback, final ErrorCallback errorCallback) {
        checkNotNull(successCallback);
        checkNotNull(errorCallback);
        checkNotNull(object);

        final Callable<Boolean> call = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return SugarRecord.delete(object);
            }
        };

        final Future<Boolean> future = doInBackground(call);
        Boolean isDeleted;

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
     * Method that performs a selective delete. The code is executed asynchronously via the usage of Futures
     * and result callbacks
     *
     * @param whereClause the clause for the search
     * @param whereArgs the values
     * @param successCallback the callback to be executed if there is no trouble
     * @param errorCallback the callback to be executed if there is an error
     */
    public void delete(final String whereClause, final String[] whereArgs, final SuccessCallback<Integer> successCallback, final ErrorCallback errorCallback) {
        checkNotNull(successCallback);
        checkNotNull(errorCallback);

        final Callable<Integer> call = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return SugarRecord.deleteAll(getSugarClass(), whereClause, whereArgs);
            }
        };

        final Future<Integer> future = doInBackground(call);
        Integer count;

        try {
            count = future.get();

            if (null == count) {
                errorCallback.onError(new Exception("Error when performing delete of all elements"));
            } else {
                successCallback.onSuccess(count);
            }

        } catch (Exception e) {
            errorCallback.onError(e);
        }
    }

    /**
     * Method that deletes all data in a SQLite table.
     *
     * @param successCallback the callback that is executed if the operation is succesful
     * @param errorCallback the callback that is executed if there is an error
     */
    public void deleteAll(final SuccessCallback<Integer> successCallback, final ErrorCallback errorCallback) {
        delete(null, null, successCallback, errorCallback);
    }

    /**
     * Method that performs a count
     *
     * @param successCallback the callback that is executed if this is successful
     * @param errorCallback the callback that is executed if there is an error
     */
    public void count(final SuccessCallback<Long> successCallback, final ErrorCallback errorCallback) {
        checkNotNull(successCallback);
        checkNotNull(errorCallback);

        final Callable<Long> call = new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return SugarRecord.count(getSugarClass());
            }
        };

        final Future<Long> future = doInBackground(call);
        Long count;

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
     * Method that checks an object to be not null
     *
     * @param object the object to be checked
     */
    protected void checkNotNull(Object object) {
        if (null == object) {
            throw new IllegalArgumentException("object shouldn't be null");
        }
    }

    public Class<T> getSugarClass() {
        return sClass;
    }

    /**
     * The callback to be executed when some SugarDataSource operation is successful.
     *
     * @author jonatan.salas
     * @param <S> the parameter of the result that is passed to onSuccess method
     */
    public interface SuccessCallback<S> {

        /**
         * This code is executed if there is no trouble on any SugarDataSource operation.
         *
         * @param result the result of some SugarDatasource operation
         */
        void onSuccess(final S result);
    }

    /**
     * The callback to be executed when some SugarDataSource operation has an error.
     *
     * @author jonatan.salas
     */
    public interface ErrorCallback {

        /**
         * This method is executed if some trouble is detected when using some SugarDataSource method.
         *
         * @param e the exception thrown by the method of SugarDataSource you have invoked
         */
        void onError(final Exception e);
    }
}
