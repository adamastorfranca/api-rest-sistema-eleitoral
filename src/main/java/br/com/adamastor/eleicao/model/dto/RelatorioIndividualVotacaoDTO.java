package br.com.adamastor.eleicao.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class RelatorioIndividualVotacaoDTO implements Serializable {

	private static final long serialVersionUID = 6255466070553315318L;

	private String nomeCargo;
	
	private String nomeCandidato;
	
	private Integer votos;

}
