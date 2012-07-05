package com.approachingpi.timetime.interceptor;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

/**
 * Denotes a method can be wrapped around a timestamp. Useful for measuring the time for a
 * particular method invocation
 */
@Inherited
@InterceptorBinding
@Target( { TYPE, METHOD } )
@Retention( RUNTIME )
public @interface Timestampable {}
