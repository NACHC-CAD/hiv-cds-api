package com.nachc.hivcds.util.auth;

import org.nachc.tools.fhirtoomop.fhir.util.server.auth.HttpClientAuthenticator;

import com.nach.core.util.http.HttpRequestClient;
import com.nachc.hivcds.api.hashivtest.HasHivTest;

public class NullAuthenticator implements HttpClientAuthenticator {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HasHivTest.class);	

	@Override
	public void init() {
		log.info("Token not required");
	}

	@Override
	public void refresh() {
		log.info("Token not required");
	}

	@Override
	public void addAuth(HttpRequestClient client) {
		log.info("Token not required");
	}

}
