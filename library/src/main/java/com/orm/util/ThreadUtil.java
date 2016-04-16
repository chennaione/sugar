package com.orm.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author jonatan.salas
 */
public final class ThreadUtil {

    //Prevent instantiation..
    private ThreadUtil() { }

    public static Future doInBackground(Callable callable) {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        Future future = executor.submit(callable);

        if(executor.isTerminated()) {
            executor.shutdown();
        }

        return future;
    }
}
