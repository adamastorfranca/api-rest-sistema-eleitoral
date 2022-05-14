package br.com.adamastor.eleicao.model.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CandidatoAtualizacaoForm {

	@NotNull
	private Long id;
	
	@NotBlank
	@Size(min = 10, max = 100)
	private String nome;
	
	@NotNull
	private Integer numero;
	
	@NotBlank
	private String legenda;
	
	private boolean ativo;
}
