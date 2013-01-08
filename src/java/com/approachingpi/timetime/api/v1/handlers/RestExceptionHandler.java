package com.approachingpi.timetime.api.v1.handlers;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.approachingpi.timetime.api.v1.providers.ExceptionMapperProvider;
import com.approachingpi.timetime.api.v1.representations.ApplicationError;
import com.approachingpi.timetime.api.v1.representations.EnumErrorCode;
import com.approachingpi.timetime.exceptions.handlers.GeneralExceptionHandler;
import com.approachingpi.timetime.services.Configuration;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.jboss.resteasy.spi.NotFoundException;
import org.jboss.seam.rest.exceptions.RestResource;
import org.jboss.solder.exception.control.CaughtException;
import org.jboss.solder.exception.control.Handles;
import org.jboss.solder.exception.control.HandlesExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 7/20/12
 *
 * @author T. Curran
 */
@HandlesExceptions
public class RestExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

	@Inject
	Configuration configuration;

	public void handleNotFoundException(
		@Handles
		CaughtException<NotFoundException> event,
		@RestResource
		Response.ResponseBuilder builder
		) {

		EnumErrorCode errorCode = EnumErrorCode.RESOURCE_NOT_FOUND;
		ApplicationError applicationError = new ApplicationError();
		applicationError.setCode(errorCode);
		applicationError.setText(configuration.getMessage("api.v1.error." + errorCode.value(), errorCode.name()));

		builder.status(ExceptionMapperProvider.resolveStatus(applicationError.getCode()))
				.entity(applicationError);
	}

	public void handleMethodConstraintViolation(
		@Handles
		CaughtException<MethodConstraintViolationException> event,
		@RestResource
		Response.ResponseBuilder builder) {

		LOGGER.debug("method straint violation caught", event.getException());

		event.handled();

		EnumErrorCode errorCode = EnumErrorCode.RESOURCE_NOT_FOUND;
		ApplicationError applicationError = new ApplicationError();
		applicationError.setCode(errorCode);
		applicationError.setText(configuration.getMessage("api.v1.error." + errorCode.value(), errorCode.name()));

		builder.status(ExceptionMapperProvider.resolveStatus(applicationError.getCode()))
				.entity(applicationError);
	}

	public void handleGeneralException(
		@Handles
		CaughtException<MethodConstraintViolationException> event,
		@RestResource
		Response.ResponseBuilder builder) {

		EnumErrorCode errorCode = EnumErrorCode.ILLEGAL_ARGUMENT_ERROR;
		ApplicationError applicationError = new ApplicationError();
		applicationError.setCode(errorCode);
		applicationError.setText(configuration.getMessage("api.v1.error." + errorCode.value(), errorCode.name()));

		builder.status(ExceptionMapperProvider.resolveStatus(applicationError.getCode()))
				.entity(applicationError);
	}

}
