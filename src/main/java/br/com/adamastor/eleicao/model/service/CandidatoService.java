package br.com.adamastor.eleicao.model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.com.adamastor.eleicao.model.dto.CandidatoResponseDTO;
import br.com.adamastor.eleicao.model.dto.EleitorRequestDTO;
import br.com.adamastor.eleicao.model.dto.RelatorioVotacaoDTO;
import br.com.adamastor.eleicao.model.entity.Candidato;
import br.com.adamastor.eleicao.model.exception.AplicacaoException;
import br.com.adamastor.eleicao.model.dto.form.CandidatoAtualizacaoForm;
import br.com.adamastor.eleicao.model.dto.form.CandidatoCadastroForm;
import br.com.adamastor.eleicao.model.repository.CandidatoRepository;

@Service
public class CandidatoService {

	@Autowired
	private CandidatoRepository repository;	
	@Autowired
	private CargoService cargoService;
	@Autowired
	private EleitorService eleitorService;
	@Autowired
	@Lazy
	private VotoService votoService;

	@CacheEvict(value = "listaDeCandidatos", allEntries = true)
	@Transactional(rollbackOn = AplicacaoException.class)
	public CandidatoResponseDTO cadastrar(CandidatoCadastroForm form) {
		Optional<Candidato> resultado = repository.findByCpf(form.getCpf());
		if (resultado.isPresent()) {
			throw new AplicacaoException("CPF já está cadastrado");
		}
		Optional<Candidato> resultado2 = repository.findByNumero(form.getNumero());
		if (resultado2.isPresent()) {
			throw new AplicacaoException("Número já está cadastrado");
		}
		Candidato c = form.gerarCandidato();
		c.setCargo(cargoService.buscarCargo(form.getIdCargo()));
		repository.save(c);
		cadastrarComoEleitor(form);
		return new CandidatoResponseDTO(c);
	}	

	@CacheEvict(value = "listaDeCandidatos", allEntries = true)
	@Transactional(rollbackOn = AplicacaoException.class)
	public CandidatoResponseDTO atualizar(CandidatoAtualizacaoForm form) {
		Optional<Candidato> resultado = repository.findById(form.getId());
		if (!resultado.isPresent()) {
			return null;
		}
		Candidato c = resultado.get();
		Optional<Candidato> resultado2 = repository.findByNumero(form.getNumero());
		if (!(resultado2.isPresent() && c.getNumero().equals(form.getNumero()))) {
			c.setNumero(form.getNumero());
		} else if (!c.getNumero().equals(form.getNumero())) {
			throw new AplicacaoException("Número já está cadastrado");
		}
		c.setNome(form.getNome().toUpperCase());
		c.setLegenda(form.getLegenda().toUpperCase());
		c.setAlteradoEm(LocalDateTime.now());
		c.setAtivo(form.isAtivo());
		if (!c.getCargo().getId().equals(form.getIdCargo())) {
			c.setCargo(cargoService.buscarCargo(form.getIdCargo()));
		}
		if (!form.isAtivo()) {
			c.setDesativadoEm(LocalDateTime.now());
		} else {
			c.setDesativadoEm(null);
		}
		repository.save(c);
		return new CandidatoResponseDTO(c);
	}

	@CacheEvict(value = "listaDeCandidatos", allEntries = true)
	public void deletar(Long id) {
		Optional<Candidato> resultado = repository.findById(id);
		if (!resultado.isPresent()) {
			throw new AplicacaoException("ID informado não está cadastrado");
		}
		Candidato c = resultado.get();
		if(votoService.verificarSeCandidatoRecebeuVoto(c)) {
			throw new AplicacaoException("Candidato não pode ser deletado pois já recebeu voto");
		}
		repository.delete(c);
	}

	@Cacheable(value = "listaDeCandidatos")
	public List<CandidatoResponseDTO> listarTodos() {
		return CandidatoResponseDTO.converter(repository.findAll());
	}

	public CandidatoResponseDTO buscarPorId(Long id) {
		Optional<Candidato> resultado = repository.findById(id);
		if (resultado.isPresent()) {
			return new CandidatoResponseDTO(resultado.get());
		}
		return null;
	}

	public Candidato buscarCandidatoParaSerVotado(Long id) {
		Optional<Candidato> resultado = repository.findById(id);
		if (!resultado.isPresent()) {
			return null;
		}
		Candidato c = resultado.get();
		if (!c.isAtivo()) {
			throw new AplicacaoException("Este candidato não já apto para eleição");
		}
		return c;
	}

	private void cadastrarComoEleitor(CandidatoCadastroForm form) {
		EleitorRequestDTO formEleitor = new EleitorRequestDTO();
		formEleitor.setNome(form.getNome());
		formEleitor.setCpf(form.getCpf());
		formEleitor.setAtivo(form.isAtivo());
		eleitorService.cadastrarCandidatoComoEleitor(formEleitor);
	}

}
