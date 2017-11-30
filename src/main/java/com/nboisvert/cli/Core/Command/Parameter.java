package com.nboisvert.cli.Core.Command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Parameter annotation
 *
 * Must wrap a String field. If a command line parameter
 * with the specified key if provided, the value of the
 * field will match. Will set to default otherwise
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Parameter
{
    /**
     * Key of the prameter
     *
     * @return
     */
    String key();

    /**
     * Fallback value
     *
     * @return String
     */
    String fallback() default "";

    /**
     * Description of the parameter
     *
     * @return String;
     */
    String description() default "";
}
