package br.com.gep.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gep.api.entities.ItemEntity;
import br.com.gep.api.entities.ListaEntity;
import br.com.gep.api.exceptions.NotFoundBussinessException;
import br.com.gep.api.repositories.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	public ItemEntity cria(ItemEntity item) {
		return itemRepository.save(item);
	}

	public ItemEntity buscaPorId(Long id) {
		return itemRepository.findById(id)
				.orElseThrow(() -> new NotFoundBussinessException("Item " + id + " não encontrado"));
	}

	public void atualiza(ItemEntity itemEncontrada) {
		itemRepository.save(itemEncontrada);
	}

	public void deleta(Long id) {
		itemRepository.deleteById(id);
	}

	public List<ItemEntity> buscaTodosDaLista(ListaEntity lista) {
		return itemRepository.findAllByLista(lista);
	}

	public void marcaConcluido(ItemEntity item) {
		item.setConcluido(true);
		itemRepository.save(item);
	}

	public void marcaNaoConcluido(ItemEntity item) {
		item.setConcluido(false);
		itemRepository.save(item);
	}

}
