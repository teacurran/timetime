package com.approachingpi.timetime.web.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.codehaus.jackson.io.JsonStringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Based on an example class posted at:
 * http://compiotr.wordpress.com/2012/06/07/java-servlet-jsonp-filter/
 */
@WebFilter("/api/*")
public class JSONPRequestFilter implements Filter {
	private static final Logger LOGGER = LoggerFactory.getLogger(JSONPRequestFilter.class);

	//The callback method to use
	private static final String CALLBACK_METHOD = "callback";

	//This is a simple safe pattern check for the callback method
	public static final Pattern SAFE_PRN = Pattern.compile("[a-zA-Z0-9_\\.]+");

	public static final String CONTENT_TYPE = "application/javascript";

	private static final String CALLBACK_PARAM_NAME = "callback", REQUEST_METHOD_PARAM_NAME = "_method", REQUEST_BODY_PARAM_NAME = "_body", REQUEST_BODY_CONTENT_TYPE = "_content-type";
	private static final String[] JSON_MIME_TYPES = {"application/json", "application/x-json", "text/json", "text/x-json"};

	@Override
	public void init(FilterConfig config) throws ServletException {
		//Nothing needed
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
			ServletException {

		if (!(request instanceof HttpServletRequest)) {
			throw new ServletException("Only HttpServletRequest requests are supported");
		}

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		if (httpRequest.getMethod().equals("GET")) {

			// Change request method
			final String requestMethodOverride = httpRequest.getParameter(REQUEST_METHOD_PARAM_NAME);
			if (requestMethodOverride != null) {
				request = new JsonpHttpServletRequestWrapper(httpRequest, requestMethodOverride.toUpperCase());
			}

			// Wrap content in callback
			final String callback = httpRequest.getParameter(CALLBACK_PARAM_NAME);

			if (callback != null && !callback.isEmpty()) {
			//Need to check if the callback method is safe
			if (!SAFE_PRN.matcher(callback).matches()) {
				throw new ServletException("JSONP Callback method '" + CALLBACK_METHOD + "' parameter not valid function");
			}

				response = new JsonpHttpServletResponseWrapper((HttpServletResponse) response, callback);
			}
		}

		filterChain.doFilter(request, response);
		response.flushBuffer();

	}

	private String getCallbackMethod(HttpServletRequest httpRequest) {
		return httpRequest.getParameter(CALLBACK_METHOD);
	}

	private boolean isJSONPRequest(String callbackMethod) {
		//A simple check to see if the query parameter has been set.
		return (callbackMethod != null && callbackMethod.length() > 0);
	}

	@Override
	public void destroy() {
		//Nothing to do
	}


	private static class JsonpHttpServletResponseWrapper extends HttpServletResponseWrapper {
		private final String callback;
		private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		private PrintWriter printWriter;
		private final static JsonStringEncoder JSON_ENCODER = new JsonStringEncoder();

		public JsonpHttpServletResponseWrapper(final HttpServletResponse response, final String callback) {

			super(response);

			this.callback = callback;
		}

		@Override
		public String getContentType() {

			return "application/javascript";
		}

		@Override
		public PrintWriter getWriter() throws IOException {

			if (printWriter == null) {
				printWriter = new PrintWriter(new OutputStreamWriter(byteArrayOutputStream, this.getCharacterEncoding()));
			}
			return printWriter;
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {

			return byteArrayOutputStream;
		}

		@Override
		public void setBufferSize(final int size) {

			byteArrayOutputStream.ensureCapacity(size);
		}

		@Override
		public int getBufferSize() {

			return byteArrayOutputStream.size();
		}

		@Override
		public void reset() {

			getResponse().reset();
			byteArrayOutputStream.reset();
		}

		@Override
		public void resetBuffer() {

			reset();
		}

		@Override
		public void flushBuffer() throws IOException {

			if (printWriter != null) {
				printWriter.close();
			}
			byteArrayOutputStream.close();

			StringBuilder metaContent = new StringBuilder();
			metaContent.append("\"meta\": {\"code\": ")
					.append(this.getStatus())
					.append("}");

			String content;
			if (this.getStatus() == SC_NO_CONTENT) {
				content = "{" + metaContent.toString() + "}";
			} else {

				content = new String(byteArrayOutputStream.toByteArray(), "UTF-8");

				// Do we need to escape the content?
				final String responseContentType = getResponse().getContentType();
				boolean escapeContent = true;
				for (final String type : JSON_MIME_TYPES) {

					if (responseContentType != null && responseContentType.contains(type)) {

						escapeContent = false;
						break;
					}
				}
				if (escapeContent) {
					content = new String(JSON_ENCODER.quoteAsUTF8(content), "UTF-8");
				}

				metaContent.append(",");
				content = content.replaceFirst("\\{", "{" + metaContent.toString());
			}
			this.setStatus(SC_OK);

			byte[] bytes = (callback + "(" + content + ");").getBytes(getCharacterEncoding());
			getResponse().setContentLength(bytes.length);
			getResponse().getOutputStream().write(bytes);
			getResponse().flushBuffer();
		}

		private static class ByteArrayOutputStream extends ServletOutputStream {

			private byte buf[] = new byte[32];
			private int count;

			private void ensureCapacity(final int minCapacity) {

				if (minCapacity - buf.length > 0) grow(minCapacity);
			}

			private void grow(final int minCapacity) {

				final int oldCapacity = buf.length;
				int newCapacity = oldCapacity << 1;
				if (newCapacity - minCapacity < 0) {
					newCapacity = minCapacity;
				}
				if (newCapacity < 0) {
					if (minCapacity < 0) throw new OutOfMemoryError();
					newCapacity = Integer.MAX_VALUE;
				}
				buf = Arrays.copyOf(buf, newCapacity);
			}

			public void write(final int b) {
				ensureCapacity(count + 1);
				buf[count] = (byte) b;
				count += 1;
			}

			public void reset() {
				count = 0;
			}

			public byte toByteArray()[] {
				return Arrays.copyOf(buf, count);
			}

			public int size() {
				return count;
			}
		}

	}

	private static class JsonpHttpServletRequestWrapper extends HttpServletRequestWrapper {

		private final String method, bodyContentType;
		private final ByteArrayServletInputStream servletInputStream;
		private BufferedReader reader;
		Map<String,String[]> params;

		public JsonpHttpServletRequestWrapper(final HttpServletRequest request, final String method) throws UnsupportedEncodingException {

			super(request);

			this.method = method;

			params = request.getParameterMap();

			LOGGER.debug("parameters:{}", params);

			final String body = request.getParameter(REQUEST_BODY_PARAM_NAME);
			if ((method.equals("POST") || method.equals("PUT")) && body != null) {

				servletInputStream = new ByteArrayServletInputStream(body.getBytes(getCharacterEncoding()));
				bodyContentType = request.getParameter(REQUEST_BODY_CONTENT_TYPE);
			} else {

				String queryString = request.getQueryString();
				servletInputStream = new ByteArrayServletInputStream(queryString.getBytes());
				bodyContentType = "application/x-www-form-urlencoded";

			}
		}

		@Override
		public String getQueryString() {
			// resteasy determines what is a @FormParam by subtracting the QueryParams, so set query params to null
			return null;
		}

		@Override
		public String getParameter(String name) {
			// return the parameter we extracted from teh GET request first.
			if (params.get(name) != null) {
				String[] parameterValues = params.get(name);
				if (parameterValues.length > 0) {
					return parameterValues[0];
				}
			}
			return super.getParameter(name);
	   }

		@Override
		public String[] getParameterValues(String name) {
			if (params.get(name) != null) {
				return params.get(name);
			}
			return super.getParameterValues(name);

		}

		@Override
		public Map<String, String[]> getParameterMap() {
			// TODO: see if we can merge this with the super.getParameterMap()
			// since we are only using this class to convert GET to POST this might not be needed.
			return params;
		}


		@Override
		public String getMethod() {

			return method;
		}

		@Override
		public int getContentLength() {

			return servletInputStream == null ? super.getContentLength() : servletInputStream.size();
		}

		@Override
		public String getContentType() {

			return bodyContentType == null ? super.getContentType() : bodyContentType;
		}

		@Override
		public ServletInputStream getInputStream() throws IOException {

			return servletInputStream == null ? super.getInputStream() : servletInputStream;
		}

		@Override
		public BufferedReader getReader() throws IOException {

			if (servletInputStream != null && reader == null) {
				reader = new BufferedReader(new InputStreamReader(servletInputStream));
			}
			return reader == null ? super.getReader() : reader;
		}

		private static class ByteArrayServletInputStream extends ServletInputStream {

			protected byte buf[];
			private int pos, mark = 0, count;

			public ByteArrayServletInputStream(final byte buf[]) {

				this.buf = buf;
				this.pos = 0;
				this.count = buf.length;
			}

			@Override
			public int read() throws IOException {

				return (pos < count) ? (buf[pos++] & 0xff) : -1;
			}

			public long skip(final long n) {

				long k = count - pos;
				if (n < k) {
					k = n < 0 ? 0 : n;
				}
				pos += k;
				return k;

			}

			public int available() {

				return count - pos;
			}

			public boolean markSupported() {

				return true;
			}

			public void mark(final int readAheadLimit) {

				mark = pos;
			}

			public void reset() {

				pos = mark;
			}

			public int size() {

				return buf.length;
			}
		}

	}
}