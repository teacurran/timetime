package com.approachingpi.timetime.security;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.seam.security.management.picketlink.JpaIdentityStoreConfiguration;
import org.jboss.solder.servlet.WebApplication;
import org.jboss.solder.servlet.event.Initialized;

/**
 * Date: 1/27/13
 *
 * @author T. Curran
 */
public class IdentityStoreInitializer {
	@Inject
	private JpaIdentityStoreConfiguration identityStoreConfig;

	public void initialize(@Observes @Initialized WebApplication webapp) {
		identityStoreConfig.setIdentityStoreClass(JpaIdentityStoreWithHashing.class);
	}
}

