package com.approachingpi.timetime.api.v1.providers;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

public class JacksonObjectMapper extends ObjectMapper {

	public static JacksonObjectMapper get() {


		JacksonObjectMapper mapper = new JacksonObjectMapper();

		mapper.setSerializer(new JacksonBeanFactory()).includeDefaults(false);

		mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
		mapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, true);


		final AnnotationIntrospector pair = new AnnotationIntrospector.Pair(
				new JacksonAnnotationIntrospector(),
				new JaxbAnnotationIntrospector());
		mapper.setDeserializationConfig(mapper.getDeserializationConfig().withAnnotationIntrospector(pair));
		mapper.setSerializationConfig(mapper.getSerializationConfig().withAnnotationIntrospector(pair));

		return mapper;
	}

	protected JacksonObjectMapper setSerializer(CustomSerializerFactory ser) {
		setSerializerFactory(ser);
		getSerializationConfig().withView(String.class);
		return this;
	}

	protected JacksonObjectMapper includeDefaults(boolean include) {
		getSerializationConfig().setSerializationInclusion(
				include ? JsonSerialize.Inclusion.ALWAYS : JsonSerialize.Inclusion.NON_DEFAULT);
		return this;
	}

}