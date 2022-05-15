package br.com.adamastor.eleicao.model.form;

import lombok.Data;

@Data
public class VotoForm {
	
	private Long idEleitor;
	
	private Long idCargo;
	
	private Long idCandidato;

}
