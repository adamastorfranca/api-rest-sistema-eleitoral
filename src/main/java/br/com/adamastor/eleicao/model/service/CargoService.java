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
	private CargoRepository repository;

	public CargoDTO cadastrar(CargoCadastroForm form) {
		Optional<Cargo> resultado = repository.findByNome(form.getNome());
		if (resultado.isPresent()) {
			throw new AplicacaoException("O cargo já está cadastrado");
		}
		Cargo c = form.gerarCargo();
		repository.save(c);
		return new CargoDTO(c);
	}

	public List<CargoDTO> listarTodos() {
		return CargoDTO.converter(repository.findAll());
	}

	public CargoDTO buscarPorId(Long id) {
		Optional<Cargo> resultado = repository.findById(id);
		if (resultado.isPresent()) {
			return new CargoDTO(resultado.get());
		}
		return null;
	}

	public CargoDTO buscarPorNome(String nome) {
		Optional<Cargo> resultado = repository.findByNome(nome.toUpperCase());
		if (resultado.isPresent()) {
			return new CargoDTO(resultado.get());
		}
		return null;
	}

	public CargoDTO atualizar(CargoAtualizacaoForm form) {
		Optional<Cargo> resultado = repository.findById(form.getId());
		if (!resultado.isPresent()) {
			return null;
		}
		Cargo c = resultado.get();
		if (!c.getNome().equals(form.getNome().toUpperCase())) {
			Optional<Cargo> resultado2 = repository.findByNome(form.getNome());
			if (resultado2.isPresent()) {
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
		repository.save(c);
		return new CargoDTO(c);
	}

	public void deletar(Long id) {	
		Optional<Cargo> resultado = repository.findById(id);
		if (!resultado.isPresent()) {
			throw new AplicacaoException("ID informado não está cadastrado");
		}
		Cargo c = resultado.get();
		repository.delete(c);
	}

	public CargoDTO alterarStatus(CargoStatusForm form) {
		Optional<Cargo> resultado = repository.findById(form.getId());
		if (!resultado.isPresent()) {
			return null;
		}
		Cargo c = resultado.get();
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
		repository.save(c);
		return new CargoDTO(c);
	}
	
	public List<CargoDTO> listarAtivos() {
		return CargoDTO.converter(repository.findByAtivoTrue());
	}

	public List<CargoDTO> listarInativos() {
		return CargoDTO.converter(repository.findByAtivoFalse());
	}
}
