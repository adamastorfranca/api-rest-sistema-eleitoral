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

import br.com.adamastor.eleicao.model.dto.CandidatoRequestDTO;
import br.com.adamastor.eleicao.model.dto.EleitorRequestDTO;
import br.com.adamastor.eleicao.model.entity.Candidato;
import br.com.adamastor.eleicao.model.exception.AplicacaoException;
import br.com.adamastor.eleicao.model.repository.CandidatoRepository;

@Service
public class CandidatoService {

	@Autowired
	private CandidatoRepository repository;
	@Autowired
	private EntityManager em;
	@Autowired
	private CargoService cargoService;
	@Autowired
	private EleitorService eleitorService;
	@Autowired
	@Lazy
	private VotoService votoService;

	@Cacheable(value = "buscarCandidatos")
	@Transactional(rollbackOn = AplicacaoException.class)
	public List<Candidato> buscar(Long id, String nome, String cpf, Long cargoId, Integer numero, String legenda, Boolean ativo) {	
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Candidato> cq = cb.createQuery(Candidato.class);

        Root<Candidato> candidato = cq.from(Candidato.class);
        Predicate idPredicate = cb.equal(candidato.get("id"), id);
        Predicate nomePredicate = cb.like(candidato.get("nome"), "%" + nome + "%");
        Predicate cpfPredicate = cb.like(candidato.get("cpf"), "%" + cpf + "%");
        Predicate cargoPredicate = cb.equal(candidato.get("cargo"), cargoId);
        Predicate numeroPredicate = cb.like(candidato.get("numero"), "%" + numero + "%");
        Predicate legendaPredicate = cb.like(candidato.get("legenda"), "%" + legenda + "%");
        Predicate ativoPredicate = cb.equal(candidato.get("ativo"), ativo);
        
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
        if (!ObjectUtils.isEmpty(cargoId)) {
        	predicates.add(cargoPredicate);
		}
        if (!ObjectUtils.isEmpty(numero)) {
        	predicates.add(numeroPredicate);
		}
        if (!ObjectUtils.isEmpty(legenda)) {
        	predicates.add(legendaPredicate);
		}
		if (!ObjectUtils.isEmpty(ativo)) {
			predicates.add(ativoPredicate);
		}
        
		cq.where(predicates.stream().toArray(Predicate[]::new));
        TypedQuery<Candidato> query = em.createQuery(cq);
        return query.getResultList();
	}

	@CacheEvict(value = "buscarCandidatos", allEntries = true)
	@Transactional(rollbackOn = AplicacaoException.class)
	public Candidato cadastrar(CandidatoRequestDTO formulario) {
		Optional<Candidato> resultado = repository.findByCpf(formulario.getCpf());
		if (resultado.isPresent()) {
			throw new AplicacaoException("CPF já está cadastrado");
		}
		Optional<Candidato> resultado2 = repository.findByNumero(formulario.getNumero());
		if (resultado2.isPresent()) {
			throw new AplicacaoException("Número já está cadastrado");
		}
		Candidato c = formulario.gerarCandidato();
		c.setCargo(cargoService.buscarCargoDaVotacao(formulario.getIdCargo()));
		repository.save(c);
		cadastrarComoEleitor(formulario);
		return c;
	}	

	@CacheEvict(value = "buscarCandidatos", allEntries = true)
	@Transactional(rollbackOn = AplicacaoException.class)
	public Candidato atualizar(Long id, CandidatoRequestDTO formulario) {
		Optional<Candidato> resultado = repository.findById(id);
		if (!resultado.isPresent()) {
			return null;
		}
		Candidato c = resultado.get();
		c.setNome(formulario.getNome().toUpperCase());
		if (!formulario.getCpf().isBlank() && formulario.getCpf() != null) {
			if (!c.getCpf().equals(formulario.getCpf())) {
				Optional<Candidato> resultado2 = repository.findByCpf(formulario.getCpf());
				if (resultado2.isPresent()) {
					throw new AplicacaoException("CPF já está cadastrado");
				}
			}
			c.setCpf(formulario.getCpf());
		}
		c.setLegenda(formulario.getLegenda().toUpperCase());
		Optional<Candidato> resultado2 = repository.findByNumero(formulario.getNumero());
		if (!(resultado2.isPresent() && c.getNumero().equals(formulario.getNumero()))) {
			c.setNumero(formulario.getNumero());
		} else if (!c.getNumero().equals(formulario.getNumero())) {
			throw new AplicacaoException("Número já está cadastrado");
		}
		if (!c.getCargo().getId().equals(formulario.getIdCargo())) {
			c.setCargo(cargoService.buscarCargoDaVotacao(formulario.getIdCargo()));
		}
		if (formulario.getAtivo() != null) {
			c.setAtivo(formulario.getAtivo());
			if (formulario.getAtivo().booleanValue()) {
				c.setDesativadoEm(null);
			} else {
				c.setDesativadoEm(LocalDateTime.now());
			}
		}
		c.setAlteradoEm(LocalDateTime.now());
		repository.save(c);
		return c;
	}

	@CacheEvict(value = "buscarCandidatos", allEntries = true)
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

	private void cadastrarComoEleitor(CandidatoRequestDTO form) {
		EleitorRequestDTO formEleitor = new EleitorRequestDTO();
		formEleitor.setNome(form.getNome());
		formEleitor.setCpf(form.getCpf());
		formEleitor.setAtivo(form.getAtivo());
		eleitorService.cadastrarCandidatoComoEleitor(formEleitor);
	}
}
