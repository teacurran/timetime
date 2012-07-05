package com.approachingpi.timetime.interceptor;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.jboss.logging.Logger;

/**
 * An Interceptor that wraps a timestamp around a method invocation. Useful for measuring the time
 * taken to complete a method invocation.
 * 
 * @See @Timestampable
 */
@Timestampable
@Interceptor
public class TimestampableInterceptor
	implements Serializable {

	private static final long serialVersionUID = 8205679619877467367L;

	@Inject
	Logger log;

	@AroundInvoke
	public Object computeTimestamp(
		InvocationContext ctx )
		throws Exception {

		log.trace( String.format(
			"Starting %s.%s",
			ctx.getMethod().getDeclaringClass().getSimpleName(),
			ctx.getMethod().getName() ) );

		long start = System.currentTimeMillis();

		Object object = ctx.proceed();

		long stop = System.currentTimeMillis();

		log.trace( String.format(
			"Invocation time for %s.%s is %s",
			ctx.getMethod().getDeclaringClass().getSimpleName(),
			ctx.getMethod().getName(),
			String.valueOf( stop - start ) ) );

		return object;
	}
}
