package br.com.adamastor.eleicao.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TB_CANDIDATO")
@Data
public class Candidato implements Serializable {

	private static final long serialVersionUID = 6268070536622079553L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CANDIDATO")
	private Long id;
	
	@Column(name = "DS_NOME", length = 100)
	private String nome;
	
	@Column(name = "NU_CANDIDATO")
	private Integer numero;
	
	@Column(name = "DS_LEGENDA")
	private String legenda;	
	
	@Column(name = "DT_CRIACAO")
	private LocalDateTime criadoEm;
	
	@Column(name = "DT_ALTERACAO", nullable = false)
	private LocalDateTime alteradoEm;
	
	@Column(name = "DT_EXCLUSAO", nullable = false)
	private LocalDateTime deletadoEm;
	
	@ManyToOne
	@JoinColumn(name = "FK_CARGO")
	private Cargo cargo;
}
