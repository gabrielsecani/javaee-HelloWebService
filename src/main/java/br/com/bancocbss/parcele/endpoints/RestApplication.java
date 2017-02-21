package br.com.bancocbss.parcele.endpoints;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

@ApplicationPath("/rest")
public class RestApplication extends Application{

	public RestApplication() {
		super();
		Logger log = Logger.getLogger(this.getClass());
//		this.getClasses().forEach(a->log.info("Rest Classes: "+a.getClass().getName()));
//		this.getSingletons().forEach(a->log.info("Rest Singletons: "+a.getClass().getName()));
//		this.getProperties().forEach((a,b)->log.info("Rest Properties: "+a+"="+b ));
		log.info("--->  Rest application was started.");
	}

}