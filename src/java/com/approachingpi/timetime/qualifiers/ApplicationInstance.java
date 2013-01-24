package com.approachingpi.timetime.qualifiers;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 * Date: 6/27/11
 * 
 * @author T. Curran
 * 
 *         Qualifier to indicate that a class should use the version from the current applicaiton
 *         rather than a bundled Seam implementation. For example
 *         com.emdserono.adminportal.MailConfig should be used rather than
 *         org.jboss.seam.mail.core.MailConfig
 */
@Target( { TYPE, METHOD, PARAMETER, FIELD } )
@Retention( RUNTIME )
@Documented
@Qualifier
public @interface ApplicationInstance {}
