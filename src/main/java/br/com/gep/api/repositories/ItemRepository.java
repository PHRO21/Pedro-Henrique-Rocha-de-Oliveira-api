package br.com.gep.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gep.api.entities.ItemEntity;
import br.com.gep.api.entities.ListaEntity;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

	List<ItemEntity> findAllByLista(ListaEntity lista);

}
