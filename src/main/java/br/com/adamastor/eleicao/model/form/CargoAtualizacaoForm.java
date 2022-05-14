package br.com.adamastor.eleicao.model.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CargoAtualizacaoForm {

	private Long id;
	
	@NotBlank
	@Size(min = 4, max = 100)
	private String nome;
	
	private boolean ativo;
}
