package com.nboisvert.cli.Core.Command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Option annotation
 *
 * Must wrap a boolean field. The value of the field will
 * automatically be true if the option or flag are filled
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Option
{
    /**
     * Key of the option
     *
     * @return String
     */
    String key();

    /**
     * Flag matching the option
     *
     * @return char
     */
    char flag() default ' ';

    /**
     * Description of the option
     *
     * @return String
     */
    String description() default "";
}
