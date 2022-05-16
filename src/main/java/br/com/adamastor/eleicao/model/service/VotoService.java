package br.com.adamastor.eleicao.model.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adamastor.eleicao.model.dto.RelatorioVotacaoDTO;
import br.com.adamastor.eleicao.model.dto.VotoRequestDTO;
import br.com.adamastor.eleicao.model.entity.Candidato;
import br.com.adamastor.eleicao.model.entity.Cargo;
import br.com.adamastor.eleicao.model.entity.Eleitor;
import br.com.adamastor.eleicao.model.entity.Voto;
import br.com.adamastor.eleicao.model.entity.VotoId;
import br.com.adamastor.eleicao.model.exception.AplicacaoException;
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
	public void votar(VotoRequestDTO voto) {
		Voto v = new Voto();
		VotoId vId = new VotoId();
		Cargo cargo = cargoService.buscarCargoDaVotacao(voto.getIdCargo());
		Candidato c = candidatoService.buscarCandidatoParaSerVotado(voto.getIdCandidato());
		vId.setEleitor(eleitorService.buscarEleitorParaVotar(voto.getIdEleitor()));
		vId.setCargo(cargo);
		v.setId(vId);
		v.setData(LocalDateTime.now());
		if (c != null && c.getCargo().equals(cargo)) {
			v.setCandidato(c);
		} else {
			v.setCandidato(null);
		}
		repository.save(v);
	}

	public void gerarRelatorio() {
		List<Candidato> candidatos = candidatosParticipantes();
		List<RelatorioVotacaoDTO> vencedoresPorCargo = new ArrayList<>();		
		for (Candidato c : candidatos) {
			int votosCandidato = repository.countByCandidato(c);
			if (vencedoresPorCargo.isEmpty()) {
				vencedoresPorCargo.add(gerarInformacoes(c, votosCandidato));
			}	
			for (RelatorioVotacaoDTO r : vencedoresPorCargo) {
				if (!r.getIdCargo().equals(c.getCargo().getId())) {	
					vencedoresPorCargo.add(gerarInformacoes(c, votosCandidato));				
				}

				if (r.getIdCargo().equals(c.getCargo().getId()) && r.getVotos() < votosCandidato) {
					vencedoresPorCargo.remove(r);
					vencedoresPorCargo.add(gerarInformacoes(c, votosCandidato));
				}
			}
		}
	}
	
	private RelatorioVotacaoDTO gerarInformacoes(Candidato c, int votos) {
		RelatorioVotacaoDTO r = new RelatorioVotacaoDTO();
		r.setIdCargo(c.getCargo().getId());
		r.setNomeCargo(c.getCargo().getNome());
		r.setVotos(votos);
		r.setIdCandidatoVencedor(c.getId());
		r.setNomeCandidatoVencedor(c.getNome());
		return r;
	}
	
	private List<Candidato> candidatosParticipantes() {
		List<Candidato> candidatos = new ArrayList<>();
		List<Voto> votos = repository.findAll();
		for (Voto v : votos) {
			if (candidatos.isEmpty()) {
				candidatos.add(v.getCandidato());
			} else {
				if (!candidatos.contains(v.getCandidato())) {
					candidatos.add(v.getCandidato());
				}
			}
		}
		return candidatos;
	}
	
	public boolean verificarSeEleitorVotou(Eleitor eleitor) {
		return repository.existsByIdEleitor(eleitor);
	}

	public boolean verificarSeCargoEstaNaEleicao(Cargo cargo) {
		return repository.existsByIdCargo(cargo);
	}

	public boolean verificarSeCandidatoRecebeuVoto(Candidato candidato) {
		return repository.existsByCandidato(candidato);
	}

}
