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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.adamastor.eleicao.model.dto.EleitorDTO;
import br.com.adamastor.eleicao.model.form.EleitorAtualizacaoForm;
import br.com.adamastor.eleicao.model.form.EleitorCadastroForm;
import br.com.adamastor.eleicao.model.form.EleitorStatusForm;
import br.com.adamastor.eleicao.model.form.VotoForm;
import br.com.adamastor.eleicao.model.service.EleitorService;
import br.com.adamastor.eleicao.model.service.VotoService;

@RestController
@RequestMapping("rest/eleitores")
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class EleitorRest {
	
	@Autowired
	private EleitorService service;
	@Autowired
	private VotoService votoService;
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<EleitorDTO> cadastrar(@RequestBody @Valid EleitorCadastroForm form) {
		EleitorDTO dto = service.cadastrar(form);
		if(dto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<EleitorDTO> atualizar(@RequestBody @Valid EleitorAtualizacaoForm form) {
		EleitorDTO dto = service.atualizar(form);
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
	public @ResponseBody ResponseEntity<List<EleitorDTO>> listarTodos() {
		List<EleitorDTO> listaDto = service.listarTodos();
		if(listaDto.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(listaDto, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<EleitorDTO> buscarPorId(@PathVariable Long id) {
		EleitorDTO dto = service.buscarPorId(id);
		if(dto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping(value = "/buscar-por-cpf/{cpf}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<EleitorDTO> buscarPorCpf(@PathVariable String cpf) {
		EleitorDTO dto = service.buscarPorCpf(cpf);
		if(dto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/buscar-por-nome/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<EleitorDTO>> buscarPorNome(@PathVariable String nome) {
		List<EleitorDTO> listaDtos = service.buscarPorNome(nome);
		if(listaDtos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(listaDtos, HttpStatus.OK);
	}
	
	@PutMapping(value = "/alterar-status", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<EleitorDTO> alterarStatus(@RequestBody @Valid EleitorStatusForm form) {
		EleitorDTO dto = service.alterarStatus(form);
		if(dto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/ativos", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<EleitorDTO>> listarAtivos() {
		List<EleitorDTO> listaDtos = service.listarAtivos();
		if(listaDtos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(listaDtos, HttpStatus.OK);
	}
	
	@GetMapping(value = "/inativos", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<EleitorDTO>> listarInativos() {
		List<EleitorDTO> listaDtos = service.listarInativos();
		if(listaDtos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(listaDtos, HttpStatus.OK);
	}
	
	@PostMapping(value = "/votar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> votar(@RequestBody @Valid VotoForm form) {
		votoService.votar(form);

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
