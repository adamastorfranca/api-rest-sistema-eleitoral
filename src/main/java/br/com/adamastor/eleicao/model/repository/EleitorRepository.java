package br.com.adamastor.eleicao.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.adamastor.eleicao.model.entity.Eleitor;

@Repository
public interface EleitorRepository extends JpaRepository<Eleitor, Long>{

}
