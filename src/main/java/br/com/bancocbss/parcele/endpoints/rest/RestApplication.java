package br.com.bancocbss.parcele.endpoints.rest;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.log4j.Logger;

@ApplicationPath("/rest")
public class RestApplication extends Application{
	
	@Inject
	private Logger log;

	public RestApplication() {
		super();
		log.info("-------->  Rest application was started.");
	}
}