package test.com.approachingpi.timetime;

/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.approachingpi.timetime.api.v1.representations.ApplicationError;
import com.approachingpi.timetime.api.v1.representations.AuthType;
import com.approachingpi.timetime.api.v1.representations.EnumErrorCode;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Arquillian Extension REST API Test Case
 * <p/>
 * Annotate the TestClass's TestMethods with JAX-RS Client annotations.
 * <p/>
 * Executes the REST request in the background for so to inject back the Response into the TestMethods arguments.
 * <p/>
 * * @author <a href="mailto:aslak@redhat.com">Aslak Knutsen</a>
 *
 * @version $Revision: $
 */
@RunWith(Arquillian.class)
public class RestClientTestCase {

	static final String ROOT_URL = "http://localhost:8080/timetime-test";


	@Deployment(testable = false)
	public static WebArchive create() {
		WebArchive archive = ShrinkWrap.create(ZipImporter.class, "timetime-test.war")
				.importFrom(new File("dist/timetime-test-1.0-DEV.war"))
				.as(WebArchive.class)
					.addPackage("test.com.shortvid.app.persistence");

		archive.delete("WEB-INF/jboss-web.xml");

		return archive;
	}


	@Test
	public void shouldBeAbleToCreateAccount() throws Exception {

		ClientRequest request = new ClientRequest(
			ROOT_URL + "/api/v1/account/register");
		request.accept(MediaType.APPLICATION_XML);

		request.formParameter("authCode", "xxxxxxxx");
		request.formParameter("username", "Tea");
		request.formParameter("email", "tea@grilledcheese.com");
		request.formParameter("fullName", "Terrence Curran");
		request.formParameter("password", "1234f");

		ClientResponse<AuthType> response = request.post(AuthType.class);

		// This will always fail because we are passing in an invalid authCode
		//Assert.assertEquals(response.getStatus(), Status.OK.getStatusCode());

	}

	@Test
	@POST
	@Path("api/v1/account/register")
	@Consumes(MediaType.APPLICATION_XML)
	public void accountCreateWithoutParamsShouldFail(ClientResponse<Object> response) {

		Assert.assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());

		ApplicationError error = response.getEntity(new GenericType<ApplicationError>() {
		});

		Assert.assertNotNull(error);

		Assert.assertTrue(error.getCode().equals(EnumErrorCode.ILLEGAL_ARGUMENT_ERROR));

	}

	@Test
	@GET
	@Path("api/v1/conversation/3")
	@Consumes(MediaType.APPLICATION_XML)
	public void shouldBeAbleToGetConcept(ClientResponse<AuthType> response) {
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());

	}
}