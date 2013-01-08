package com.approachingpi.timetime.api.v1;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.approachingpi.timetime.api.v1.resources.AccountResource;
import com.approachingpi.timetime.api.v1.providers.ExceptionMapperProvider;
import com.approachingpi.timetime.api.v1.providers.JacksonConfiguratorProvider;

/**
 * Date: 7/10/12
 *
 * @author T. Curran
 */
@ApplicationPath("/api/v1")
public class V1Application extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();

		classes.add(AccountResource.class);

		classes.add(ExceptionMapperProvider.class);
		classes.add(JacksonConfiguratorProvider.class);

		return classes;
	}
}
