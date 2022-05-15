package br.com.adamastor.eleicao.model.dto;

import java.time.LocalDateTime;

import br.com.adamastor.eleicao.model.entity.Eleitor;
import lombok.Data;

@Data
public class EleitorRequestDTO {

	private String nome;

	private String cpf;
	
	private Boolean ativo;
	
	public Eleitor gerarEleitor() {
		Eleitor e = new Eleitor();
		e.setNome(nome.toUpperCase());
		e.setCpf(cpf);
		e.setCriadoEm(LocalDateTime.now());
		e.setAtivo(ativo);
		return e;
	}
}
