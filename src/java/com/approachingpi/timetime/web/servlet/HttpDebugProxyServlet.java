package com.approachingpi.timetime.web.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.approachingpi.timetime.beans.HttpDebugProxyBean;
import com.approachingpi.timetime.services.Configuration;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 11/19/10
 *
 * @author T. Curran
 */
public class HttpDebugProxyServlet extends HttpServlet {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpDebugProxyServlet.class);

	@Inject
	private Configuration configuration;

	private ServletContext servletContext;

	// todo, make this configurable so we can reuse this servlet.
	private static final String endpointUrlResource = "mediamind.url";

	private static final String applicationStoreKey = "HttpDebugProxyServlet.requests";

	private String endpointUrl;

	public void init(ServletConfig config) throws ServletException {

		super.init(config);

		endpointUrl = configuration.getSetting(endpointUrlResource);

		servletContext = config.getServletContext();
	}

	public void doGet(final HttpServletRequest request,
					  final HttpServletResponse response) {

		doPost(request, response);
	}

	public void doPost(final HttpServletRequest request,
					   final HttpServletResponse response) {

		ArrayList<HttpDebugProxyBean> store = null;
		if (servletContext.getAttribute(applicationStoreKey) != null) {
			store = (ArrayList<HttpDebugProxyBean>) servletContext.getAttribute(applicationStoreKey);
		}
		if (store == null) {
			store = new ArrayList<HttpDebugProxyBean>();
			servletContext.setAttribute(applicationStoreKey, store);
		}

		HttpDebugProxyBean proxyBean = new HttpDebugProxyBean();
		store.add(proxyBean);

		if (store.size() > 10) {
			store.remove(0);
		}

		HttpURLConnection con;

		try {
			int oneByte;
			String headerText;

			StringBuilder endpointUrlBuilder = new StringBuilder();
			endpointUrlBuilder.append(endpointUrl);

			if ( request.getPathInfo() != null ) {
				endpointUrlBuilder.append(request.getPathInfo());
			}

			String queryString = request.getQueryString();
			if (queryString != null) {
				endpointUrlBuilder.append("?").append(queryString);
			}

			URL endpointUrl = new URL(endpointUrlBuilder.toString());

			proxyBean.setUrl(endpointUrl.toString());
			LOGGER.debug("endpointUrl:{}", endpointUrl.toString());

			proxyBean.setMethod(request.getMethod());

			con = (HttpURLConnection) endpointUrl.openConnection();

			con.setRequestMethod(request.getMethod());
			con.setDoOutput(true);
			con.setDoInput(true);
			//con.setFollowRedirects( true );
			con.setUseCaches(true);

			Enumeration headerEnum = request.getHeaderNames();
			while (headerEnum.hasMoreElements()) {
				String key = headerEnum.nextElement().toString();
				String value = request.getHeader(key);
				if (key.equalsIgnoreCase("SOAPAction")
						|| key.equalsIgnoreCase("user-agent")
						|| key.equalsIgnoreCase("content-type")
						) {
					con.setRequestProperty(key, value);
				}
			}

			StringBuilder requestHeaders = new StringBuilder();
			for (Iterator i = con.getRequestProperties().entrySet().iterator(); i.hasNext(); ) {
				Map.Entry mapEntry = (Map.Entry) i.next();
				if (mapEntry.getKey() != null) {
					String key = mapEntry.getKey().toString();
					String value = ((List) mapEntry.getValue()).get(0).toString();
					requestHeaders.append(key).append("=").append(value).append("\n");
				}
			}
			proxyBean.setRequestHeaders(requestHeaders.toString());

			proxyBean.setRequestHeaders(requestHeaders.toString());
			LOGGER.debug("requestHeaders:\n{}", proxyBean.getRequestHeaders());

			con.connect();

			if (proxyBean.getMethod().equals("POST")) {

				BufferedInputStream clientToProxyBuf = new BufferedInputStream(request.getInputStream());
				BufferedOutputStream proxyToWebBuf = new BufferedOutputStream(con.getOutputStream());

				// Warning: Posting binary will cause problems
				ByteArrayOutputStream postContent = new ByteArrayOutputStream();

				while ((oneByte = clientToProxyBuf.read()) != -1) {
					proxyToWebBuf.write(oneByte);
					postContent.write(oneByte);
				}

				proxyBean.setRequestBody(postContent.toString());
				LOGGER.debug("Post Content:\n{}", proxyBean.getRequestBody());
				proxyToWebBuf.flush();
				proxyToWebBuf.close();
				clientToProxyBuf.close();
				con.getOutputStream().close();
				LOGGER.debug("Post Content sent");
			}

			try {
				proxyBean.setResponseCode(con.getResponseCode());
				response.setStatus(proxyBean.getResponseCode());
			} catch (IOException ioe) {
				LOGGER.error("error reading response data", ioe);
			}

			StringBuilder responseHeaders = new StringBuilder();
			for (Iterator i = con.getHeaderFields().entrySet().iterator(); i.hasNext(); ) {
				Map.Entry mapEntry = (Map.Entry) i.next();
				if (mapEntry.getKey() != null) {
					String key = mapEntry.getKey().toString();
					String value = ((List) mapEntry.getValue()).get(0).toString();
					response.setHeader(key, value);
					responseHeaders.append(key).append("=").append(value).append("\n");
				}
			}
			proxyBean.setResponseHeaders(responseHeaders.toString());

			// Read the response

			LOGGER.debug("reading response");
			BufferedInputStream responseStream = new BufferedInputStream(getResponseEntityStream(con));
			BufferedOutputStream responseOutputStream = new BufferedOutputStream(response.getOutputStream());
			ByteArrayOutputStream responseContent = new ByteArrayOutputStream();

			while ((oneByte = responseStream.read()) != -1) {
				responseOutputStream.write(oneByte);
				responseContent.write(oneByte);
			}
			LOGGER.debug("done reading response");

			proxyBean.setResponseBody(responseContent.toString());
			LOGGER.debug("Response Content:\n{}", proxyBean.getResponseBody());

			responseOutputStream.flush();
			responseOutputStream.close();
			responseStream.close();

			con.disconnect();


		} catch (Exception e) {
			LOGGER.error("Exception proxying request", e);
		} finally {
		}
	}

	private InputStream getResponseEntityStream(HttpURLConnection connection) {
		InputStream result = null;

		try {
			result = connection.getInputStream();
		} catch (IOException ioe) {
			result = connection.getErrorStream();
		}

		if (result == null) {
			// Maybe an error stream is available instead
			result = connection.getErrorStream();
		}

		return result;
	}
}
