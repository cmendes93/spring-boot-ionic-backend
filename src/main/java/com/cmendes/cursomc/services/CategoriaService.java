package com.cmendes.cursomc.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cmendes.cursomc.domain.Categoria;
import com.cmendes.cursomc.repositories.CategoriaRepository;
import com.cmendes.cursomc.services.exceptions.DataViolationException;
import com.cmendes.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " +id+", Tipo: " + Categoria.class.getName()
				));
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null);
		obj = repo.save(obj);
		return obj;
	}

	public void update(Categoria obj) {
		find(obj.getId());
		repo.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataViolationException("Não e possível excluir uma categoria que possui produto");
		}
	}
}
