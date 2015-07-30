package org.frank.rest4j.logic;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

/**
 * Created by FrankJ on 22.7.2015.
 */
public class RestMethod {

	private String urlFragment;

	private HttpMethod method;

	private HttpStatus successStatus;

	private Class returnType;

	public String getUrlFragment() {
		return urlFragment;
	}

	public void setUrlFragment(String urlFragment) {
		this.urlFragment = urlFragment;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public Class getReturnType() {
		return returnType;
	}

	public void setReturnType(Class returnType) {
		this.returnType = returnType;
	}

	public String getLink(String server) {
		return server + urlFragment;
	}

	public HttpStatus getSuccessStatus() {
		return successStatus;
	}

	public void setSuccessStatus(HttpStatus successStatus) {
		this.successStatus = successStatus;
	}
}
