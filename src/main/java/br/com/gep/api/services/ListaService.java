package br.com.gep.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gep.api.entities.ItemEntity;
import br.com.gep.api.entities.ListaEntity;
import br.com.gep.api.exceptions.NotFoundBussinessException;
import br.com.gep.api.repositories.ItemRepository;
import br.com.gep.api.repositories.ListaRepository;

@Service
public class ListaService {

	@Autowired
	private ListaRepository listaRepository;

	@Autowired
	private ItemRepository itemRepository;

	public ListaEntity cria(ListaEntity lista) {
		return listaRepository.save(lista);
	}

	public List<ListaEntity> buscaTodos() {
		return listaRepository.findAll();
	}

	public ListaEntity buscaPorId(Long id) {
		return listaRepository.findById(id)
				.orElseThrow(() -> new NotFoundBussinessException("Lista " + id + " não encontrada"));
	}

	public void atualiza(ListaEntity listaEncontrada) {
		listaRepository.save(listaEncontrada);
	}

	public void deleta(ListaEntity lista, List<ItemEntity> itensDaLista) {
		for (ItemEntity item : itensDaLista) {
			itemRepository.delete(item);
		}
		listaRepository.delete(lista);
	}

}
