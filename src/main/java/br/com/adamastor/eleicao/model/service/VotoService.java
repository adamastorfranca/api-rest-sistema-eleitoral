package br.com.adamastor.eleicao.model.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import br.com.adamastor.eleicao.model.dto.RelatorioVotacaoResponseDTO;
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

	@CacheEvict(value = "buscarEleitores", allEntries = true)
	@Transactional(rollbackOn = AplicacaoException.class)
	public void votar(VotoRequestDTO voto) {
		Voto v = new Voto();
		VotoId vId = new VotoId();
		Cargo cargo = cargoService.buscarCargoDaVotacao(voto.getIdCargo());
		Eleitor e = eleitorService.buscarEleitorParaVotar(voto.getIdEleitor());
		vId.setEleitor(e);
		vId.setCargo(cargo);
		v.setId(vId);
		Candidato c = candidatoService.buscarCandidatoParaSerVotado(voto.getIdCandidato());
		if (c != null && c.getCargo().equals(cargo)) {
			v.setCandidato(c);
			v.setEmBranco(false);
			v.setNulo(false);
		} else {
			if (voto.isEmBranco()) {
				v.setCandidato(null);
				v.setEmBranco(true);
				v.setNulo(false);
			}
			if (voto.isNulo()) {
				v.setCandidato(null);
				v.setNulo(true);
				v.setEmBranco(false);
			}
		}
		if (!e.isVotou()) {
			e.setVotou(true);
		}
		if (c != null && !c.isVotado()) {
			c.setVotado(true);
		}
		v.setData(LocalDateTime.now());
		repository.save(v);
	}

	public List<RelatorioVotacaoResponseDTO> gerarRelatorioPorCargo(Long id) {
		List<Candidato> candidatos = candidatosParticipantes(id);
		List<RelatorioVotacaoResponseDTO> relatorio = new ArrayList<>();
		int  votosCandidato;
		for(Candidato c : candidatos) {
			votosCandidato = repository.countByCandidato(c);
			relatorio.add(gerarInformacoes(c, votosCandidato));
		}
		Candidato temp = new Candidato();
		temp.setId(-1L);
		votosCandidato = repository.countByEmBrancoIsTrueAndIdCargoId(id);
		relatorio.add(gerarInformacoes(temp, votosCandidato));
		temp.setId(-2L);
		votosCandidato = repository.countByNuloIsTrueAndIdCargoId(id);
		relatorio.add(gerarInformacoes(temp, votosCandidato));
		return relatorio;
	}

	public List<RelatorioVotacaoResponseDTO> gerarRelatorioGeral() {
		List<Candidato> candidatos = candidatosParticipantes(null);
		List<RelatorioVotacaoResponseDTO> vencedoresPorCargo = new ArrayList<>();
		RelatorioVotacaoResponseDTO temp;
		for (Candidato c : candidatos) {
			
			List<RelatorioVotacaoResponseDTO> listaCloneParaIterar = new ArrayList<>(vencedoresPorCargo);
			int votosCandidato = repository.countByCandidato(c);
			if (vencedoresPorCargo.isEmpty()) {
				vencedoresPorCargo.add(gerarInformacoes(c, votosCandidato));
			}
			for (RelatorioVotacaoResponseDTO r : listaCloneParaIterar) {
				if (!r.getNomeCargo().equals(c.getCargo().getNome())) {
					temp = gerarInformacoes(c, votosCandidato);
					if (!vencedoresPorCargo.contains(temp)) {
						vencedoresPorCargo.add(temp);
					}
				}
				if (r.getNomeCargo().equals(c.getCargo().getNome()) && r.getVotos() < votosCandidato) {
					vencedoresPorCargo.remove(r);
					temp = gerarInformacoes(c, votosCandidato);
					if (!vencedoresPorCargo.contains(temp)) {
						vencedoresPorCargo.add(temp);
					}
				}					
			}	
		}
		return vencedoresPorCargo;
	}

	public RelatorioVotacaoResponseDTO gerarRelatorioIndividual(Long id) {
		Candidato c = candidatoService.buscarCandidatoParaSerVotado(id);
		RelatorioVotacaoResponseDTO r = new RelatorioVotacaoResponseDTO();
		r.setNomeCargo(c.getCargo().getNome());
		r.setNomeCandidato(c.getNome());
		r.setNumeroCandidato(c.getNumero());
		r.setLegendaCandidato(c.getLegenda());
		r.setVotos(repository.countByCandidato(c));
		return r;
	}

	private List<Candidato> candidatosParticipantes(Long idCargo) {
		List<Candidato> candidatos = new ArrayList<>();
		List<Voto> votos;	
		if(idCargo == null) {
			votos = repository.findAll();
		} else {
			votos = repository.findByIdCargoId(idCargo);
		}
		for (Voto v : votos) {
			if (v.getCandidato() != null) {
				if (candidatos.isEmpty()) {
					candidatos.add(v.getCandidato());
				} else {
					if (!candidatos.contains(v.getCandidato())) {
						candidatos.add(v.getCandidato());
					}
				}
			}
		}
		return candidatos;
	}

	private RelatorioVotacaoResponseDTO gerarInformacoes(Candidato c, int votos) {
		RelatorioVotacaoResponseDTO r = new RelatorioVotacaoResponseDTO();
		if (c.getId() > 0) {
			r.setNomeCargo(c.getCargo().getNome());
			r.setNomeCandidato(c.getNome());
			r.setNumeroCandidato(c.getNumero());
			r.setLegendaCandidato(c.getLegenda());
		} else {
			if (c.getId() == -1) {
				r.setNomeCandidato("VOTO EM BRANCO");
			}
			if (c.getId() == -2) {
				r.setNomeCandidato("VOTO NULO");
			}
		}
		r.setVotos(votos);
		return r;
	}

	public boolean verificarSeCargoEstaNaEleicao(Cargo cargo) {
		return repository.existsByIdCargo(cargo);
	}

}
