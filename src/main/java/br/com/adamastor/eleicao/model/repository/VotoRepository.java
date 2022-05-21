package br.com.adamastor.eleicao.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.adamastor.eleicao.model.entity.Candidato;
import br.com.adamastor.eleicao.model.entity.Cargo;
import br.com.adamastor.eleicao.model.entity.Eleitor;
import br.com.adamastor.eleicao.model.entity.Voto;
import br.com.adamastor.eleicao.model.entity.VotoId;

@Repository
public interface VotoRepository extends JpaRepository<Voto, VotoId>{

	boolean existsByIdCargo(Cargo cargo);
	boolean existsByIdEleitor(Eleitor eleitor);
	boolean existsByCandidato(Candidato candidato);
	Integer countByCandidato(Candidato candidato);
	List<Voto> findByIdCargoId(Long idCargo);
	Integer countByEmBrancoIsTrueAndIdCargoId(Long idCargo);
	Integer countByNuloIsTrueAndIdCargoId(Long idCargo);

}
