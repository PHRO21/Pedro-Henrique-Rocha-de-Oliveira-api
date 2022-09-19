package br.com.gep.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gep.api.entities.ListaEntity;
import br.com.gep.api.exceptions.NotFoundBussinessException;
import br.com.gep.api.repositories.ListaRepository;

@Service
public class ListaService {
	
	@Autowired
	private ListaRepository listaRepository;

	public ListaEntity cria(ListaEntity lista) {
		return listaRepository.save(lista);
	}

	public List<ListaEntity> buscaTodos() {
		return listaRepository.findAll();
	}

	public ListaEntity buscaPorId(Long id) {
		return listaRepository.findById(id)
				.orElseThrow(() -> new NotFoundBussinessException("Usuario " + id + " não encontrado"));
	}

	public void atualiza(ListaEntity listaEncontrada) {
		listaRepository.save(listaEncontrada);
	}

	public void deleta(Long id) {
		listaRepository.deleteById(id);
	}

}
