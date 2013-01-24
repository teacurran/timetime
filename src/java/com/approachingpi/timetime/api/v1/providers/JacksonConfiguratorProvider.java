package com.approachingpi.timetime.api.v1.providers;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJacksonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@Consumes({MediaType.APPLICATION_JSON, "text/json"})
@Produces({MediaType.APPLICATION_JSON, "text/json"})
public class JacksonConfiguratorProvider extends ResteasyJacksonProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(JacksonConfiguratorProvider.class);

	public JacksonConfiguratorProvider() {
		super();

		LOGGER.info("loading timetime jackson configurator");

		//_mapperConfig.getConfiguredMapper();
		//ObjectMapper mapper = _mapperConfig.getConfiguredMapper();
		//ObjectMapper mapper = super.locateMapper(Object.class, MediaType.APPLICATION_JSON_TYPE);

		//mapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, true);

		//configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, true);
		//configure(SerializationConfig.Feature.INDENT_OUTPUT, false);
		//configure(SerializationConfig.Feature.SORT_PROPERTIES_ALPHABETICALLY, true);
		JacksonObjectMapper mapper = JacksonObjectMapper.get();
		setMapper(mapper);

	}
}