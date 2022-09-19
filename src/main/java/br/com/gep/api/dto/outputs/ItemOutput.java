package br.com.gep.api.dto.outputs;

import br.com.gep.api.entities.ListaEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemOutput {
	
	private String titulo;
	
	private ListaEntity lista;
	
	private boolean concluido;
}
