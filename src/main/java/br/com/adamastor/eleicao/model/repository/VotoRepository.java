package br.com.adamastor.eleicao.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.adamastor.eleicao.model.entity.Voto;
import br.com.adamastor.eleicao.model.entity.VotoId;

@Repository
public interface VotoRepository extends JpaRepository<Voto, VotoId>{

}
