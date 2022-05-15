package br.com.adamastor.eleicao.model.form;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import br.com.adamastor.eleicao.model.entity.Candidato;
import lombok.Data;

@Data
public class CandidatoCadastroForm {

	@NotBlank
	@Size(min = 10, max = 100)
	private String nome;
	
	@NotBlank
	@CPF
	private String cpf;
	
	@NotNull
	private Integer numero;
	
	@NotBlank
	@Size(min = 2, max = 100)
	private String legenda;
	
	@NotNull
	private Long idCargo;
	
	private boolean ativo;
	
	public Candidato gerarCandidato() {
		Candidato c = new Candidato();
		c.setNome(nome.toUpperCase());
		c.setCpf(cpf);
		c.setNumero(numero);
		c.setLegenda(legenda.toUpperCase());
		c.setAtivo(ativo);
		c.setCriadoEm(LocalDateTime.now());
		return c;
	}
}
