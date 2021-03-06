package br.com.adamastor.eleicao.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;

@Entity
@Table(name = "TB_ELEITOR")
@Data
public class Eleitor implements Serializable {

	private static final long serialVersionUID = -3974970210747888346L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ELEITOR")
	private Long id;
	
	@Column(name = "DS_NOME", nullable = false, length = 100)
	@Size(min = 10, max = 100)
	private String nome;
	
	@CPF
	@Column(name = "NU_CPF", unique = true, nullable = false, length = 11)
	private String cpf;

	@Column(name = "DT_CRIACAO", nullable = false)
	private LocalDateTime criadoEm;
	
	@Column(name = "DT_ALTERACAO")
	private LocalDateTime alteradoEm;
	
	@Column(name = "DT_DESATIVADO")
	private LocalDateTime desativadoEm;
	
	@Column(name = "ATIVO", nullable = false)
	private boolean ativo;
	
	@Column(name = "VOTOU")
	private boolean votou;

}
