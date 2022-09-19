package br.com.gep.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.gep.api.config.ControllerConfig;
import br.com.gep.api.converts.ListaConvert;
import br.com.gep.api.dto.input.ListaInput;
import br.com.gep.api.dto.outputs.ListaOutput;
import br.com.gep.api.entities.ListaEntity;
import br.com.gep.api.services.ListaService;

@RestController
@RequestMapping(ControllerConfig.PRE_URL + "/listas")
public class ListaController {
	
	@Autowired
	private ListaConvert listaConvert;
	
	@Autowired
	private ListaService listaService;
	
	@PostMapping("/cria")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ListaOutput cria(@RequestBody @Valid ListaInput input) {
		ListaEntity lista = listaConvert.inputToEntity(input);
		ListaEntity listaCriada = listaService.cria(lista);
		return listaConvert.entityToOutput(listaCriada);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ListaOutput altera(@PathVariable Long id, @RequestBody @Valid ListaInput input) {
		ListaEntity listaEncontrada = listaService.buscaPorId(id);
		listaConvert.copyInputToEntity(input, listaEncontrada);
		listaService.atualiza(listaEncontrada);
		return listaConvert.entityToOutput(listaEncontrada);
	}
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<ListaOutput> listaTodos(){
		return listaConvert.listEntityToListOutput(listaService.buscaTodos());
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ListaOutput buscaPorId(@PathVariable Long id) {
		ListaEntity listaEncontrada = listaService.buscaPorId(id);
		return listaConvert.entityToOutput(listaEncontrada);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleta(@PathVariable Long id) {
		listaService.deleta(id);
	}
}
