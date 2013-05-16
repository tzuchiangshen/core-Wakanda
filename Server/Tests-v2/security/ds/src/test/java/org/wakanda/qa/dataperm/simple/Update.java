package org.wakanda.qa.dataperm.simple;

import static org.junit.Assert.assertNotNull;

import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AUTH;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.wakanda.qa.dataperm.Targets;
import org.wakanda.qa.dataperm.extend.AbstractSecurityTestCase;


/**
 * @author ouissam.gouni@4d.com
 * 
 */
public class Update extends AbstractSecurityTestCase {

	@Override
	protected HttpHost getDefaultTarget() {
		return Targets.PERM;
	}

	@Override
	protected String getDataClassName() {
		return "Update";
	}

	@Override
	protected RestAction getRestAction() {
		return RestAction.UPDATE;
	}

	@Override
	protected User getAllowedUser() {
		return new User("update");
	}

	/**
	 * Check that "Update" action is performed when no privileges are required.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateActionIsPerformedWhenNoPermission() throws Exception {
		// Build request
		HttpRequestBase request = (HttpRequestBase) getDefaultRequest();
		request.setURI(new URI(request.getURI().toString()
				.replace(getDataClassName(), getNoPermDataClassName())));
		// Choose No Perm project
		HttpHost target = Targets.NO_PERM;
		// Execute request
		HttpResponse response = getRequestor().execute(target, request);
		// Consume response to ensure proper release of system resources
		String content = EntityUtils.toString(response.getEntity());
		logger.debug(content);
		// Should get 200 OK
		assertEqualsStatusCode(HttpStatus.SC_OK, response);
	}

	/**
	 * Check that "Update" action is not performed when the request is devoid of
	 * authentication elements ie. session cookie or autorization header.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateActionIsNotPerformedWhenRequestIsDevoidOfAuth()
			throws Exception {
		// Execute request
		HttpResponse response = executeRequest();
		// Ensure proper release of system resources
		EntityUtils.consume(response.getEntity());
		// Should get 401 Unauthorized
		assertEqualsStatusCode(HttpStatus.SC_UNAUTHORIZED, response);
		Header challenge = response.getFirstHeader(AUTH.WWW_AUTH);
		// Should get the authentication challenge too
		assertNotNull(AUTH.WWW_AUTH + " header is missing", challenge);
	}

	/**
	 * Check that "Update" action is not performed when the user is not
	 * authenticated ie. does not even exist.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateActionIsNotPerformedWhenNotAuthenticated()
			throws Exception {
		// Get the user that does not belong to solution directory
		User user = getNonAuthenticatedUser();
		// Execute request
		HttpResponse response = executeAuthenticatedRequest(user);
		// Ensure proper release of system resources
		EntityUtils.consume(response.getEntity());
		// Should get 401 Unauthorized
		assertEqualsStatusCode(HttpStatus.SC_UNAUTHORIZED, response);
	}

	/**
	 * Check that "Update" action is not performed when the user is
	 * authenticated but not allowed.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateActionIsNotPerformedWhenAuthenticatedButNotAllowed()
			throws Exception {
		// Get an authenticated but not allowed user
		User user = getAuthenticatedButNotAllowedUser();
		// Execute request
		HttpResponse response = executeAuthenticatedRequest(user);
		// Ensure proper release of system resources
		EntityUtils.consume(response.getEntity());
		// Should get 401 Unauthorized
		assertEqualsStatusCode(HttpStatus.SC_UNAUTHORIZED, response);
	}

	/**
	 * Check that "Update" action is performed when the user is authenticated
	 * and allowed.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateActionIsPerformedWhenAuthenticatedAndAllowed()
			throws Exception {
		// Get the user allowed
		User user = getAllowedUser();
		// Execute request
		HttpResponse response = executeAuthenticatedRequest(user);
		// Ensure proper release of system resources
		EntityUtils.consume(response.getEntity());
		// Should get 200 OK
		assertEqualsStatusCode(HttpStatus.SC_OK, response);
	}

}