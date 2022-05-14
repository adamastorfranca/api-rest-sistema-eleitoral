package br.com.adamastor.eleicao.model.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.adamastor.eleicao.model.entity.Candidato;
import lombok.Data;

@Data
public class CandidatoDTO {

	private Long id;

	private String nome;
	
	private String cpf;

	private Integer numero;
	
	private String legenda;

	private LocalDateTime criadoEm;

	private LocalDateTime alteradoEm;

	private LocalDateTime desativadoEm;

	private boolean ativo;
	
	public CandidatoDTO(Candidato candidato) {
		this.id = candidato.getId();
		this.nome = candidato.getNome();
		this.cpf = candidato.getCpf();
		this.numero = candidato.getNumero();
		this.legenda = candidato.getLegenda();
		this.criadoEm = candidato.getCriadoEm();
		this.alteradoEm = candidato.getAlteradoEm();
		this.desativadoEm = candidato.getDesativadoEm();
		this.ativo = candidato.isAtivo();
	}
	
	public static List<CandidatoDTO> converter(List<Candidato> candidatos){
		return candidatos.stream().map(CandidatoDTO::new).collect(Collectors.toList());
	}
}
