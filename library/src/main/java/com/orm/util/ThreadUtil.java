package com.orm.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Util class to deal with threads.
 *
 * @author jonatan.salas
 */
public final class ThreadUtil {

    //Prevent instantiation..
    private ThreadUtil() { }

    /**
     * Submits a Callable object and returns a Future ready to use.
     *
     * @param callable the callable you want to submit
     * @return a Future object
     */
    public static Future doInBackground(Callable callable) {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        Future future = executor.submit(callable);

        if(executor.isTerminated()) {
            executor.shutdown();
        }

        return future;
    }
}
