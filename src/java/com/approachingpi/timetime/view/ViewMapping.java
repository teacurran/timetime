package com.approachingpi.timetime.view;

import com.approachingpi.timetime.security.annotations.AuthenticatedUser;
import org.jboss.seam.faces.rewrite.UrlMapping;
import org.jboss.seam.faces.view.config.ViewConfig;
import org.jboss.seam.faces.view.config.ViewPattern;

/**
 * Date: 1/21/13
 *
 * @author T. Curran
 */
@ViewConfig
public interface ViewMapping {

	static enum Pages {

		@UrlMapping(pattern = "/")
		@ViewPattern("/pages/index.xhtml")
		HOME,

		@AuthenticatedUser
		@UrlMapping(pattern = "/home")
		@ViewPattern("/pages/secure/home.xhtml")
		SECURE_HOME,

		@UrlMapping(pattern = "/error")
		@ViewPattern("/pages/error.xhtml")
		ERROR,

		@UrlMapping(pattern = "/login")
		@ViewPattern("/pages/auth/login.xhtml")
		LOGIN;
	}
}
