package br.com.adamastor.eleicao.controller.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "http://localhost:4200")
public class CargoRest {

	@Autowired
	private CargoService service;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<CargoDTO> cadastrar(@RequestBody @Valid CargoCadastroForm form) {
		CargoDTO dto = service.cadastrar(form);
		if(dto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<CargoDTO>> listarTodos() {
		List<CargoDTO> listaDtos = service.listarTodos();
		if(listaDtos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(listaDtos, HttpStatus.OK);
	}

	@GetMapping(value = "/{cargoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<CargoDTO> buscarPorId(@PathVariable Long cargoId) {
		CargoDTO dto = service.buscarPorId(cargoId);
		if(dto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping(value = "/buscar/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<CargoDTO> buscarPorNome(@PathVariable String nome) {
		CargoDTO dto = service.buscarPorNome(nome);
		if(dto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<CargoDTO> atualizar(@RequestBody @Valid CargoAtualizacaoForm form) {
		CargoDTO dto = service.atualizar(form);
		if(dto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);		
	}
	
	@DeleteMapping(value = "/{cargoId}")
	public ResponseEntity<Void> deletar(@PathVariable Long cargoId) {
		service.deletar(cargoId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping(value = "/alterar-status", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<CargoDTO> alterarStatus(@RequestBody @Valid CargoStatusForm form) {
		CargoDTO dto = service.alterarStatus(form);
		if(dto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/ativos", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<CargoDTO>> listarAtivos() {
		List<CargoDTO> listaDtos = service.listarAtivos();
		if(listaDtos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(listaDtos, HttpStatus.OK);
	}
	
	@GetMapping(value = "/inativos", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<CargoDTO>> listarInativos() {
		List<CargoDTO> listaDtos = service.listarInativos();
		if(listaDtos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(listaDtos, HttpStatus.OK);
	}
}
