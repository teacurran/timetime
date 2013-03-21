package com.approachingpi.timetime.security;

import org.apache.shiro.authc.credential.PasswordMatcher;

/**
 * Date: 3/21/13
 *
 * @author T. Curran
 */
public class TimeTimePasswordMatcher
	extends PasswordMatcher {

	public TimeTimePasswordMatcher() {
		this.setPasswordService(new PasswordMatchService());
	}
}
