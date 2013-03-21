package com.approachingpi.timetime.services;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.approachingpi.timetime.qualifiers.ApplicationInstance;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.jboss.seam.international.status.Messages;

/**
 * Date: 3/17/13
 *
 * @author T. Curran
 */
@Named
@RequestScoped
public class SecurityService implements Serializable {

	private static final long serialVersionUID = 4385533312552666762L;

	@Inject
	protected transient Subject subject;

	@Inject
	protected Messages messages;

	@Inject
	protected Configuration configuration;

	protected String username;

	protected String password;

	public String login() {

		// Authenticate
		try {
			subject.login( new UsernamePasswordToken( this.username, this.password ) );

			
			
		} catch ( final AuthenticationException e ) {

			this.messages.error( this.configuration.getMessage(
				"authentication.failure",
				"Login Failed" ) );

			return "failure";
		}

		// Navigate to administration console home page
		return "success";
	}

	public String logout() {

		subject.logout();

		// Navigate to administration console login page
		return "success";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
