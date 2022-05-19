package br.com.adamastor.eleicao.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.com.adamastor.eleicao.model.entity.Cargo;
import lombok.Data;

@Data
public class CargoRequestDTO implements Serializable {

	private static final long serialVersionUID = -6363212527410137180L;

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
