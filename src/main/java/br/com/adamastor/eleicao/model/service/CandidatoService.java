package br.com.adamastor.eleicao.model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
public class CandidatoService {

	@Autowired
	private CandidatoRepository repository;
	@Autowired
	private EleitorService eleitorService;

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
		repository.save(c);
		cadastrarComoEleitor(form);
		return new CandidatoDTO(c);
	}

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
	
	public CandidatoDTO atualizar(CandidatoAtualizacaoForm form) {
		Optional<Candidato> resultado = repository.findById(form.getId());
		if (!resultado.isPresent()) {
			return null;
		}
		Candidato c = resultado.get();
		c.setNome(form.getNome().toUpperCase());
		c.setAlteradoEm(LocalDateTime.now());
		Optional<Candidato> resultado2 = repository.findByNumero(form.getNumero());
		if (resultado2.isPresent() && c.getNumero() != form.getNumero()) {
			c.setNumero(form.getNumero());
		}
		c.setAtivo(form.isAtivo());
		c.setDesativadoEm(null);
		if (!form.isAtivo()) {
			c.setDesativadoEm(LocalDateTime.now());
		}	
		repository.save(c);
		return new CandidatoDTO(c);
	}	
	
	public void deletarPorId(Long id) {	
		Optional<Candidato> resultado = repository.findById(id);
		if (!resultado.isPresent()) {
			throw new AplicacaoException("ID informado não está cadastrado");
		}
		repository.delete(resultado.get());
	}

	public void deletarPorCpf(String cpf) {	
		Optional<Candidato> resultado = repository.findByCpf(cpf);
		if (!resultado.isPresent()) {
			throw new AplicacaoException("CPF informado não está cadastrado");
		}
		repository.delete(resultado.get());
	}
	
	public void deletarPorNumero(Integer numero) {	
		Optional<Candidato> resultado = repository.findByNumero(numero);
		if (!resultado.isPresent()) {
			throw new AplicacaoException("Número de candidato informado não está cadastrado");
		}
		repository.delete(resultado.get());
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

	private void cadastrarComoEleitor(CandidatoCadastroForm form) {
		EleitorCadastroForm formEleitor = new EleitorCadastroForm();
		formEleitor.setNome(form.getNome());
		formEleitor.setCpf(form.getCpf());
		formEleitor.setAtivo(form.isAtivo());
		eleitorService.cadastrarCandidatoComoEleitor(formEleitor);
	}

}
