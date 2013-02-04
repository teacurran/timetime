package com.approachingpi.timetime.interceptor;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.approachingpi.timetime.security.annotations.NamedResource;
import com.approachingpi.timetime.security.annotations.Secured;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 2/4/13
 *
 * @author T. Curran
 */
@Secured
@Interceptor
public class SecurityInterceptor {

	public static final Logger LOGGER = LoggerFactory.getLogger(SecurityInterceptor.class);

	@Inject
	Subject subject;

	@Inject
	SecurityManager securityManager;

	@AroundInvoke
	public Object interceptGet(InvocationContext ctx) throws Exception {
		LOGGER.info("Securing {} {}", new Object[]{ctx.getMethod(), ctx.getParameters()});
		LOGGER.debug("Principal is: {}", subject.getPrincipal());

		final Class<? extends Object> runtimeClass = ctx.getTarget().getClass();
		LOGGER.debug("Runtime extended classes: {}", runtimeClass.getClasses());
		LOGGER.debug("Runtime implemented interfaces: {}", runtimeClass.getInterfaces());
		LOGGER.debug("Runtime annotations ({}): {}", runtimeClass.getAnnotations().length, runtimeClass.getAnnotations());

		final Class<?> declaringClass = ctx.getMethod().getDeclaringClass();
		LOGGER.debug("Declaring class: {}", declaringClass);
		LOGGER.debug("Declaring extended classes: {}", declaringClass.getClasses());
		LOGGER.debug("Declaring annotations ({}): {}", declaringClass.getAnnotations().length, declaringClass.getAnnotations());

		String entityName;
		try {
			NamedResource namedResource = runtimeClass.getAnnotation(NamedResource.class);
			entityName = namedResource.value();
			LOGGER.debug("Got @NamedResource={}", entityName);
		} catch (NullPointerException e) {
			entityName = declaringClass.getSimpleName().toLowerCase(); // TODO: should be lowerFirst camelCase
			LOGGER.warn("@NamedResource not annotated, inferred from declaring classname: {}", entityName);
		}

		String action = "admin";
		if (ctx.getMethod().getName().matches("get[A-Z].*"))
			action = "view";
		if (ctx.getMethod().getName().matches("set[A-Z].*"))
			action = "edit";

		String permission = String.format("%s:%s", action, entityName);
		LOGGER.info("Checking permission '{}' for user '{}'", permission, subject.getPrincipal());
		try {
			subject.checkPermission(permission);
		} catch (Exception e) {
			LOGGER.error("Access denied - {}: {}", e.getClass().getName(), e.getMessage());
			throw e;
		}
		return ctx.proceed();
	}
}