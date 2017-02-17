package br.com.bancocbss.parcele.model.viewobject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ClienteVO {
	private String nome;
	private String sobrenome;

	public ClienteVO() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public ClienteVO(String nome, String sobrenome) {
		super();
		this.nome = nome;
		this.sobrenome = sobrenome;
	}

}
