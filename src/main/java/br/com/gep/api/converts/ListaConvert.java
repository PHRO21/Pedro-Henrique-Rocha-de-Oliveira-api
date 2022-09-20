package br.com.gep.api.converts;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.gep.api.dto.input.ListaInput;
import br.com.gep.api.dto.outputs.ListaOutput;
import br.com.gep.api.entities.ListaEntity;

@Component
public class ListaConvert {

	@Autowired
	private ModelMapper modelMapper;

	public ListaEntity inputToEntity(ListaInput input) {
		return modelMapper.map(input, ListaEntity.class);
	}

	public ListaOutput entityToOutput(ListaEntity listaCriada) {
		return modelMapper.map(listaCriada, ListaOutput.class);
	}

	public List<ListaOutput> listEntityToListOutput(List<ListaEntity> listas) {
		return listas.stream().map(lista -> this.entityToOutput(lista)).collect(Collectors.toList());
	}

	public void copyInputToEntity(@Valid ListaInput input, ListaEntity listaEncontrada) {
		modelMapper.map(input, listaEncontrada);
	}

}
