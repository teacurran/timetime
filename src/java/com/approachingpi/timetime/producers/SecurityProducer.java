package com.approachingpi.timetime.producers;

import javax.enterprise.inject.Produces;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Date: 1/27/13
 *
 * @author T. Curran
 */
public class SecurityProducer {

	@Produces
	public Subject getSubject() {
		return SecurityUtils.getSubject();
	}
}
