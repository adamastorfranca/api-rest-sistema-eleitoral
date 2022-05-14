package br.com.adamastor.eleicao.model.form;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import br.com.adamastor.eleicao.model.entity.Eleitor;
import lombok.Data;

@Data
public class EleitorCadastroForm {

	@NotBlank
	@Size(min = 10, max = 100)
	private String nome;
	
	@NotBlank
	@CPF
	private String cpf;
	
	private boolean ativo;
	
	public Eleitor gerarEleitor() {
		Eleitor e = new Eleitor();
		e.setNome(nome.toUpperCase());
		e.setCpf(cpf);
		e.setCriadoEm(LocalDateTime.now());
		e.setAtivo(ativo);
		return e;
	}
}
