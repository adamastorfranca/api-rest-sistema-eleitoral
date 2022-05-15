package br.com.adamastor.eleicao.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.adamastor.eleicao.model.entity.Cargo;
import lombok.Data;

@Data
public class CargoResponseDTO implements Serializable {

	private static final long serialVersionUID = -7552168425411443299L;

	private Long id;
	
	private String nome;

	private LocalDateTime criadoEm;

	private LocalDateTime alteradoEm;
	
	private LocalDateTime desativadoEm;
	
	private Boolean ativo;
	
	public CargoResponseDTO(Cargo cargo) {
		this.id = cargo.getId();
		this.nome = cargo.getNome();
		this.criadoEm = cargo.getCriadoEm();
		this.alteradoEm = cargo.getAlteradoEm();
		this.desativadoEm = cargo.getDesativadoEm();
		this.ativo = cargo.isAtivo();
	}
	
	public static List<CargoResponseDTO> converter(List<Cargo> cargos){
		return cargos.stream().map(CargoResponseDTO::new).collect(Collectors.toList());
	}
}
