package com.approachingpi.timetime.web.validation;

import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import com.approachingpi.timetime.util.StringUtils;
import com.approachingpi.timetime.util.TimeTimeConstants;
import org.jboss.seam.faces.validation.InputElement;
import org.jboss.seam.international.status.Messages;
import org.jboss.seam.international.status.builder.BundleKey;

/**
 * Date: 1/21/13
 *
 * @author T. Curran
 */
@FacesValidator( "registerAccountValidator" )
public class RegisterAccountValidator implements Validator {

	Pattern emailPattern = Pattern.compile( TimeTimeConstants.REGEX_EMAIL );

	@Inject
	private InputElement< String > registerFullnameField;

	@Inject
	private InputElement< String > registerEmailField;

	@Inject
	private InputElement< String > registerCompanyField;

	@Inject
	private InputElement< String > registerPasswordField;

	@Inject
	Messages messages;

	@Override
	public void validate(
		final FacesContext facesContext,
		final UIComponent uiComponent,
		final Object values )
		throws ValidatorException {

		boolean addressRequired = true;

		// see if they entered an email address and if it is valid
		if ( StringUtils.isEmpty(this.registerFullnameField.getValue())) {
			this.registerFullnameField.getComponent().setValid(false);
			messages.error( "First Name required" );
		}

		// see if they entered an email address and if it is valid
		if ( !StringUtils.isEmpty( this.registerEmailField.getValue() ) ) {

			if ( !this.emailPattern.matcher( this.registerEmailField.getValue() ).matches() ) {
				this.registerEmailField.getComponent().setValid( false );
				messages.error( "You must enter a valid email address" );
			}
		}

		// not throwing an error because that highlights all fields in the form.
		// setting messages.error will cause the form to be invalid.
		// if ( errors.size() > 0 ) {
		// throw new ValidatorException( errors );
		// }
	}

	public FacesMessage createError(
		final String value ) {

		messages.error( value );

		final FacesMessage message = new FacesMessage();
		message.setDetail( value );
		message.setSummary( value );
		message.setSeverity( FacesMessage.SEVERITY_ERROR );
		return message;
	}
}
