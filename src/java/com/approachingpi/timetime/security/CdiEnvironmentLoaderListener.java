package com.approachingpi.timetime.security;

import javax.servlet.ServletContext;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.web.env.DefaultWebEnvironment;
import org.apache.shiro.web.env.EnvironmentLoaderListener;
import org.apache.shiro.web.env.WebEnvironment;

/**
 * Date: 3/20/13
 *
 * @author T. Curran
 */
public class CdiEnvironmentLoaderListener extends EnvironmentLoaderListener {

	TimeTimeAuthorizingRealm shiroRealm = null;

	@Override
	protected WebEnvironment createEnvironment(ServletContext sc) {
		WebEnvironment environment = super.createEnvironment(sc);
		shiroRealm = new TimeTimeAuthorizingRealm();

		RealmSecurityManager rsm = (RealmSecurityManager)environment.getSecurityManager();

		//HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		//matcher.setHashAlgorithmName(Sha512Hash.ALGORITHM_NAME);
		//
		//shiroRealm.setCredentialsMatcher(matcher);

		rsm.setRealm(shiroRealm);

		((DefaultWebEnvironment)environment).setSecurityManager(rsm);

		return environment;
	}
}