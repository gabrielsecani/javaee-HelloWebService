package br.com.bancocbss.parcele.model.service;

import javax.ejb.Stateless;

import br.com.bancocbss.parcele.model.viewobject.ClienteVO;
import br.com.bancocbss.parcele.service.IClienteService;

@Stateless
public class ClienteServiceImpl implements  IClienteService {

	public ClienteServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ClienteVO getClienteVO(String nome, String sobrenome) {
		return new ClienteVO(nome, sobrenome);
	}

}


