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

@RestController
@RequestMapping(ControllerConfig.PRE_URL + "/itens")
public class ItemController {
	
	@Autowired
	private ItemConvert itemConvert;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ListaService listaService;
	
	@PostMapping("/cria")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ItemOutput cria(@RequestBody @Valid ItemInput input) {
		ItemEntity item = itemConvert.inputToEntity(input);
		ItemEntity itemCriada = itemService.cria(item);
		return itemConvert.entityToOutput(itemCriada);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ItemOutput altera(@PathVariable Long id, @RequestBody @Valid ItemInput input) {
		ItemEntity itemEncontrada = itemService.buscaPorId(id);
		itemConvert.copyInputToEntity(input, itemEncontrada);
		itemService.atualiza(itemEncontrada);
		return itemConvert.entityToOutput(itemEncontrada);
	}
	
	@GetMapping("/lista/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ItemOutput> listaTodosItensDaLista(@PathVariable Long id){
		ListaEntity lista = listaService.buscaPorId(id);
		return itemConvert.listEntityToListOutput(itemService.buscaTodosDaLista(lista));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleta(@PathVariable Long id) {
		itemService.deleta(id);
	}
	
	@PostMapping("/{id}/concluir")
	@ResponseStatus(code= HttpStatus.OK)
	public void marcaConcluido(@PathVariable Long id) {
		itemService.marcaConcluido(id);
	}
}
