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

import br.com.adamastor.eleicao.model.dto.CandidatoRequestDTO;
import br.com.adamastor.eleicao.model.dto.CandidatoResponseDTO;
import br.com.adamastor.eleicao.model.dto.RelatorioVotacaoResponseDTO;
import br.com.adamastor.eleicao.model.entity.Candidato;
import br.com.adamastor.eleicao.model.service.CandidatoService;
import br.com.adamastor.eleicao.model.service.VotoService;

@RestController
@RequestMapping("rest/candidatos")
public class CandidatoRest {

	@Autowired
	private CandidatoService service;
	@Autowired
	private VotoService votoService; 
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<CandidatoResponseDTO>> buscar(
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "cpf", required = false) String cpf,
			@RequestParam(value = "cargo", required = false) Long cargoId,
			@RequestParam(value = "numero", required = false) Integer numero,
			@RequestParam(value = "legenda", required = false) String legenda,
			@RequestParam(value = "ativo", required = false) Boolean ativo) {
		List<Candidato> resultado = service.buscar(id, nome, cpf, cargoId, numero, legenda, ativo);

		return new ResponseEntity<>(CandidatoResponseDTO.converter(resultado), HttpStatus.OK);
	}
		
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<CandidatoResponseDTO> cadastrar(@RequestBody CandidatoRequestDTO formulario) {
		Candidato c = service.cadastrar(formulario);
		if(c == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new CandidatoResponseDTO(c), HttpStatus.OK);
	}
	
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<CandidatoResponseDTO> atualizar(@PathVariable Long id, @RequestBody CandidatoRequestDTO formulario) {
		Candidato c = service.atualizar(id, formulario);
		if(c == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new CandidatoResponseDTO(c), HttpStatus.OK);		
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		service.deletar(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/relatorio", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<RelatorioVotacaoResponseDTO>> relatorioGeral() {
		return new ResponseEntity<>(votoService.gerarRelatorioGeral(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/relatorio/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<RelatorioVotacaoResponseDTO> relatorioIndividual(@PathVariable Long id) {
		return new ResponseEntity<>(votoService.gerarRelatorioIndividual(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/relatorio-cargo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<RelatorioVotacaoResponseDTO>> relatorioCargo(@PathVariable Long id) {
		return new ResponseEntity<>(votoService.gerarRelatorioPorCargo(id), HttpStatus.OK);
	}
}
