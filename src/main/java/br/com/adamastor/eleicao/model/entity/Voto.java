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
@Table(name = "TB_VOTO")
@Data
public class Voto implements Serializable {

	private static final long serialVersionUID = -8019002232508246743L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true)
	private VotoId id;
	
	@Column(name = "DT_VOTO", nullable = false, updatable = false)
	private LocalDateTime data;
	
	@ManyToOne
	@JoinColumn(name = "FK_CANDIDATO", updatable = false)
	private Candidato candidato;
	
}
