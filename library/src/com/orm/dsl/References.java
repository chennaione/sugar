package com.orm.dsl;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface References
{
    String foreignTable();
    String foreignColumn() default "ID";
    String onDelete() default "NO ACTION";
    String onUpdate() default "NO ACTION";
}
