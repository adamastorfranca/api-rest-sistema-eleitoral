package br.com.adamastor.eleicao.controller.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.adamastor.eleicao.model.dto.CargoDTO;
import br.com.adamastor.eleicao.model.form.CargoAtualizacaoForm;
import br.com.adamastor.eleicao.model.form.CargoCadastroForm;
import br.com.adamastor.eleicao.model.form.CargoStatusForm;
import br.com.adamastor.eleicao.model.service.CargoService;

@RestController
@RequestMapping("rest/cargos")
public class CargoRest {

	@Autowired
	private CargoService cargoService;

	@PostMapping(value = "/cadastrar", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<CargoDTO> cadastrar(@RequestBody @Valid CargoCadastroForm form) {
		CargoDTO dto = cargoService.cadastrar(form);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<CargoDTO>> listarTodos() {
		List<CargoDTO> dto = cargoService.listarTodos();
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<CargoDTO> buscarPorId(@PathVariable Long id) {
		CargoDTO dto = cargoService.buscarPorId(id);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping(value = "/buscarPorNome/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<CargoDTO> buscarPorId(@PathVariable String nome) {
		CargoDTO dto = cargoService.buscarPorNome(nome);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@PutMapping(value = "/atualizar", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<CargoDTO> atualizar(@RequestBody @Valid CargoAtualizacaoForm form) {
		CargoDTO dto = cargoService.atualizar(form);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
		cargoService.deletarPorId(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping(value = "/alterar-status", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<CargoDTO> ativar(@RequestBody CargoStatusForm form) {
		CargoDTO dto = cargoService.alterarStatus(form);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/ativos", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<CargoDTO>> listarAtivos() {
		List<CargoDTO> dto = cargoService.listarAtivos();
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/inativos", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<CargoDTO>> listarInativos() {
		List<CargoDTO> dto = cargoService.listarInativos();
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
}
