package com.cmendes.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmendes.cursomc.domain.Pedido;
import com.cmendes.cursomc.repositories.PedidoRepository;
import com.cmendes.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoServico {
	
	@Autowired
	private PedidoRepository repo;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " +id+", Tipo: " + Pedido.class.getName()
				));
	}
}
