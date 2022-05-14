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

import org.hibernate.validator.constraints.br.CPF;

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
	
	@Column(name = "DS_NOME", nullable = false, length = 100)	
	private String nome;
		
	@CPF
	@Column(name = "NU_CPF", unique = true, nullable = false, length = 11)
	private String cpf;
	
	@Column(name = "NU_CANDIDATO", unique = true, nullable = false, length = 10)
	private Integer numero;
	
	@Column(name = "DS_LEGENDA", nullable = false, length = 100)
	private String legenda;	
	
	@Column(name = "DT_CRIACAO", nullable = false)
	private LocalDateTime criadoEm;
	
	@Column(name = "DT_ALTERACAO")
	private LocalDateTime alteradoEm;
	
	@Column(name = "DT_DESATIVADO")
	private LocalDateTime desativadoEm;
	
	@Column(name = "ATIVO", nullable = false)
	private boolean ativo;
	
	@ManyToOne
	@JoinColumn(name = "FK_CARGO")
	private Cargo cargo;
}
