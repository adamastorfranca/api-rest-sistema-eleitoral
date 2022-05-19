package br.com.adamastor.eleicao.model.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adamastor.eleicao.model.dto.RelatorioGeralVotacaoDTO;
import br.com.adamastor.eleicao.model.dto.RelatorioIndividualVotacaoDTO;
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
			throw new AplicacaoException("Número do candidato inexistente");
		}
		repository.save(v);
	}

	public List<RelatorioGeralVotacaoDTO> gerarRelatorioGeral() {
		List<Candidato> candidatos = candidatosParticipantes();
		List<RelatorioGeralVotacaoDTO> vencedoresPorCargo = new ArrayList<>();
		RelatorioGeralVotacaoDTO temp;
		for (Candidato c : candidatos) {
			List<RelatorioGeralVotacaoDTO> listaCloneParaIterar = new ArrayList<>(vencedoresPorCargo);
			int votosCandidato = repository.countByCandidato(c);
			if (vencedoresPorCargo.isEmpty()) {
				vencedoresPorCargo.add(gerarInformacoes(c, votosCandidato));
			}		
			for (RelatorioGeralVotacaoDTO r : listaCloneParaIterar) {
				if (!r.getIdCargo().equals(c.getCargo().getId())) {
					temp = gerarInformacoes(c, votosCandidato);
					if(!vencedoresPorCargo.contains(temp)) {
						vencedoresPorCargo.add(temp);
					}
				}
				if (r.getIdCargo().equals(c.getCargo().getId()) && r.getVotos() < votosCandidato ) {
					vencedoresPorCargo.remove(r);
					temp = gerarInformacoes(c, votosCandidato);					
					if(!vencedoresPorCargo.contains(temp)) {
						vencedoresPorCargo.add(temp);
					}
				}
			}
		}
		return vencedoresPorCargo;
	}
	
	public RelatorioIndividualVotacaoDTO gerarRelatorioIndividual(Long id) {
		Candidato c = candidatoService.buscarCandidatoParaSerVotado(id);
		RelatorioIndividualVotacaoDTO r = new RelatorioIndividualVotacaoDTO();
		r.setNomeCandidato(c.getNome());
		r.setNomeCargo(c.getCargo().getNome());
		r.setVotos(repository.countByCandidato(c));
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

	private RelatorioGeralVotacaoDTO gerarInformacoes(Candidato c, int votos) {
		RelatorioGeralVotacaoDTO r = new RelatorioGeralVotacaoDTO();
		r.setIdCargo(c.getCargo().getId());
		r.setNomeCargo(c.getCargo().getNome());
		r.setVotos(votos);
		r.setIdCandidatoVencedor(c.getId());
		r.setNomeCandidatoVencedor(c.getNome());
		return r;
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
