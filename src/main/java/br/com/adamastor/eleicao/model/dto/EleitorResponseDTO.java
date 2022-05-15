package br.com.adamastor.eleicao.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.adamastor.eleicao.model.entity.Eleitor;
import lombok.Data;

@Data
public class EleitorResponseDTO implements Serializable {

	private static final long serialVersionUID = -2035300989058374798L;

	private Long id;

	private String nome;

	private String cpf;

	private LocalDateTime criadoEm;

	private LocalDateTime alteradoEm;

	private LocalDateTime desativadoEm;

	private boolean ativo;
	
	public EleitorResponseDTO(Eleitor eleitor) {
		this.id = eleitor.getId();
		this.nome = eleitor.getNome();
		this.cpf = eleitor.getCpf();
		this.criadoEm = eleitor.getCriadoEm();
		this.alteradoEm = eleitor.getAlteradoEm();
		this.desativadoEm = eleitor.getDesativadoEm();
		this.ativo = eleitor.isAtivo();
	}
	
	public static List<EleitorResponseDTO> converter(List<Eleitor> eleitores){
		return eleitores.stream().map(EleitorResponseDTO::new).collect(Collectors.toList());
	}
}
