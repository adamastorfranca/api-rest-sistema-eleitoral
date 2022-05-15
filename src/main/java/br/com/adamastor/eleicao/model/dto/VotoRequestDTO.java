package br.com.adamastor.eleicao.model.dto;

import lombok.Data;

@Data
public class VotoRequestDTO {
	
	private Long idEleitor;
	
	private Long idCargo;
	
	private Long idCandidato;

}
