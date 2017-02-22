package br.com.bancocbss.parcele.resources;

import javax.annotation.Resource;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Resources {

	@Produces
	@PersistenceContext
	private EntityManager em;

	@Produces
	public Logger produceLog(InjectionPoint injectionPoint) {
		return LogManager.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}
	
	/* Note that this resource has to be configured in standalone.xml (or domain.xml if you're in domain mode) */
	/* Used by MailService */
	@Produces
	@Resource(mappedName = "java:jboss/mail/ParceleMail")
	private Session mailSession;
}
