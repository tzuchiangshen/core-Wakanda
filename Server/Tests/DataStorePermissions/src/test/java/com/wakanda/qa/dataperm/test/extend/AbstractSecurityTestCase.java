package com.wakanda.qa.dataperm.test.extend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.wakanda.qa.utils.JsonUtil;
import com.wakanda.qa.utils.JsonUtil.JsonEntity;
import com.wakanda.qa.utils.JsonUtil.JsonResponse;

/**
 * Abstract class that provides common test cases utilities.
 * 
 * @author ouissam.gouni@4d.com
 * 
 */
public abstract class AbstractSecurityTestCase extends AbstractTestCase {

	protected Logger logger = Logger.getLogger(this.getClass());

	public enum RestAction {
		NONE, READ, CREATE, UPDATE, REMOVE, EXECUTE
	}

	protected HttpHost getDefaultTarget() {
		return null;
	}

	protected String getDataClassName() {
		return null;
	}

	protected String getDataClassMethodName() {
		return null;
	}

	protected String getNoPermDataClassName() {
		return "NoPerm";
	}

	protected String getNoPermDataClassMethodName() {
		return "unsecured";
	}

	protected JsonEntity getFirstEntity(String dataClass, HttpHost target) {
		JsonEntity record = null;
		try {
			// Get the entity
			HttpRequest request = getRequestForDataClassAction(RestAction.READ,
					dataClass);
			HttpResponse response = getRequestor().execute(target, request);
			// Should get a 200 OK
			int status = response.getStatusLine().getStatusCode();
			assertEquals(HttpStatus.SC_OK, status);

			// Map JSON content to objects
			String content = EntityUtils.toString(response.getEntity());
			logger.debug(content);
			JsonResponse respJSON = new Gson().fromJson(content,
					JsonResponse.class);

			// Get entities list
			JsonEntity[] allRecords = respJSON.getEntities();
			assertNotNull("No entity returned", allRecords);
			assertTrue("No entity returned", allRecords.length > 0);

			// Choose the first entity
			record = allRecords[0];
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return record;
	}

	protected HttpRequest getRequestForDataClassMethod(String dataClass,
			String method) {
		String url = "/rest/" + dataClass + "/" + method;
		HttpGet request = new HttpGet(url);
		return request;
	}

	protected RestAction getRestAction() {
		return RestAction.NONE;
	}

	protected HttpRequest getRequestForDataClassAction(RestAction action,
			String dataClass, HttpHost target) {
		HttpRequest request = null;
		JsonEntity entity = null;
		String newValue = (new Date()).toString();
		try {
			switch (action) {
			case READ:
				request = JsonUtil.getRestRequestForRead(dataClass, target);
				break;
			case CREATE:
				entity = new JsonEntity(newValue);
				request = JsonUtil.getRestRequestForUpdate(dataClass, entity);
				break;
			case UPDATE:
				entity = getFirstEntity(dataClass, target);
				entity.setName(newValue);
				request = JsonUtil.getRestRequestForUpdate(dataClass, entity);
				break;
			case REMOVE:
				entity = getFirstEntity(dataClass, target);
				request = JsonUtil.getRestRequestForDelete(dataClass, entity);
				break;
			default:
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return request;
	}

	protected HttpRequest getRequestForDataClassAction(RestAction action,
			String dataClass) {
		return getRequestForDataClassAction(action, dataClass,
				getDefaultTarget());
	}

	protected HttpRequest getDefaultRequest() throws Exception {
		RestAction action = getRestAction();
		if (action == RestAction.EXECUTE) {
			return getRequestForDataClassMethod(getDataClassName(),
					getDataClassMethodName());
		}
		return getRequestForDataClassAction(action, getDataClassName());
	}

	protected User getAdminUser() {
		return new User("admin");
	}

	protected User getNonAuthenticatedUser() {
		return new User("wrong");
	}

	protected User getAuthenticatedButNotAllowedUser() {
		return new User("noperm");
	}

	protected User getAllowedUser() {
		return getAdminUser();
	}

	protected User getDefaultUser() throws Exception {
		return getAllowedUser();
	}

	protected HttpResponse executeAuthenticatedRequest(HttpHost target,
			HttpRequest request, User user) throws Exception {
		return getRequestor().executeAuthenticated(target, request,
				user.getName(), user.getPassword());
	}

	protected HttpResponse executeAuthenticatedRequest(HttpRequest request,
			User user) throws Exception {
		return executeAuthenticatedRequest(getDefaultTarget(), request, user);
	}

	protected HttpResponse executeAuthenticatedRequest(HttpRequest request)
			throws Exception {
		return executeAuthenticatedRequest(getDefaultTarget(), request,
				getDefaultUser());
	}

	protected HttpResponse executeAuthenticatedRequest(HttpRequest request,
			HttpHost target) throws Exception {
		return executeAuthenticatedRequest(target, request, getDefaultUser());
	}

	protected HttpResponse executeAuthenticatedRequest(User user)
			throws Exception {
		return executeAuthenticatedRequest(getDefaultRequest(), user);
	}

	public class User {

		private String name;
		private String password;

		public User(String name, String password) {
			super();
			this.name = name;
			this.password = password;
		}

		public User(String nameSameAsPwd) {
			super();
			this.name = nameSameAsPwd;
			this.password = nameSameAsPwd;

		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getPassword() {
			return password;
		}

	}

}