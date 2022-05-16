package br.com.adamastor.eleicao.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.adamastor.eleicao.model.service.VotoService;

@RestController
@RequestMapping("rest/votacao")
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class VotoRest {

	@Autowired
	private VotoService service;

	@GetMapping
	public void buscar() {
		service.gerarRelatorio();
	}
	


}
