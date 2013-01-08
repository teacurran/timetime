package com.approachingpi.timetime.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Date: 11/18/10
 *
 * @author T. Curran
 */
public class HttpDebugProxyBean implements Serializable {

	private Date date;

	private String method = "";

	private String requestHeaders = "";
	private String requestBody = "";

	private int responseCode = 0;
	private String responseHeaders = "";
	private String responseBody = "";

	private String url;

	public HttpDebugProxyBean() {

		date = new Date();
	}

	public Date getDate() {
		return date;
	}

	public void setDate( Date date ) {
		this.date = date;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod( String method ) {
		this.method = method;
	}

	public String getRequestHeaders() {
		return requestHeaders;
	}

	public void setRequestHeaders( String requestHeaders ) {
		this.requestHeaders = requestHeaders;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody( String requestBody ) {
		this.requestBody = requestBody;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode( int responseCode ) {
		this.responseCode = responseCode;
	}

	public String getResponseHeaders() {
		return responseHeaders;
	}

	public void setResponseHeaders( String responseHeaders ) {
		this.responseHeaders = responseHeaders;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody( String responseBody ) {
		this.responseBody = responseBody;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl( String url ) {
		this.url = url;
	}
}
