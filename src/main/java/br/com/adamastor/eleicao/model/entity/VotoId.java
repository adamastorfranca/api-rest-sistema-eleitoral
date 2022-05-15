package br.com.adamastor.eleicao.model.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Embeddable
public class VotoId implements Serializable {

	private static final long serialVersionUID = -8625722725569288786L;

	@ManyToOne
	@JoinColumn(name = "FK_ELEITOR", updatable = false, nullable = false)
	private Eleitor eleitor;

	@ManyToOne
	@JoinColumn(name = "FK_CARGO", updatable = false, nullable = false)
	private Cargo cargo;
}