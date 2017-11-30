package com.nboisvert.cli.Core.Command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Argument annotation
 *
 * Must wrap a String field. The value of the field
 * will be attached automatically
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Argument
{
    String name();
    int position();
    String fallback() default "";
}
