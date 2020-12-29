package com.quortexservice.quortex.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.quortexservice.quortex.exception.QuortexException;
import com.quortexservice.quortex.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String fromEmail;

	public NotificationService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendNotification(String email, String subject, String body) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			helper.setText(body, true); 
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setFrom(fromEmail);
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new QuortexException(e.getMessage(), ConstantUtil.EMAIL_SERVICE_FAILURE_CODE);
		}
	}
	
	public void sendBccNotification(String[] email, String subject, String body) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			helper.setText(body, true); 
			helper.setBcc(email);
			helper.setSubject(subject);
			helper.setFrom(fromEmail);
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new QuortexException(e.getMessage(), ConstantUtil.EMAIL_SERVICE_FAILURE_CODE);
		}
	}
}
