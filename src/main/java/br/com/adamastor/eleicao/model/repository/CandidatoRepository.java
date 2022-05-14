package br.com.adamastor.eleicao.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.adamastor.eleicao.model.entity.Candidato;

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long>{

	List<Candidato> findByNome(String nome);
	List<Candidato> findByAtivoTrue();
	List<Candidato> findByAtivoFalse();
}
