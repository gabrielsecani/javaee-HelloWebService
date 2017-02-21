package br.com.bancocbss.parcele.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.bancocbss.parcele.model.Cliente;
import br.com.bancocbss.parcele.model.ClienteRepository;
import br.com.bancocbss.parcele.model.viewobject.ClienteVO;

@Stateless
public class ClienteService {

	@Inject
	ClienteRepository repo;

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
	}

	public Cliente findByID(Long id) {
		return repo.findById(id);
	}

}
