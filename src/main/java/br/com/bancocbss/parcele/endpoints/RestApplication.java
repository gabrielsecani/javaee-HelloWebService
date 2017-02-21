package br.com.bancocbss.parcele.endpoints;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.log4j.Logger;

@ApplicationPath("/rest")
public class RestApplication extends Application{

	public RestApplication() {
		super();
		Logger.getLogger(this.getClass()).info("--->  Rest application was started.");
	}
	
}