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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.adamastor.eleicao.model.dto.CandidatoDTO;
import br.com.adamastor.eleicao.model.form.CandidatoAtualizacaoForm;
import br.com.adamastor.eleicao.model.form.CandidatoCadastroForm;
import br.com.adamastor.eleicao.model.form.CandidatoStatusForm;
import br.com.adamastor.eleicao.model.service.CandidatoService;

@RestController
@RequestMapping("rest/candidatos")
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CandidatoRest {

	@Autowired
	private CandidatoService service;
		
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<CandidatoDTO> cadastrar(@RequestBody @Valid CandidatoCadastroForm form) {
		CandidatoDTO dto = service.cadastrar(form);
		if(dto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<CandidatoDTO> atualizar(@RequestBody @Valid CandidatoAtualizacaoForm form) {
		CandidatoDTO dto = service.atualizar(form);
		if(dto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);		
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		service.deletar(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
		
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<CandidatoDTO>> listarTodos() {
		List<CandidatoDTO> listaDto = service.listarTodos();
		if(listaDto.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(listaDto, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<CandidatoDTO> buscarPorId(@PathVariable Long id) {
		CandidatoDTO dto = service.buscarPorId(id);
		if(dto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/buscar-por-cpf/{cpf}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<CandidatoDTO> buscarPorCpf(@PathVariable String cpf) {
		CandidatoDTO dto = service.buscarPorCpf(cpf);
		if(dto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
		
	@GetMapping(value = "/buscar-por-nome/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<CandidatoDTO>> buscarPorNome(@PathVariable String nome) {
		List<CandidatoDTO> listaDtos = service.buscarPorNome(nome);
		if(listaDtos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(listaDtos, HttpStatus.OK);
	}
	
	@GetMapping(value = "/buscar-por-numero/{numero}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<CandidatoDTO> buscarPorCpf(@PathVariable Integer numero) {
		CandidatoDTO dto = service.buscarPorNumero(numero);
		if(dto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@PutMapping(value = "/alterar-status", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<CandidatoDTO> alterarStatus(@RequestBody @Valid CandidatoStatusForm form) {
		CandidatoDTO dto = service.alterarStatus(form);
		if(dto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/ativos", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<CandidatoDTO>> listarAtivos() {
		List<CandidatoDTO> listaDtos = service.listarAtivos();
		if(listaDtos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(listaDtos, HttpStatus.OK);
	}
	
	@GetMapping(value = "/inativos", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<CandidatoDTO>> listarInativos() {
		List<CandidatoDTO> listaDtos = service.listarInativos();
		if(listaDtos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(listaDtos, HttpStatus.OK);
	}
}