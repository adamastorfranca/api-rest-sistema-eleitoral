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

import br.com.adamastor.eleicao.model.dto.CandidatoDTO;
import br.com.adamastor.eleicao.model.entity.Candidato;
import br.com.adamastor.eleicao.model.exception.AplicacaoException;
import br.com.adamastor.eleicao.model.form.CandidatoAtualizacaoForm;
import br.com.adamastor.eleicao.model.form.CandidatoCadastroForm;
import br.com.adamastor.eleicao.model.form.CandidatoStatusForm;
import br.com.adamastor.eleicao.model.form.EleitorCadastroForm;
import br.com.adamastor.eleicao.model.repository.CandidatoRepository;

@Service
@Transactional(rollbackOn = AplicacaoException.class)
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
	public CandidatoDTO cadastrar(CandidatoCadastroForm form) {
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
		return new CandidatoDTO(c);
	}	

	@CacheEvict(value = "listaDeCandidatos", allEntries = true)
	public CandidatoDTO atualizar(CandidatoAtualizacaoForm form) {
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
		return new CandidatoDTO(c);
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
	public List<CandidatoDTO> listarTodos() {
		return CandidatoDTO.converter(repository.findAll());
	}

	public CandidatoDTO buscarPorId(Long id) {
		Optional<Candidato> resultado = repository.findById(id);
		if (resultado.isPresent()) {
			return new CandidatoDTO(resultado.get());
		}
		return null;
	}

	public CandidatoDTO buscarPorCpf(String cpf) {
		Optional<Candidato> resultado = repository.findByCpf(cpf);
		if (resultado.isPresent()) {
			return new CandidatoDTO(resultado.get());
		}
		return null;
	}

	public List<CandidatoDTO> buscarPorNome(String nome) {
		return CandidatoDTO.converter(repository.findByNome(nome.toUpperCase()));
	}

	public CandidatoDTO buscarPorNumero(Integer numeroCandidato) {
		Optional<Candidato> resultado = repository.findByNumero(numeroCandidato);
		if (resultado.isPresent()) {
			return new CandidatoDTO(resultado.get());
		}
		return null;
	}

	public CandidatoDTO alterarStatus(CandidatoStatusForm form) {
		Optional<Candidato> resultado = repository.findById(form.getId());
		if (!resultado.isPresent()) {
			return null;
		}
		Candidato c = resultado.get();
		if (form.isAtivo()) {
			c.setAtivo(form.isAtivo());
			c.setAlteradoEm(LocalDateTime.now());
			c.setDesativadoEm(null);
		}
		if (!form.isAtivo()) {
			c.setAtivo(form.isAtivo());
			c.setAlteradoEm(LocalDateTime.now());
			c.setDesativadoEm(LocalDateTime.now());
		}
		repository.save(c);
		return new CandidatoDTO(c);
	}

	public List<CandidatoDTO> listarAtivos() {
		return CandidatoDTO.converter(repository.findByAtivoTrue());
	}

	public List<CandidatoDTO> listarInativos() {
		return CandidatoDTO.converter(repository.findByAtivoFalse());
	}

	public Candidato buscarCandidato(Long id) {
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
		EleitorCadastroForm formEleitor = new EleitorCadastroForm();
		formEleitor.setNome(form.getNome());
		formEleitor.setCpf(form.getCpf());
		formEleitor.setAtivo(form.isAtivo());
		eleitorService.cadastrarCandidatoComoEleitor(formEleitor);
	}

}
