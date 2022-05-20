package br.com.adamastor.eleicao.model.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import br.com.adamastor.eleicao.model.dto.EleitorRequestDTO;
import br.com.adamastor.eleicao.model.entity.Eleitor;
import br.com.adamastor.eleicao.model.exception.AplicacaoException;
import br.com.adamastor.eleicao.model.repository.EleitorRepository;

@Service
public class EleitorService {

	@Autowired
	private EleitorRepository repository;
	@Autowired
	private EntityManager em;
	@Autowired
	@Lazy
	private VotoService votoService;

	@Cacheable(value = "buscarEleitores")
	@Transactional(rollbackOn = AplicacaoException.class)
	public List<Eleitor> buscar(Long id, String nome, String cpf, Boolean ativo) {	
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Eleitor> cq = cb.createQuery(Eleitor.class);

        Root<Eleitor> eleitor = cq.from(Eleitor.class);
        Predicate idPredicate = cb.equal(eleitor.get("id"), id);
        Predicate nomePredicate = cb.like(eleitor.get("nome"), "%" + nome + "%");
        Predicate cpfPredicate = cb.like(eleitor.get("cpf"), "%" + cpf + "%");
        Predicate ativoPredicate = cb.equal(eleitor.get("ativo"), ativo);
        
        List<Predicate> predicates = new ArrayList<>();
        if (!ObjectUtils.isEmpty(id)) {
        	predicates.add(idPredicate);
		}
        if (!ObjectUtils.isEmpty(nome)) {
        	predicates.add(nomePredicate);
		}
        if (!ObjectUtils.isEmpty(cpf)) {
        	predicates.add(cpfPredicate);
		}
		if (!ObjectUtils.isEmpty(ativo)) {
			predicates.add(ativoPredicate);
		}
        
		cq.where(predicates.stream().toArray(Predicate[]::new));
        TypedQuery<Eleitor> query = em.createQuery(cq);
        return query.getResultList();
	}
	
	@CacheEvict(value = "buscarEleitores", allEntries = true)
	@Transactional(rollbackOn = AplicacaoException.class)
	public Eleitor cadastrar(EleitorRequestDTO formulario) {
		Optional<Eleitor> resultado = repository.findByCpf(formulario.getCpf());
		if (resultado.isPresent()) {
			throw new AplicacaoException("CPF já está cadastrado");
		}
		Eleitor e = formulario.gerarEleitor();
		repository.save(e);
		return e;
	}

	@CacheEvict(value = "buscarEleitores", allEntries = true)
	@Transactional(rollbackOn = AplicacaoException.class)
	public Eleitor atualizar(Long id, EleitorRequestDTO formulario) {
		Optional<Eleitor> resultado = repository.findById(id);
		if (!resultado.isPresent()) {
			return null;
		}
		Eleitor e = resultado.get();
		if (!formulario.getCpf().isBlank() && formulario.getCpf() != null) {
			e.setNome(formulario.getNome().toUpperCase());
		}
		if (!formulario.getCpf().isBlank() && formulario.getCpf() != null) {
			if (!e.getCpf().equals(formulario.getCpf())) {
				Optional<Eleitor> resultado2 = repository.findByCpf(formulario.getCpf());
				if (resultado2.isPresent()) {
					throw new AplicacaoException("CPF já está cadastrado");
				}
			}
			e.setCpf(formulario.getCpf());
		}
		if (formulario.getAtivo().booleanValue() && !e.isAtivo()) {
			e.setDesativadoEm(null);
		} 
		if (!formulario.getAtivo().booleanValue() && e.isAtivo()) {
			e.setDesativadoEm(LocalDateTime.now());
		}
		e.setAtivo(formulario.getAtivo());	
		e.setAlteradoEm(LocalDateTime.now());
		repository.save(e);
		return e;
	}

	@CacheEvict(value = "buscarEleitores", allEntries = true)
	public void deletar(Long id) {
		Optional<Eleitor> resultado = repository.findById(id);
		if (!resultado.isPresent()) {
			throw new AplicacaoException("ID informado não está cadastrado");
		}
		Eleitor e = resultado.get();
		if (votoService.verificarSeEleitorVotou(e)) {
			throw new AplicacaoException("Eleitor não pode ser deletado pois já votou");
		}
		repository.delete(e);
	}

	public Eleitor buscarEleitorParaVotar(Long id) {
		Optional<Eleitor> resultado = repository.findById(id);
		if (!resultado.isPresent()) {
			throw new AplicacaoException("ID informado não está cadastrado para nenhum eleitor");
		}
		Eleitor e = resultado.get();
		if (!e.isAtivo()) {
			throw new AplicacaoException("Este eleitor não já apto à votar");
		}
		return e;
	}

	public void cadastrarCandidatoComoEleitor(EleitorRequestDTO formulario) {
		Optional<Eleitor> resultado = repository.findByCpf(formulario.getCpf());
		if (resultado.isPresent()) {
			return;
		}
		Eleitor e = formulario.gerarEleitor();
		repository.save(e);
	}

}
