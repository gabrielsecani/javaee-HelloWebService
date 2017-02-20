package br.com.bancocbss.parcele.endpoints.rest;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.log4j.Logger;

@ApplicationPath("/rest")
public class RestApplication extends Application{
	@Inject
	Logger logger;

	public RestApplication() {
		super();
		logger.info("Rest application was started.");
	}
}