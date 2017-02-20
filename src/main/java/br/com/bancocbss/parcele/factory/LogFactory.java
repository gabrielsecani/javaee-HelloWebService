package br.com.bancocbss.parcele.factory;

import javax.enterprise.inject.spi.InjectionPoint;
import org.apache.log4j.Logger;
import javax.enterprise.inject.Produces;

class LogFactory {
	@Produces
	Logger createLogger(InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}
}