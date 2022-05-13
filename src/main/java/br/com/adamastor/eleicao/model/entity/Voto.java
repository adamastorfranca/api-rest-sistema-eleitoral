package br.com.adamastor.eleicao.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TB_VOTO")
@Data
public class Voto implements Serializable {

	private static final long serialVersionUID = -8019002232508246743L;

	@Id
	private VotoId id;
	
	@Column(name = "DT_VOTO", nullable = false)
	private LocalDateTime data;
	
}
