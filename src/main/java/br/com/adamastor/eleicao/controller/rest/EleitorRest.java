package br.com.adamastor.eleicao.controller.rest;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.adamastor.eleicao.model.dto.EleitorRequestDTO;
import br.com.adamastor.eleicao.model.dto.EleitorResponseDTO;
import br.com.adamastor.eleicao.model.dto.VotoRequestDTO;
import br.com.adamastor.eleicao.model.entity.Eleitor;
import br.com.adamastor.eleicao.model.service.EleitorService;
import br.com.adamastor.eleicao.model.service.VotoService;

@RestController
@RequestMapping("rest/eleitores")
public class EleitorRest {
	
	@Autowired
	private EleitorService service;
	@Autowired
	private VotoService votoService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<EleitorResponseDTO>> buscar(
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "cpf", required = false) String cpf,
			@RequestParam(value = "ativo", required = false) Boolean ativo) {
		List<Eleitor> resultado = service.buscar(id, nome, cpf, ativo);

		return new ResponseEntity<>(EleitorResponseDTO.converter(resultado), HttpStatus.OK);
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<EleitorResponseDTO> cadastrar(@RequestBody EleitorRequestDTO formulario) {
		Eleitor e = service.cadastrar(formulario);
		if(e == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new EleitorResponseDTO(e), HttpStatus.OK);
	}
	
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<EleitorResponseDTO> atualizar(@PathVariable Long id, @RequestBody EleitorRequestDTO formulario) {
		Eleitor e = service.atualizar(id, formulario);
		if(e == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new EleitorResponseDTO(e), HttpStatus.OK);		
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		service.deletar(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/{id}/votar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> votar(@PathVariable Long id, @RequestBody VotoRequestDTO voto) {
		votoService.votar(id, voto);

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
