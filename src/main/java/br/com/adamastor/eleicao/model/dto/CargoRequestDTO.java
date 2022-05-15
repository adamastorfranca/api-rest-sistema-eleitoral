package br.com.adamastor.eleicao.model.dto;

import java.time.LocalDateTime;

import br.com.adamastor.eleicao.model.entity.Cargo;
import lombok.Data;

@Data
public class CargoRequestDTO {

	private String nome;
	
	private Boolean ativo;
	
	public Cargo gerarCargo() {
		Cargo c = new Cargo();
		c.setNome(nome.toUpperCase());
		c.setCriadoEm(LocalDateTime.now());
		c.setAtivo(ativo);
		return c;
	}
}
