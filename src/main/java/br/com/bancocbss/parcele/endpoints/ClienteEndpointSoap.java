package br.com.bancocbss.parcele.endpoints;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import br.com.bancocbss.parcele.model.ClienteRepository;

@WebService(serviceName="cliente",name="cliente")
public class ClienteEndpointSoap {
	@Inject
	Logger logger;
	
	@Inject
	ClienteRepository clienteRepository;

	@WebMethod
	public String sayHello(String name) {
		logger.info("sayHello " + name);
		System.out.println("Webservice sayHello called...");
		return "Hello " + name;
	}
	
	@WebMethod
	public br.com.bancocbss.parcele.model.Cliente getCliente(String nome){
		return clienteRepository.findByNome(nome);
	}
}
