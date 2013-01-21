package com.approachingpi.timetime.security.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.seam.security.annotations.SecurityBindingType;

/**
 * Date: 1/21/13
 *
 * @author T. Curran
 */
@SecurityBindingType
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
public @interface AuthenticatedUser {
}



