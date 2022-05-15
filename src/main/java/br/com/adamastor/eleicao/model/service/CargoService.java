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

import br.com.adamastor.eleicao.model.dto.CargoRequestDTO;
import br.com.adamastor.eleicao.model.entity.Cargo;
import br.com.adamastor.eleicao.model.exception.AplicacaoException;
import br.com.adamastor.eleicao.model.repository.CargoRepository;

@Service
public class CargoService {

	@Autowired
	private CargoRepository repository;
	@Autowired
	private EntityManager em;
	@Autowired
	@Lazy
	private VotoService votoService;
	
	@Cacheable(value = "buscarCargos")
	@Transactional(rollbackOn = AplicacaoException.class)
	public List<Cargo> buscar(Long id, String nome, Boolean ativo) {	
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cargo> cq = cb.createQuery(Cargo.class);

        Root<Cargo> cargo = cq.from(Cargo.class);
        Predicate idPredicate = cb.equal(cargo.get("id"), id);
        Predicate nomePredicate = cb.like(cargo.get("nome"), "%" + nome + "%");
        Predicate ativoPredicate = cb.equal(cargo.get("ativo"), ativo);
        
        List<Predicate> predicates = new ArrayList<>();
        if (!ObjectUtils.isEmpty(id)) {
        	predicates.add(idPredicate);
		}
        if (!ObjectUtils.isEmpty(nome)) {
        	predicates.add(nomePredicate);
		}
		if (!ObjectUtils.isEmpty(ativo)) {
			predicates.add(ativoPredicate);
		}
        
		cq.where(predicates.stream().toArray(Predicate[]::new));
        TypedQuery<Cargo> query = em.createQuery(cq);
        return query.getResultList();
	}

	@CacheEvict(value = "buscarCargos", allEntries = true)
	@Transactional(rollbackOn = AplicacaoException.class)
	public Cargo cadastrar(CargoRequestDTO formulario) {
		Optional<Cargo> resultado = repository.findByNome(formulario.getNome());
		if (resultado.isPresent()) {
			throw new AplicacaoException("O cargo já está cadastrado");
		}
		Cargo c = formulario.gerarCargo();
		repository.save(c);
		return c;
	}

	@CacheEvict(value = "buscarCargos", allEntries = true)
	@Transactional(rollbackOn = AplicacaoException.class)
	public Cargo atualizar(Long id, CargoRequestDTO formulario) {
		Optional<Cargo> resultado = repository.findById(id);
		if (!resultado.isPresent()) {
			return null;
		}
		Cargo c = resultado.get();
		if (!formulario.getNome().isBlank() && formulario.getNome() != null) {
			if (!c.getNome().equals(formulario.getNome().toUpperCase())) {
				Optional<Cargo> resultado2 = repository.findByNome(formulario.getNome());
				if (resultado2.isPresent()) {
					throw new AplicacaoException("O cargo já está cadastrado");
				}
			}
			c.setNome(formulario.getNome().toUpperCase());
		}
		c.setAlteradoEm(LocalDateTime.now());
		if(formulario.getAtivo() != null) {
			c.setAtivo(formulario.getAtivo());
			if (formulario.getAtivo()) {
				c.setDesativadoEm(null);
			} else {
				c.setDesativadoEm(LocalDateTime.now());
			}
		}		
		repository.save(c);
		return c;
	}

	@CacheEvict(value = "buscarCargos", allEntries = true)
	public void deletar(Long id) {
		Optional<Cargo> resultado = repository.findById(id);
		if (!resultado.isPresent()) {
			throw new AplicacaoException("ID informado não está cadastrado");
		}
		Cargo c = resultado.get();
		if(votoService.verificarSeCargoEstaNaEleicao(c)) {
			throw new AplicacaoException("Cargo não pode ser deletado pois está na votação");
		}
		repository.delete(c);
	}

	public Cargo buscarCargoDaVotacao(Long id) {
		Optional<Cargo> resultado = repository.findById(id);
		if (!resultado.isPresent()) {
			throw new AplicacaoException("ID informado não está cadastrado para nenhum cargo");
		}
		Cargo c = resultado.get();
		if (!c.isAtivo()) {
			throw new AplicacaoException("Este cargo não pode ser atribuido ao candidato");
		}
		return c;
	}
}
