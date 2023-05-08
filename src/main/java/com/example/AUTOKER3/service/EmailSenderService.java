package com.example.AUTOKER3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

	@Autowired
	private JavaMailSender mailSender;

	public void sendSimpleEmail(String subject, String body,String replyTo) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("pinterg11@gmail.com");
		message.setText(body);
		message.setSubject(subject);
		message.setReplyTo(replyTo);
		mailSender.send(message);

	}

}
