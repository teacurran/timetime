package com.approachingpi.timetime.api.v1.providers;

import java.util.List;

import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.BasicBeanDescription;
import org.codehaus.jackson.map.ser.BeanPropertyWriter;
import org.codehaus.jackson.map.ser.BeanSerializer;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;

/*
 * xjc generated classes contain isSetFoo() methods which get interpreted
 * by Jackson as "setFoo" properties which we don't want serialized.
 */
public class JacksonBeanFactory extends CustomSerializerFactory {

}