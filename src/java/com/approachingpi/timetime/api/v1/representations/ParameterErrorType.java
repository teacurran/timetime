package com.approachingpi.timetime.api.v1.representations;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Date: 7/21/12
 *
 * @author T. Curran
 */
@XmlRootElement(name="parameter-error")
@XmlAccessorType(XmlAccessType.FIELD)
public class ParameterErrorType {

	String parameter;
	String message;

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
