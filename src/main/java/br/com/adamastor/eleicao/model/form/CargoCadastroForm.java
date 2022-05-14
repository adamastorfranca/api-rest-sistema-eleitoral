package br.com.adamastor.eleicao.model.form;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.adamastor.eleicao.model.entity.Cargo;
import lombok.Data;

@Data
public class CargoCadastroForm {

	@NotBlank
	@Size(min = 4, max = 100)
	private String nome;
	
	private boolean ativo;
	
	public Cargo gerarCargo() {
		Cargo c = new Cargo();
		c.setNome(nome.toUpperCase());
		c.setCriadoEm(LocalDateTime.now());
		c.setAtivo(ativo);
		return c;
	}
}
