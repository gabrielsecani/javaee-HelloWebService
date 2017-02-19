package br.com.bancocbss.parcele.service;

import javax.ejb.Local;

import br.com.bancocbss.parcele.model.viewobject.ClienteVO;

@Local
public interface IClienteService {

	public ClienteVO getClienteVO(String name, String sobrenome);

}
