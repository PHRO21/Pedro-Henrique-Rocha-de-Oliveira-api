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
import br.com.gep.api.converts.ItemConvert;
import br.com.gep.api.dto.input.ItemInput;
import br.com.gep.api.dto.outputs.ItemOutput;
import br.com.gep.api.entities.ItemEntity;
import br.com.gep.api.entities.ListaEntity;
import br.com.gep.api.services.ItemService;
import br.com.gep.api.services.ListaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Item")
@RestController
@RequestMapping(ControllerConfig.PRE_URL + "/itens")
public class ItemController {

	@Autowired
	private ItemConvert itemConvert;

	@Autowired
	private ItemService itemService;

	@Autowired
	private ListaService listaService;

	@Operation(summary = "Cria um novo item de lista")
	@PostMapping("/cria")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ItemOutput cria(@Parameter(description = "Representação de um item") @RequestBody @Valid ItemInput input) {
		ItemEntity item = itemConvert.inputToEntity(input);
		ItemEntity itemCriada = itemService.cria(item);
		return itemConvert.entityToOutput(itemCriada);
	}

	@Operation(summary = "Altera um item existente")
	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ItemOutput altera(@Parameter(description = "Id do item a ser alterado", example = "1") @PathVariable Long id,
			@Parameter(description = "Representação de um item") @RequestBody @Valid ItemInput input) {
		ItemEntity itemEncontrada = itemService.buscaPorId(id);
		itemConvert.copyInputToEntity(input, itemEncontrada);
		itemService.atualiza(itemEncontrada);
		return itemConvert.entityToOutput(itemEncontrada);
	}

	@Operation(summary = "Lista todos os itens de uma lista")
	@GetMapping("/lista/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ItemOutput> listaTodosItensDaLista(
			@Parameter(description = "Id da lista a serem buscados os itens", example = "1") @PathVariable Long id) {
		ListaEntity lista = listaService.buscaPorId(id);
		return itemConvert.listEntityToListOutput(itemService.buscaTodosDaLista(lista));
	}

	@Operation(summary = "Deleta um item")
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleta(@Parameter(description = "Id do item a ser deletado", example = "1") @PathVariable Long id) {
		itemService.deleta(id);
	}

	@Operation(summary = "Marca o item como concluído")
	@PutMapping("/{id}/concluido")
	@ResponseStatus(code = HttpStatus.OK)
	public ItemOutput marcaConcluido(
			@Parameter(description = "Id do item a ser marcado como concluído", example = "1") @PathVariable Long id) {
		ItemEntity itemEncontrada = itemService.buscaPorId(id);
		itemService.marcaConcluido(itemEncontrada);
		return itemConvert.entityToOutput(itemEncontrada);
	}

	@Operation(summary = "Desmarca o item como concluído")
	@PutMapping("/{id}/nao-concluido")
	@ResponseStatus(code = HttpStatus.OK)
	public ItemOutput desmarcaConcluido(
			@Parameter(description = "Id do item a ser desmarcado como concluído", example = "1") @PathVariable Long id) {
		ItemEntity itemEncontrada = itemService.buscaPorId(id);
		itemService.marcaNaoConcluido(itemEncontrada);
		return itemConvert.entityToOutput(itemEncontrada);
	}
}
