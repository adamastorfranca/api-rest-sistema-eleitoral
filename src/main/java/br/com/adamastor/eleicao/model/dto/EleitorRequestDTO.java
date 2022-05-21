package br.com.adamastor.eleicao.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.com.adamastor.eleicao.model.entity.Eleitor;
import lombok.Data;

@Data
public class EleitorRequestDTO implements Serializable {

	private static final long serialVersionUID = -8578249447885331282L;

	private String nome;

	private String cpf;
	
	private Boolean ativo;
	
	public Eleitor gerarEleitor() {
		Eleitor e = new Eleitor();
		e.setNome(nome.toUpperCase());
		e.setCpf(cpf);
		e.setCriadoEm(LocalDateTime.now());
		if(!ativo.booleanValue()) {
			e.setDesativadoEm(LocalDateTime.now());
		}
		e.setVotou(false);
		e.setAtivo(ativo);
		return e;
	}
}
