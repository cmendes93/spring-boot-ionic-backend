package com.cmendes.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.cmendes.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
