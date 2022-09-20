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
import br.com.gep.api.entities.ItemEntity;
import br.com.gep.api.entities.ListaEntity;
import br.com.gep.api.services.ItemService;
import br.com.gep.api.services.ListaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Lista")
@RestController
@RequestMapping(ControllerConfig.PRE_URL + "/listas")
public class ListaController {

	@Autowired
	private ListaConvert listaConvert;

	@Autowired
	private ListaService listaService;

	@Autowired
	private ItemService itemService;

	@Operation(summary = "Cria nova lista")
	@PostMapping("/cria")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ListaOutput cria(
			@Parameter(description = "Representação de uma lista") @RequestBody @Valid ListaInput input) {
		ListaEntity lista = listaConvert.inputToEntity(input);
		ListaEntity listaCriada = listaService.cria(lista);
		return listaConvert.entityToOutput(listaCriada);
	}

	@Operation(summary = "Altera lista existente")
	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ListaOutput altera(
			@Parameter(description = "Id da lista a ser alterada", example = "1") @PathVariable Long id,
			@Parameter(description = "Representação de uma lista") @RequestBody @Valid ListaInput input) {
		ListaEntity listaEncontrada = listaService.buscaPorId(id);
		listaConvert.copyInputToEntity(input, listaEncontrada);
		listaService.atualiza(listaEncontrada);
		return listaConvert.entityToOutput(listaEncontrada);
	}

	@Operation(summary = "Lista todas as listas criadas")
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<ListaOutput> listaTodos() {
		return listaConvert.listEntityToListOutput(listaService.buscaTodos());
	}

	@Operation(summary = "Lista uma lista pelo Id")
	@GetMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ListaOutput buscaPorId(
			@Parameter(description = "Id da lista a ser buscada", example = "1") @PathVariable Long id) {
		ListaEntity listaEncontrada = listaService.buscaPorId(id);
		return listaConvert.entityToOutput(listaEncontrada);
	}

	@Operation(summary = "Deleta lista e todos os seus itens")
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleta(@Parameter(description = "Id da lista a ser deletada", example = "1") @PathVariable Long id) {
		ListaEntity lista = listaService.buscaPorId(id);
		List<ItemEntity> itensDaLista = itemService.buscaTodosDaLista(lista);
		listaService.deleta(lista, itensDaLista);
	}
}
