package br.com.adamastor.eleicao.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.adamastor.eleicao.model.entity.Eleitor;
import lombok.Data;

@Data
public class EleitorDTO implements Serializable {

	private static final long serialVersionUID = -2035300989058374798L;

	private Long id;

	private String nome;

	private String cpf;

	private LocalDateTime criadoEm;

	private LocalDateTime alteradoEm;

	private LocalDateTime desativadoEm;

	private boolean ativo;
	
	public EleitorDTO(Eleitor eleitor) {
		this.id = eleitor.getId();
		this.nome = eleitor.getNome();
		this.cpf = eleitor.getCpf();
		this.criadoEm = eleitor.getCriadoEm();
		this.alteradoEm = eleitor.getAlteradoEm();
		this.desativadoEm = eleitor.getDesativadoEm();
		this.ativo = eleitor.isAtivo();
	}
	
	public static List<EleitorDTO> converter(List<Eleitor> eleitores){
		return eleitores.stream().map(EleitorDTO::new).collect(Collectors.toList());
	}
}
