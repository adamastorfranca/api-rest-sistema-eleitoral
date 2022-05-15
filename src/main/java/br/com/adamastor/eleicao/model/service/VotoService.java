package br.com.adamastor.eleicao.model.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adamastor.eleicao.model.entity.Candidato;
import br.com.adamastor.eleicao.model.entity.Cargo;
import br.com.adamastor.eleicao.model.entity.Eleitor;
import br.com.adamastor.eleicao.model.entity.Voto;
import br.com.adamastor.eleicao.model.entity.VotoId;
import br.com.adamastor.eleicao.model.exception.AplicacaoException;
import br.com.adamastor.eleicao.model.form.VotoForm;
import br.com.adamastor.eleicao.model.repository.VotoRepository;

@Service
public class VotoService {
	
	@Autowired
	private VotoRepository repository;
	@Autowired
	private EleitorService eleitorService;
	@Autowired
	private CandidatoService candidatoService;
	@Autowired
	private CargoService cargoService;
	
	@Transactional(rollbackOn = AplicacaoException.class)
	public void votar(VotoForm form) {
		Voto v = new Voto();
		VotoId vId = new VotoId();
		Cargo cargo = cargoService.buscarCargo(form.getIdCargo());
		Candidato c = candidatoService.buscarCandidato(form.getIdCandidato());	
		vId.setEleitor(eleitorService.buscarEleitor(form.getIdEleitor()));	
		vId.setCargo(cargo);
		v.setId(vId);
		v.setData(LocalDateTime.now());
		if(c.getCargo().equals(cargo)) {
			v.setCandidato(c);
		} else {
			v.setCandidato(null);
		}
		repository.save(v);
	}
	
	public boolean verificarSeEleitorVotou(Eleitor eleitor) {
		return repository.existsByIdEleitor(eleitor);
	}
	
	public boolean verificarSeCandidatoRecebeuVoto(Candidato candidato) {
		return repository.existsByCandidato(candidato);
	}

}
