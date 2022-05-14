package br.com.adamastor.eleicao.model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adamastor.eleicao.model.dto.CargoDTO;
import br.com.adamastor.eleicao.model.entity.Cargo;
import br.com.adamastor.eleicao.model.exception.AplicacaoException;
import br.com.adamastor.eleicao.model.form.CargoAtualizacaoForm;
import br.com.adamastor.eleicao.model.form.CargoCadastroForm;
import br.com.adamastor.eleicao.model.form.CargoStatusForm;
import br.com.adamastor.eleicao.model.repository.CargoRepository;

@Service
public class CargoService {

	@Autowired
	private CargoRepository cargoRepository;

	public CargoDTO cadastrar(CargoCadastroForm form) {
		Optional<Cargo> resultado = cargoRepository.findByNome(form.getNome());
		if (resultado.isPresent()) {
			throw new AplicacaoException("O cargo já está cadastrado");
		}
		Cargo c = form.gerarCargo();
		cargoRepository.save(c);
		return new CargoDTO(c);
	}

	public List<CargoDTO> listarTodos() {
		List<Cargo> cargos = cargoRepository.findAll();
		if (cargos.isEmpty()) {
			throw new AplicacaoException("Não há cargos cadastrados");
		}
		return CargoDTO.converter(cargos);
	}

	public CargoDTO buscarPorId(Long id) {
		Cargo c = verificarSeIdExiste(id);
		return new CargoDTO(c);
	}

	public CargoDTO buscarPorNome(String nome) {
		Optional<Cargo> resultado = cargoRepository.findByNome(nome.toUpperCase());
		if (!resultado.isPresent()) {
			throw new AplicacaoException("Não há cargo com nome informado");
		}
		return new CargoDTO(resultado.get());
	}

	public CargoDTO atualizar(CargoAtualizacaoForm form) {
		Cargo c = verificarSeIdExiste(form.getId());
		if (!c.getNome().equals(form.getNome().toUpperCase())) {
			Optional<Cargo> resultado = cargoRepository.findByNome(form.getNome());
			if (resultado.isPresent()) {
				throw new AplicacaoException("O cargo já está cadastrado");
			}
		}
		c.setNome(form.getNome().toUpperCase());
		c.setAlteradoEm(LocalDateTime.now());
		c.setAtivo(form.isAtivo());
		c.setDesativadoEm(null);
		if (!form.isAtivo()) {
			c.setDesativadoEm(LocalDateTime.now());
		}
		cargoRepository.save(c);
		return new CargoDTO(c);
	}

	public void deletarPorId(Long id) {
		Cargo c = verificarSeIdExiste(id);
		cargoRepository.delete(c);
	}

	public CargoDTO alterarStatus(CargoStatusForm form) {
		Cargo c = verificarSeIdExiste(form.getId());
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
		cargoRepository.save(c);
		return new CargoDTO(c);
	}
	
	public List<CargoDTO> listarAtivos() {
		List<Cargo> cargos = cargoRepository.findByAtivoTrue();
		if (cargos.isEmpty()) {
			throw new AplicacaoException("Não há cargos cadastrados");
		}
		return CargoDTO.converter(cargos);
	}

	public List<CargoDTO> listarInativos() {
		List<Cargo> cargos = cargoRepository.findByAtivoFalse();
		if (cargos.isEmpty()) {
			throw new AplicacaoException("Não há cargos cadastrados");
		}
		return CargoDTO.converter(cargos);
	}
	
	private Cargo verificarSeIdExiste(Long id) {
		Optional<Cargo> resultado = cargoRepository.findById(id);
		if (!resultado.isPresent()) {
			throw new AplicacaoException("Não há cargo com ID informado");
		}
		return resultado.get();
	}

}
