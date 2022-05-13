package br.com.adamastor.eleicao.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TB_VOTO")
@Data
public class Voto implements Serializable {

	private static final long serialVersionUID = -8019002232508246743L;

	@Column(name = "DT_VOTO")
	private LocalDateTime dtVoto;
	
	@Id
	@OneToOne
	@JoinColumn(name = "FK_ELEITOR")
	private Eleitor eleitor;
	
	@Id
	@OneToOne
	@JoinColumn(name = "FK_CANDIDATO")
	private Candidato candidato;
}
