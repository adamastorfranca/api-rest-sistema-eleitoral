package br.com.adamastor.eleicao.model.dto;

import lombok.Data;

@Data
public class RelatorioIndividualVotacaoDTO {

	private String nomeCargo;
	
	private String nomeCandidato;
	
	private Integer votos;

}
