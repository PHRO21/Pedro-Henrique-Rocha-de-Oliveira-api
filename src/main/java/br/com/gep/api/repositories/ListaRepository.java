package br.com.gep.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gep.api.entities.ListaEntity;

public interface ListaRepository extends JpaRepository<ListaEntity, Long> {

}
