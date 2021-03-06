package com.cmendes.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.cmendes.cursomc.domain.Cliente;
import com.cmendes.cursomc.domain.enums.TipoCliente;
import com.cmendes.cursomc.dto.ClienteNewDTO;
import com.cmendes.cursomc.repositories.ClienteRepository;
import com.cmendes.cursomc.resources.exception.FieldMessage;
import com.cmendes.cursomc.services.validation.util.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Autowired
	private ClienteRepository repo;
	
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		//  inserindo erros na lista
		
		if(objDto.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj()))
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		
		if(objDto.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj()))
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));

		//Válida se email existe no banco
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if(aux != null)
			list.add(new FieldMessage("email", "Email já existente"));
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}