package br.com.adamastor.eleicao.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.adamastor.eleicao.model.entity.Candidato;
import br.com.adamastor.eleicao.model.entity.Cargo;
import lombok.Data;

@Data
public class CandidatoResponseDTO implements Serializable {

	private static final long serialVersionUID = -9007257472946893386L;

	private Long id;

	private String nome;
	
	private String cpf;

	private Integer numero;
	
	private String legenda;
	
	private Cargo cargo;

	private LocalDateTime criadoEm;

	private LocalDateTime alteradoEm;

	private LocalDateTime desativadoEm;

	private Boolean ativo;
	
	public CandidatoResponseDTO(Candidato candidato) {
		this.id = candidato.getId();
		this.nome = candidato.getNome();
		this.cpf = candidato.getCpf();
		this.numero = candidato.getNumero();
		this.legenda = candidato.getLegenda();
		this.cargo = candidato.getCargo();
		this.criadoEm = candidato.getCriadoEm();
		this.alteradoEm = candidato.getAlteradoEm();
		this.desativadoEm = candidato.getDesativadoEm();
		this.ativo = candidato.isAtivo();
	}
	
	public static List<CandidatoResponseDTO> converter(List<Candidato> candidatos){
		return candidatos.stream().map(CandidatoResponseDTO::new).collect(Collectors.toList());
	}
}
