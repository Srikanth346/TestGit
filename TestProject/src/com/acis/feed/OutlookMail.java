package com.acis.feed;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class OutlookMail {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final String username = "srikanth_maddu@optum.com";
		final String password = "Srik@nth.m5593";
		//set properties
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "outlook.office365.com");
		props.put("mail.smtp.port", "443");
		//create session 
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});	
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("srikanth_maddu@optum.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("srikanth_maddu@optum.com"));
			message.setSubject("Test");
			message.setText("HI");
			// Send Message
			Transport.send(message);
			System.out.println("Mail Sent to Srikanth Sucessfully!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}