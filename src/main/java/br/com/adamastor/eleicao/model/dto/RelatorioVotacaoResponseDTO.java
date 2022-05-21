package br.com.adamastor.eleicao.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class RelatorioVotacaoResponseDTO implements Serializable {

	private static final long serialVersionUID = 8330302073289553721L;

	private String nomeCargo;
	
	private String nomeCandidato;
	
	private Integer numeroCandidato;
	
	private String legendaCandidato;
	
	private Integer votos;
	
	


}
