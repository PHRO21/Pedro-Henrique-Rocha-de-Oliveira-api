package br.com.gep.api.converts;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.gep.api.dto.input.ItemInput;
import br.com.gep.api.dto.outputs.ItemOutput;
import br.com.gep.api.entities.ItemEntity;
import br.com.gep.api.services.ListaService;

@Component
public class ItemConvert {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ListaService listaService;

	public ItemEntity inputToEntity(ItemInput input) {
		ItemEntity itemCriado = modelMapper.map(input, ItemEntity.class);
		itemCriado.setLista(listaService.buscaPorId(input.getListaId()));
		return itemCriado;
	}

	public ItemOutput entityToOutput(ItemEntity itemCriada) {
		return modelMapper.map(itemCriada, ItemOutput.class);
	}

	public List<ItemOutput> listEntityToListOutput(List<ItemEntity> items) {
		return items.stream().map(item -> this.entityToOutput(item)).collect(Collectors.toList());
	}

	public void copyInputToEntity(@Valid ItemInput input, ItemEntity itemEncontrada) {
		itemEncontrada.setLista(listaService.buscaPorId(input.getListaId()));
		modelMapper.map(input, itemEncontrada);
	}

}
