package br.com.adamastor.eleicao.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class RelatorioGeralVotacaoDTO implements Serializable {

	private static final long serialVersionUID = 8330302073289553721L;

	private Long idCargo;
	
	private String nomeCargo;
	
	private Integer votos;
	
	private Long idCandidatoVencedor;
	
	private String nomeCandidatoVencedor;

}
