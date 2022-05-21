package br.com.adamastor.eleicao.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.com.adamastor.eleicao.model.entity.Candidato;
import lombok.Data;

@Data
public class CandidatoRequestDTO implements Serializable {

	private static final long serialVersionUID = -1760358246776668745L;

	private String nome;
	
	private String cpf;

	private Integer numero;

	private String legenda;

	private Long idCargo;
	
	private Boolean ativo;
	
	public Candidato gerarCandidato() {
		Candidato c = new Candidato();
		c.setNome(nome.toUpperCase());
		c.setCpf(cpf);
		c.setNumero(numero);
		c.setLegenda(legenda.toUpperCase());
		c.setVotado(false);
		c.setAtivo(ativo);
		c.setCriadoEm(LocalDateTime.now());
		return c;
	}
}
