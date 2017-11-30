package com.nboisvert.cli.Core.Functionality;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Boot annotation
 *
 * When used on a method, the method will be invoked
 * after the functionality is executed
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dismantle {}
