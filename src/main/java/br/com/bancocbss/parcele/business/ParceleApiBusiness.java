package br.com.bancocbss.parcele.business;

import java.util.concurrent.atomic.AtomicLong;

import javax.enterprise.context.RequestScoped;

import br.com.bancocbss.parcele.model.Cliente;

@RequestScoped
public class ParceleApiBusiness {
	
	private static final String template = "Hello, %s!";
	
	private final AtomicLong counter = new AtomicLong();

	public Cliente recuperaCliente(String name){
		return new Cliente(counter.incrementAndGet(), String.format(template, name));
	}
	
}
