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

import br.com.adamastor.eleicao.model.dto.EleitorDTO;
import br.com.adamastor.eleicao.model.entity.Eleitor;
import br.com.adamastor.eleicao.model.exception.AplicacaoException;
import br.com.adamastor.eleicao.model.form.EleitorAtualizacaoForm;
import br.com.adamastor.eleicao.model.form.EleitorCadastroForm;
import br.com.adamastor.eleicao.model.form.EleitorStatusForm;
import br.com.adamastor.eleicao.model.repository.EleitorRepository;

@Service
@Transactional(rollbackOn = AplicacaoException.class)
public class EleitorService {
	
	@Autowired
	private EleitorRepository repository;
	@Autowired
	@Lazy
	private VotoService votoService;
	
	@CacheEvict(value = "listaDeEleitores", allEntries = true)
	public EleitorDTO cadastrar(EleitorCadastroForm form) {
		Optional<Eleitor> resultado = repository.findByCpf(form.getCpf());
		if (resultado.isPresent()) {
			throw new AplicacaoException("CPF já está cadastrado");
		}
		Eleitor e = form.gerarEleitor();
		repository.save(e);
		return new EleitorDTO(e);
	}
	
	@CacheEvict(value = "listaDeEleitores", allEntries = true)
	public EleitorDTO atualizar(EleitorAtualizacaoForm form) {
		Optional<Eleitor> resultado = repository.findById(form.getId());
		if (!resultado.isPresent()) {
			return null;
		}
		Eleitor e = resultado.get();
		e.setNome(form.getNome().toUpperCase());
		e.setAlteradoEm(LocalDateTime.now());
		e.setAtivo(form.isAtivo());
		e.setDesativadoEm(null);
		if (!form.isAtivo()) {
			e.setDesativadoEm(LocalDateTime.now());
		}
		repository.save(e);
		return new EleitorDTO(e);
	}
	
	@CacheEvict(value = "listaDeEleitores", allEntries = true)
	public void deletar(Long id) {	
		Optional<Eleitor> resultado = repository.findById(id);
		if (!resultado.isPresent()) {
			throw new AplicacaoException("ID informado não está cadastrado");
		}
		Eleitor e = resultado.get();
		if(votoService.verificarSeEleitorVotou(e)) {
			throw new AplicacaoException("Eleitor não pode ser deletado pois já votou");
		}
		repository.delete(e);
	}

	@Cacheable(value = "listaDeEleitores")
	public List<EleitorDTO> listarTodos() {
		return EleitorDTO.converter(repository.findAll());
	}
	
	public EleitorDTO buscarPorId(Long id) {
		Optional<Eleitor> resultado = repository.findById(id);
		if (resultado.isPresent()) {
			return new EleitorDTO(resultado.get());
		}
		return null;
	}
	
	public EleitorDTO buscarPorCpf(String cpf) {
		Optional<Eleitor> resultado = repository.findByCpf(cpf);
		if (resultado.isPresent()) {
			return new EleitorDTO(resultado.get());
		}
		return null;
	}
	
	public List<EleitorDTO> buscarPorNome(String nome) {
		return EleitorDTO.converter(repository.findByNome(nome.toUpperCase()));
	}

	public EleitorDTO alterarStatus(EleitorStatusForm form) {
		Optional<Eleitor> resultado = repository.findById(form.getId());
		if (!resultado.isPresent()) {
			return null;
		}
		Eleitor e = resultado.get();
		if (form.isAtivo()) {
			e.setAtivo(form.isAtivo());
			e.setAlteradoEm(LocalDateTime.now());
			e.setDesativadoEm(null);
		}
		if (!form.isAtivo()) {
			e.setAtivo(form.isAtivo());
			e.setAlteradoEm(LocalDateTime.now());
			e.setDesativadoEm(LocalDateTime.now());
		}
		repository.save(e);
		return new EleitorDTO(e);
	}

	public List<EleitorDTO> listarAtivos() {
		return EleitorDTO.converter(repository.findByAtivoTrue());
	}

	public List<EleitorDTO> listarInativos() {
		return EleitorDTO.converter(repository.findByAtivoFalse());
	}

	public void cadastrarCandidatoComoEleitor(EleitorCadastroForm form) {
		Optional<Eleitor> resultado = repository.findByCpf(form.getCpf());
		if (resultado.isPresent()) {
			return;
		}
		Eleitor e = form.gerarEleitor();
		repository.save(e);
	}
	
	public Eleitor buscarEleitor(Long id) {
		Optional<Eleitor> resultado = repository.findById(id);
		if (!resultado.isPresent()) {
			throw new AplicacaoException("ID informado não está cadastrado para nenhum eleitor");
		}
		Eleitor e = resultado.get();
		if(!e.isAtivo()) {
			throw new AplicacaoException("Este eleitor não já apto à votar");
		}
		return e;
	}
}
