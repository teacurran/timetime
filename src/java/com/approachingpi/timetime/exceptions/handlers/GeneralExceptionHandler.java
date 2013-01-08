package com.approachingpi.timetime.exceptions.handlers;

import javax.inject.Inject;

import com.approachingpi.timetime.services.Configuration;
import org.jboss.solder.exception.control.CaughtException;
import org.jboss.solder.exception.control.Handles;
import org.jboss.solder.exception.control.HandlesExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logs all exceptions and allows the to propagate
 *
 * @author <a href="http://community.jboss.org/people/spinner">Jose Freitas</a>
 */
@HandlesExceptions
public class GeneralExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger( GeneralExceptionHandler.class );

	@Inject
	Configuration configuration;

	public void handleNonWebRequest(
		@Handles
		final CaughtException< Throwable > event ) {

		LOGGER.error( "exception caught", event.getException() );

		//event.handled();
	}


}