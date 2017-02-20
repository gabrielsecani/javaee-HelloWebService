package br.com.bancocbss.parcele.endpoints.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import br.com.bancocbss.parcele.model.viewobject.ClienteVO;
import br.com.bancocbss.parcele.service.IClienteService;

@Path("/teste")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class RestEndpoint {

	// final static Logger logger = Logger.getLogger(RestEndpoint.class);
	@Inject
	Logger logger;

	@Inject
	private IClienteService clienteService;

	@GET
	@Path("hello")
	public String sayHello() {
		logger.info("sayHello");
		return "Hello";
	}

	@GET
	@Path("/name/{name}")
	public String sayName(@PathParam("name") String name) {
		logger.info("sayName " + name);
		return "The name is: " + (name) + "!";
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String sayHi(String name) {
		logger.info("sayHi " + name);
		return "Hello, The name is: " + (name) + "!";
	}

	@GET
	@Path("/cliente/{name}-{sobrenome}")
	public ClienteVO getCliente(@PathParam("name") String name, @PathParam("sobrenome") String sobrenome) {
		logger.info("rest getCliente " + name + " " + sobrenome);
		System.out.println("Rest getCliente called...");
		return clienteService.getClienteVO(name, sobrenome);
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/echocliente")
	public ClienteVO EchoCliente(ClienteVO cliente) {
		logger.info("echo Cliente");
		System.out.println("Rest getCliente called...");
		cliente.setNome(cliente.getNome() + "..");
		return cliente;
	}
}
