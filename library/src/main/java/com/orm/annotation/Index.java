package com.orm.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Lee Howett on 2017-05-16.
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Index {
    boolean unique() default false;
}
