package com.approachingpi.timetime.exceptions.handlers;

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import com.approachingpi.timetime.services.Configuration;
import org.jboss.solder.exception.control.CaughtException;
import org.jboss.solder.exception.control.Handles;
import org.jboss.solder.servlet.WebRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 7/20/12
 *
 * @author T. Curran
 */
public class WebExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebExceptionHandler.class);

	@Inject
	Configuration configuration;

	@Inject
	private FacesContext facesContext;

	void handleWebRequest(
		@Handles
		@WebRequest
		final CaughtException< Throwable > event,
		final HttpServletResponse response ) {

		LOGGER.error( "exception caught", event.getException() );

		event.handled();

		try {
			this.facesContext.getExternalContext().redirect( "/error" );

		} catch ( final IOException e ) {
			LOGGER.error( "error forwarding request", e );
		}
	}

}
