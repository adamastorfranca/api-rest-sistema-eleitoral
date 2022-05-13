package br.com.adamastor.eleicao.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TB_CARGO")
@Data
public class Cargo implements Serializable {

	private static final long serialVersionUID = -5397190416241068724L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ELEITOR")
	private Long id;
	
	@Column(name = "DS_NOME", length = 100)
	private String nome;
	
	@Column(name = "DT_CRIACAO")
	private LocalDateTime criadoEm;
	
	@Column(name = "DT_ALTERACAO", nullable = false)
	private LocalDateTime alteradoEm;
	
	@Column(name = "DT_EXCLUSAO", nullable = false)
	private LocalDateTime deletadoEm;
}