package br.com.bancocbss.parcele.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import br.com.bancocbss.parcele.model.Cliente;
import br.com.bancocbss.parcele.model.ClienteRepository;
import br.com.bancocbss.parcele.resources.MailEvent;


@ApplicationScoped
public class ClienteService {

	@Inject
	ClienteRepository repo;
    
    @Inject
    private Event<MailEvent> eventProducer;

	public ClienteService() {
		
	}

	public List<Cliente> getAll() {
		return repo.findAllOrderedByName();
	}

	public Cliente getCliente(String nome) {
		return repo.findByNome(nome);
	}

	public void addCliente(Cliente cliente) throws Exception {
		repo.add(cliente);
		sendEmail();
	}

	public Cliente findByID(Long id) {
		return repo.findById(id);
	}

	private void sendEmail() {
		MailEvent event = new MailEvent();
		event.setTo("gabriel.ribeiro@castgroup.com.br");
		event.setSubject("Async email testing");
		event.setMessage("Testing email");
		eventProducer.fire(event);
	}
}
