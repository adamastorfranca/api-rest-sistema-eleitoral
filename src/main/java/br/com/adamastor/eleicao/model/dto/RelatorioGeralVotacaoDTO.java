package br.com.adamastor.eleicao.model.dto;

import lombok.Data;

@Data
public class RelatorioGeralVotacaoDTO {
	
	private Long idCargo;
	
	private String nomeCargo;
	
	private Integer votos;
	
	private Long idCandidatoVencedor;
	
	private String nomeCandidatoVencedor;

}
