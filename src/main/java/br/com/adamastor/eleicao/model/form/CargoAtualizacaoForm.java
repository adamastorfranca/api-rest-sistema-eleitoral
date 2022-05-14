package br.com.adamastor.eleicao.model.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CargoAtualizacaoForm {

	@NotNull
	private Long id;
	
	@NotBlank
	@Size(min = 4, max = 50)
	private String nome;
	
	private boolean ativo;
}