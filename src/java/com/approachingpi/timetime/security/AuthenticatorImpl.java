package com.approachingpi.timetime.security;

import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.security.Authenticator;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.picketlink.idm.impl.api.model.SimpleUser;


/**
 * Date: 1/20/13
 *
 * @author T. Curran
 */
@Named
public class AuthenticatorImpl extends BaseAuthenticator implements Authenticator {

	@Inject
	Identity identity;

	@Inject
	Credentials credentials;

	@Override
	public void authenticate() {
		if ("demo".equals(credentials.getUsername())) {
			identity.addRole("admin", "USERS", "GROUP");
		}

		if ("user".equals(credentials.getUsername())) {
			identity.addGroup("USERS", "GROUP");
		}

		// Let any user log in
		setStatus(Authenticator.AuthenticationStatus.SUCCESS);
		setUser(new SimpleUser(credentials.getUsername()));
	}
}
