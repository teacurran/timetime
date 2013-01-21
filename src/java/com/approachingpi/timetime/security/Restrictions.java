package com.approachingpi.timetime.security;

import com.approachingpi.timetime.security.annotations.AuthenticatedUser;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;

/**
 * Date: 1/21/13
 *
 * @author T. Curran
 */
public class Restrictions {

	public
	@Secures
	@AuthenticatedUser
	boolean isUser(Identity identity) {
		return identity.inGroup("USERS", "GROUP");
	}

}
