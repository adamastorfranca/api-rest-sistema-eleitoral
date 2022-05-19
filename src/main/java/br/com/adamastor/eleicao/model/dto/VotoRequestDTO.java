package br.com.adamastor.eleicao.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class VotoRequestDTO implements Serializable {

	private static final long serialVersionUID = -4221323463887323507L;

	Long idEleitor;
	
	private Long idCargo;
	
	private Long idCandidato;

}
