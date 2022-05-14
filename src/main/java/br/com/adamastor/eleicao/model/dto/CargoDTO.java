package br.com.adamastor.eleicao.model.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.adamastor.eleicao.model.entity.Cargo;
import lombok.Data;

@Data
public class CargoDTO {

	private Long id;
	
	private String nome;

	private LocalDateTime criadoEm;

	private LocalDateTime alteradoEm;
	
	private LocalDateTime desativadoEm;
	
	private Boolean ativo;
	
	public CargoDTO(Cargo cargo) {
		this.id = cargo.getId();
		this.nome = cargo.getNome();
		this.criadoEm = cargo.getCriadoEm();
		this.alteradoEm = cargo.getAlteradoEm();
		this.desativadoEm = cargo.getDesativadoEm();
		this.ativo = cargo.isAtivo();
	}
	
	public static List<CargoDTO> converter(List<Cargo> cargos){
		return cargos.stream().map(CargoDTO::new).collect(Collectors.toList());
	}

}
