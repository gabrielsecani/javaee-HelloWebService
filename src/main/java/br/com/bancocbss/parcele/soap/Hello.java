package br.com.bancocbss.parcele.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceRef;

import org.apache.log4j.Logger;

import br.com.bancocbss.parcele.model.Cliente;

@WebService()
public class Hello {
	final static Logger logger = Logger.getLogger(Hello.class);

	@WebMethod
	public String sayHello(String name) {
		logger.info("sayHello " + name);

		System.out.println("Webservice sayHello called...");
		return "Hello " + name;
	}

	@WebMethod
	public String sayHi(String name) {
		logger.info("sayHi" + name);
		System.out.println("Webservice sayHi called...");
		return "Hi there, " + name;
	}

	@WebMethod
	public Cliente Cliente(String name, String sobrenome) {
		logger.info("getCliente"+name+" "+sobrenome);
		System.out.println("Webservice getCliente called...");
		return new Cliente(name, sobrenome);
	}
	
	@WebMethod
	public Cliente EchoCliente(Cliente cliente) {
		logger.info("echo Cliente");
		System.out.println("Webservice getCliente called...");
		return new Cliente(cliente.getSobrenome(), cliente.getNome());
	}
}
