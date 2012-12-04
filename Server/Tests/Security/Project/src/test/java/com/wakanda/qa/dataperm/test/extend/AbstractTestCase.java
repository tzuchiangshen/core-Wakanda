package com.wakanda.qa.dataperm.test.extend;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import com.wakanda.qa.dataperm.Settings;
import com.wakanda.qa.utils.IBasicSettings;
import com.wakanda.qa.utils.Requestor;


/**
 * Abstract class that provides common test cases utilities.
 * 
 * @author ouissam.gouni@4d.com
 * 
 */
public abstract class AbstractTestCase extends com.wakanda.qa.utils.AbstractTestCase {

	/**
	 * @return
	 */
	protected abstract HttpHost getDefaultTarget();
	
	/**
	 * @return
	 * @throws Exception 
	 */
	protected abstract HttpRequest getDefaultRequest() throws Exception;

	protected HttpResponse executeRequest(HttpRequest request, HttpHost target)
			throws Exception {
		HttpResponse response = getRequestor().execute(target, request);
		return response;
	}

	protected HttpResponse executeRequest(HttpRequest request) throws Exception {
		HttpResponse response = executeRequest(request, getDefaultTarget());
		return response;
	}
	
	/**
	 * Executes the default request.
	 * @return
	 * @throws Exception
	 */
	protected HttpResponse executeRequest() throws Exception {
		HttpResponse response = executeRequest(getDefaultRequest());
		return response;
	}


	@Override
	protected IBasicSettings getDefaultSettings() {
		return Settings.getSettingsInstance();
	}
	
	@Override
	protected Requestor getRequestor() {
		return Settings.getRequestorInstance();
	}
	
	

}