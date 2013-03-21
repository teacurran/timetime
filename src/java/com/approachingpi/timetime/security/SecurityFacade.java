package com.approachingpi.timetime.security;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.inject.Singleton;

import com.approachingpi.timetime.qualifiers.ApplicationInstance;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 1/31/13
 *
 * @author T. Curran
 */
@Singleton
public class SecurityFacade {

	Logger LOGGER = LoggerFactory.getLogger(SecurityFacade.class);
	private SecurityManager securityManager;

	@PostConstruct
	public void init() {

		LOGGER.info("Initializing Shiro SecurityManager using ");

		AuthorizingRealm authorizingRealm = new TimeTimeAuthorizingRealm();

		SecurityManager securityManager = new DefaultSecurityManager(authorizingRealm);
		
		authorizingRealm.setCredentialsMatcher(new TimeTimePasswordMatcher());
		
		//Make the SecurityManager instance available to the entire application via static memory:
		SecurityUtils.setSecurityManager(securityManager);
		
	}

}