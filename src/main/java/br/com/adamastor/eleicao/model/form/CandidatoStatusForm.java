package br.com.adamastor.eleicao.model.form;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CandidatoStatusForm {
	
	@NotNull
	private Long id;

	private boolean ativo;
}