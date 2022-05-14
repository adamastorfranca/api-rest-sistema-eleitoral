package br.com.adamastor.eleicao.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.adamastor.eleicao.model.entity.Eleitor;

@Repository
public interface EleitorRepository extends JpaRepository<Eleitor, Long>{

	Optional<Eleitor> findByCpf(String cpf);
	List<Eleitor> findByNome(String nome);
	List<Eleitor> findByAtivoTrue();
	List<Eleitor> findByAtivoFalse();
}
