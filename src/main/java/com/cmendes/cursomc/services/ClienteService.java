package com.cmendes.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.jboss.jandex.UnsupportedVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.cmendes.cursomc.domain.Cliente;
import com.cmendes.cursomc.dto.ClienteDTO;
import com.cmendes.cursomc.repositories.ClienteRepository;
import com.cmendes.cursomc.services.exceptions.DataViolationException;
import com.cmendes.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " +id+", Tipo: " + Cliente.class.getName()
				));
	}
	
	public void update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataViolationException("Não e possível excluir uma Cliente que possui pedidos");
		}
	}

	public List<Cliente> findAll() {
		List<Cliente> list = repo.findAll();
		return list;
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page,linesPerPage,Direction.valueOf(direction) , orderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
