package com.approachingpi.timetime.api.oauth2.provider.services;

import java.net.URI;
import java.util.Properties;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import com.approachingpi.timetime.data.model.ApiApplication;
import com.approachingpi.timetime.data.model.Authorization;
import com.approachingpi.timetime.services.ApiApplicationService;
import com.approachingpi.timetime.services.AuthorizationService;
import com.approachingpi.timetime.services.SessionService;
import org.apache.amber.oauth2.as.issuer.MD5Generator;
import org.apache.amber.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.amber.oauth2.as.request.OAuthAuthzRequest;
import org.apache.amber.oauth2.as.response.OAuthASResponse;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.message.OAuthResponse;
import org.apache.amber.oauth2.common.message.types.ResponseType;
import org.apache.amber.oauth2.common.utils.OAuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class OAuth2ProviderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2ProviderService.class);

	@PersistenceContext
	protected EntityManager em;

	@Inject
	SessionService sessionService;

	@Inject
	ApiApplicationService apiApplicationService;

	@Inject
	HttpServletRequest httpRequest;

	@Inject
	HttpServletResponse httpResponse;

	@Inject
	private ServletContext context;

	@Inject
	AuthorizationService authorizationService;

	private static Properties consumerProperties = null;

	protected String clientId;

	public String authorize() throws Exception {

		ApiApplication apiApplication = apiApplicationService.findById(clientId);

		if (sessionService.getAccount() != null) {
			// user has already granted permission to this applciation
			Authorization authorization = authorizationService.findExistingAuthorization(apiApplication, sessionService.getAccount());

			if (authorization != null) {

				OAuthAuthzRequest oauthRequest = null;
				OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());

				try {
					oauthRequest = new OAuthAuthzRequest(httpRequest);

					//build response according to response_type
					String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);

					OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse
							.authorizationResponse(httpRequest, HttpServletResponse.SC_FOUND);

					if (responseType.equals(ResponseType.CODE.toString())) {
						// update the request code with each request
						authorization.setRequestCode(oauthIssuerImpl.authorizationCode());
						builder.setCode(authorization.getRequestCode());
						em.merge(authorization);
					}
					if (responseType.equals(ResponseType.TOKEN.toString())) {
						builder.setAccessToken(authorization.getToken());
						//builder.setExpiresIn(3600l);
					}

					// TODO: make sure the redirect URI is authorized to this account.
					String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);

					final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();
					URI url = new URI(response.getLocationUri());
					httpResponse.sendRedirect(url.toString());

				} catch (OAuthProblemException e) {

					final Response.ResponseBuilder responseBuilder = Response.status(HttpServletResponse.SC_FOUND);

					String redirectUri = e.getRedirectUri();

					if (OAuthUtils.isEmpty(redirectUri)) {
						throw new Exception("OAuth callback url needs to be provided by client!!!");
					}
					final OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND)
							.error(e)
							.location(redirectUri).buildQueryMessage();
					final URI location = new URI(response.getLocationUri());

					httpResponse.sendRedirect(location.toString());

				} catch (OAuthSystemException e) {
					throw new ServletException(e);
				}
			}

		}

		return "success";
	}




}